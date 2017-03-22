package jp.co.orangearch.workmanage.controller;

import java.util.Locale;

import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import jp.co.orangearch.workmanage.common.constant.LogFileMarker;
import jp.co.orangearch.workmanage.common.constant.MessageId;
import jp.co.orangearch.workmanage.common.exception.BussinessException;
import jp.co.orangearch.workmanage.common.exception.SystemException;
import jp.co.orangearch.workmanage.service.LoginUserInfo;

/**
 * コントローラの抽象クラスです。
 * <br>
 * コントローラークラスを実装する場合は本クラスを敬称してください。
 *
 * @author t-otsuka
 *
 */
@ControllerAdvice
abstract public class AbstractWorkManageController {

	/** アプリケーションロガー */
	private static final Logger logger = LoggerFactory.getLogger(LogFileMarker.APP);
	
	
	/** forward */
	protected static final String FORWARD_ACTION = "forward:";
	
	/** redirect */
	protected static final String REDIRECT_ACTION = "redirect:";
	
	@Autowired
	private MessageSource messages;



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
	@ExceptionHandler(BussinessException.class)
	public ModelAndView handleBussinessException(Exception e) {
		// 例外ハンドリングを行う
		BussinessException exception = (BussinessException)e;
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
		String msg = getMessage(messageId, fillchar, message, e);

		ModelAndView mav = new ModelAndView();
		mav.addObject("message", msg);
		mav.setViewName("error/bussinessError");
		logger.error("[" + (ObjectUtils.isEmpty(messageId) ? "-":messageId.getValue()) + "] [" + msg + "]", e);
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

		String msg = getMessage(messageId, fillchar, null, e);
        logger.error("[" + messageId.getValue() + "] [" + msg + "]", e);
		// エラーページへ遷移
		ModelAndView mav = new ModelAndView();
		mav.setViewName("error/systemError");
        return mav;
	}

	/**
	 * メッセージを取得します。
	 * メッセージプロパティから取得した文字列をメッセージIDとメッセージに分解し、
	 * メッセージIDをMDCに設定して、メッセージを返却します。
	 * @param messageId メッセージID
	 * @param fillchar 埋め時
	 * @param defaultMessage デフォルトメッセージ
	 * @param e 例外情報
	 * @return メッセージ
	 */
	private String getMessage(MessageId messageId, String[] fillchar, String defaultMessage, Exception e){
		String msg = defaultMessage;
		String level = "ERROR";
		if(StringUtils.isEmpty(defaultMessage)){
			msg = messages.getMessage(messageId.getValue(), fillchar, defaultMessage, Locale.getDefault());
			if(StringUtils.isEmpty(msg)){
				throw new SystemException(MessageId.S003, e);
			}
	
			String[] messages = msg.split("\\|");
			if(messages.length < 2){
				throw new SystemException(MessageId.S003, e);
			}
			level = messages[0];
			msg = messages[1];
		}

		MDC.put("LEVEL", level);
		return msg;
	}

	/**
	 * ログアウト処理を行います。
	 */
	protected void logout(){
		SecurityContextHolder.clearContext();
	}

}
