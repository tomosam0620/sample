package jp.co.orangearch.workmanage.controller;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import jp.co.orangearch.workmanage.component.CalendarComponent;
import jp.co.orangearch.workmanage.component.util.DateUtils;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.entity.Project;
import jp.co.orangearch.workmanage.domain.entity.join.JoinProjectUser;
import jp.co.orangearch.workmanage.domain.exception.BusinessException;
import jp.co.orangearch.workmanage.domain.exception.SystemException;
import jp.co.orangearch.workmanage.domain.logger.ApplicationLogger;
import jp.co.orangearch.workmanage.domain.logger.MessageHandler;
import jp.co.orangearch.workmanage.domain.logger.MessageInfo;
import jp.co.orangearch.workmanage.service.LoginUserInfo;
import jp.co.orangearch.workmanage.service.ProjectManageService;
import jp.co.orangearch.workmanage.service.UserManageService;

/**
 * コントローラの抽象クラスです。
 * <br>
 * コントローラークラスを実装する場合は本クラスを継承してください。
 *
 * @author t-otsuka
 *
 */
@ControllerAdvice
abstract public class AbstractWorkManageController {

	/** forward */
	protected static final String FORWARD_ACTION = "forward:";
	
	/** redirect */
	protected static final String REDIRECT_ACTION = "redirect:";
	
	/** ロール(役員) */
	private static final String ROLE_OFFICER = "A01";
	
	/** ロール(部長) */
	private static final String ROLE_GENERAL_MANAGER = "A02";
	
	/** ロール(課長) */
	private static final String ROLE_MANAGER = "A03";
	
	/** ロール(PL) */
	private static final String ROLE_LEADER = "U01";
	
	/** ロール(一般) */
	private static final String ROLE_GENERAL = "U02";
	
	/** ロール(経理) */
	private static final String ROLE_ACCOUNTANT = "U03";
	
	/** ロガー。 */
	@Autowired
	private ApplicationLogger applicationLogger;
	
	/** メッセージハンドラー。 */
	@Autowired
	private MessageHandler messageHandler;

	@Autowired
	private UserManageService userManageService;

	@Autowired
	private ProjectManageService projectManageService;

	@Autowired
	private CalendarComponent calendarComponent;

	/**
	 * ログインユーザ情報取得。
	 *
	 * @return ログインユーザ情報。
	 */
	protected LoginUserInfo getLoginUserInfo() {

		LoginUserInfo userInfo = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(auth != null){
			Object principal = auth.getPrincipal();
			if(principal != null && (principal instanceof LoginUserInfo)){
				userInfo = (LoginUserInfo)auth.getPrincipal();
			}
		}

		return userInfo;
	}

	/**
	 * ログインユーザID取得。
	 *
	 * @return ログインユーザID。
	 */
	protected String getLoginUserId(){
		LoginUserInfo userInfo = getLoginUserInfo();
		return ObjectUtils.isEmpty(userInfo) ? null : userInfo.getUsername();
	}

