package jp.co.orangearch.workmanage.controller;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.exception.BusinessException;
import jp.co.orangearch.workmanage.domain.exception.SystemException;
import jp.co.orangearch.workmanage.domain.logger.ApplicationLogger;
import jp.co.orangearch.workmanage.domain.logger.MessageHandler;
import jp.co.orangearch.workmanage.domain.logger.MessageHandler.MessageInfo;
import jp.co.orangearch.workmanage.service.LoginUserInfo;

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
	
	/** ロガー。 */
	@Autowired
	private ApplicationLogger applicationLogger;
	
	/** メッセージハンドラー。 */
	@Autowired
	private MessageHandler messageHandler;

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

}