	/**
	 * 業務例外の共通ハンドリングを行います。
	 * <p>
	 * コントローラーのメソッドが業務例外で抜けた場合に、本メソッドで以下処理を行います。<br>
	 * <li> エラー内容のログ出力
	 * <li> 業務エラーページへ遷移
	 * </p>
	 * @param e 例外情報
	 * @return 遷移先情報
	 */
	@ExceptionHandler(BusinessException.class)
	public ModelAndView handleBussinessException(Exception e) {
		// 例外ハンドリングを行う
		BusinessException exception = (BusinessException)e;
		return handleBusinessException(exception.getMessageId(), exception.getFillChars(), exception.getMessage(), e);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String handlConstraintViolationException(Exception e){
		return e.getMessage();
	}
	
	/**
	 * lock例外の共通ハンドリングを行います。
	 * <p>
	 * コントローラーのメソッドが業務例外で抜けた場合に、本メソッドで以下処理を行います。<br>
	 * <li> エラー内容のログ出力
	 * <li> 業務エラーページへ遷移
	 * </p>
	 * @param e 例外情報
	 * @return 遷移先情報
	 */
	@ExceptionHandler(OptimisticLockingFailureException.class)
	public ModelAndView handleOptimisticLockingFailureException(Exception e) {
		// 例外ハンドリングを行う
		return handleBusinessException(MessageId.M002, null, null, e);
	}

	/**
	 * システム例外の共通ハンドリングを行います。
	 * <p>
	 * コントローラーのメソッドがシステム例外で抜けた場合に、本メソッドで以下処理を行います。
	 * エラー内容をログ出力し、ログアウト処理後、システムエラーページに遷移する。
	 * <li> エラー内容のログ出力
	 * <li> ログアウト処理
	 * <li> システムエラーページへ遷移
	 * </p>
	 * @param e 例外情報
	 * @return 遷移先情報
	 */
	@ExceptionHandler(SystemException.class)
	public ModelAndView handleSystemException(Exception e) {
		SystemException exception = (SystemException)e;
		return handleException(exception.getMessageId(), exception.getFillChars(), e);
	}

	/**
	 * その他例外の共通ハンドリングを行います。
	 * <p>
	 * コントローラーのメソッドが業務例外、システム例外、楽観排他例外以外で抜けた場合に、<br>
	 * 本メソッドで以下処理を行います。
	 * エラー内容をログ出力し、ログアウト処理後、システムエラーページに遷移する。
	 * <li> エラー内容のログ出力
	 * <li> ログアウト処理
	 * <li> システムエラーページへ遷移
	 * </p>
	 * @param e 例外情報
	 * @return 遷移先情報
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleOtherException(Exception e) {
		return handleException(MessageId.S003, null, e);
	}

	/**
	 * 業務系エラーハンドリング処理を行います。
	 *
	 * @param messageId メッセージID
	 * @param fillchar 埋め時
	 * @param message メッセージ
	 * @param e 例外情報
	 * @return 遷移先情報
	 */
	private ModelAndView handleBusinessException(MessageId messageId, String[] fillchar, String message, Exception e){
		// 例外ハンドリングを行う
		MessageInfo msgInfo = messageHandler.getMessage(messageId, fillchar, message, e);

		ModelAndView mav = new ModelAndView();
		mav.addObject("message", msgInfo.getMessage());
		mav.setViewName("error/bussinessError");
		applicationLogger.log(messageId, fillchar, message, e);
		return mav;
	}

	/**
	 * システム系エラーハンドリング処理を行います。
	 *
	 * エラー画面に遷移する前にログアウト処理を行います。
	 *
	 * @param messageId メッセージID
	 * @param fillchar 埋め時
	 * @param e 例外情報
	 * @return 遷移先情報
	 */
	private ModelAndView handleException(MessageId messageId, String[] fillchar, Exception e){

		logout();

		applicationLogger.log(messageId, fillchar, null, e);
		// エラーページへ遷移
		ModelAndView mav = new ModelAndView();
		mav.setViewName("error/systemError");
		return mav;
	}

	/**
	 * ログアウト処理を行います。
	 */
	protected void logout(){
		SecurityContextHolder.clearContext();
	}

	/**
	 * アクセス権をチェックする。
	 * ユーザーのアクセス権から引数に対するアクセス権をチェックする。
	 * アクセス権情報の各要素はnullの場合制約なしとする。
	 * @param affiliationCd 所属コード
	 * @param projectId プロジェクトID
	 * @param userId ユーザーID
	 * @return アクセス権情報(アクセス権がない場合はnull)
	 */
	protected AccessScope hasAuthority(Integer affiliationCd, Integer projectId, String userId){
		LoginUserInfo userInfo = getLoginUserInfo();
		String roleId = userInfo.getRoleId();
		
		affiliationCd = affiliationCd == null ? null : affiliationCd;
		projectId = projectId == null ? null : projectId;
		userId = StringUtils.isEmpty(userId) ? null : userId;
		
		AccessScope scope = new AccessScope();
		switch(roleId){
		case ROLE_OFFICER:
		case ROLE_ACCOUNTANT:
			scope.setAffiliationCd(affiliationCd);
			scope.setProjectId(projectId);
			scope.setUserId(userId);
			break;
		case ROLE_GENERAL_MANAGER://部長
			scope = getGeneralManagerAccessScope(affiliationCd, projectId, userId);
			break;
		case ROLE_MANAGER:
			scope = getLeaderAccessScope(affiliationCd, projectId, userId);
			break;
		case ROLE_LEADER:
			scope = getLeaderAccessScope(affiliationCd, projectId, userId);
			break;
		default:
			scope = getGeneralAccessScope(affiliationCd, projectId, userId);
			break;
		}
		return scope;
	}

	
	private AccessScope getGeneralManagerAccessScope(Integer affiliationCd, Integer projectId, String userId){
		LocalDate today = calendarComponent.getSystemDate();
		LoginUserInfo userInfo = getLoginUserInfo();
		AccessScope scope = new AccessScope();
		
		//所属配下のみアクセス可。
		if(affiliationCd != null && !userInfo.getAffiliation().equals(affiliationCd)){
			return null;
		}
		//プロジェクトが所属配下。
		Optional<Project> projectInfo = Optional.empty();
		if(projectId != null){
			projectInfo = projectManageService.select(projectId);
			if(!projectInfo.isPresent() || !projectInfo.get().getAffiliation().equals(affiliationCd)){
				return null;
			}
		}
		//所属配下。
		Optional<JoinProjectUser> user = userManageService.select(userInfo.getAffiliation(), projectId, userId,
				DateUtils.getFirstDayOfMonth(today), DateUtils.getFinalDayOfMonth(today));
		if(!StringUtils.isEmpty(userId) && !userId.equals(user.get().getUserId())){
			return null;
		}
		scope.setAffiliationCd(userInfo.getAffiliation());
		scope.setProjectId(projectId);
		scope.setUserId(userId);
		return scope;
	}
	
	private AccessScope getLeaderAccessScope(Integer affiliationCd, Integer projectId, String userId){
		LocalDate today = calendarComponent.getSystemDate();
		LoginUserInfo userInfo = getLoginUserInfo();
		AccessScope scope = new AccessScope();
		
		if(affiliationCd != null && !userInfo.getAffiliation().equals(affiliationCd)){
			return null;
		}
		Integer myPriject = userManageService.select(null, null, getLoginUserId(),
				DateUtils.getFirstDayOfMonth(today), DateUtils.getFinalDayOfMonth(today)).get().getProjectId();
		if(projectId != null && !projectId.equals(myPriject)){
			return null;
		}
		Optional<JoinProjectUser> user = userManageService.select(affiliationCd, myPriject, userId,
				DateUtils.getFirstDayOfMonth(today), DateUtils.getFinalDayOfMonth(today));
		//ユーザーが同一プロジェクトでなければNG
		if(!user.isPresent() || (projectId !=null && !user.get().getProjectId().equals(myPriject))){
			return null;
		}
		scope.setAffiliationCd(affiliationCd);
		scope.setProjectId(StringUtils.isEmpty(userId) ? myPriject : projectId);
		scope.setUserId(userId);
		return scope;
	}

	private AccessScope getGeneralAccessScope(Integer affiliationCd, Integer projectId, String userId){
		LoginUserInfo userInfo = getLoginUserInfo();
		AccessScope scope = new AccessScope();
		
		//一般の場合、自分の属性(所属、プロジェクト、ユーザーID)と違う場合NG
		//所属
		if(!StringUtils.isEmpty(affiliationCd) && !userInfo.getAffiliation().equals(affiliationCd)){
			return null;
		}
		//プロジェクトID
		LocalDate now = LocalDate.now();
		Integer myPriject = userManageService.select(null, null, getLoginUserId(),
				DateUtils.getFirstDayOfMonth(now), DateUtils.getFinalDayOfMonth(now)).get().getProjectId();
		if(!StringUtils.isEmpty(projectId) && !projectId.equals(myPriject)){
			return null;
		}
		//ユーザーID
		if(!StringUtils.isEmpty(userId)){
			if(!StringUtils.isEmpty(userId) && !userInfo.getUsername().equals(userId)){
				return null;
			}
		}
		scope.setAffiliationCd(affiliationCd);
		scope.setProjectId(projectId);
		scope.setUserId(userInfo.getUsername());
		
		return scope;
	}
	
	protected class AccessScope {
		private Integer affiliationCd;
		private Integer projectId;
		private String userId;
		
		public Integer getAffiliationCd() {
			return affiliationCd;
		}

		public void setAffiliationCd(Integer affiliationCd2) {
			affiliationCd = affiliationCd2;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String value) {
			userId = value;
		}

		public Integer getProjectId() {
			return projectId;
		}
		
		public void setProjectId(Integer value) {
			projectId = value;
		}
		
	}

}
