package jp.co.orangearch.workmanage.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

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

	@Autowired
	private MessageSource messages;

	private static final Logger logger = LoggerFactory.getLogger(AbstractWorkManageController.class);


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
		String msg = messages.getMessage(exception.getMessageId(), exception.getFillChars(), exception.getMessage(), Locale.getDefault());

		ModelAndView mav = new ModelAndView();
		mav.addObject("message", msg);
		mav.setViewName("error/bussinessError");
        logger.error("["+exception.getMessageId() + "] [" + msg + "]", e);
        return mav;
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
		String msg = messages.getMessage("M002", null, null, Locale.getDefault());

		ModelAndView mav = new ModelAndView();
		mav.addObject("message", msg);
		mav.setViewName("error/bussinessError");
        logger.error("[M002] [" + msg + "]", e);
        return mav;
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
    public ModelAndView handlehandleException(Exception e) {
        // 例外ハンドリングを行う
		SystemException exception = (SystemException)e;
		String msg = messages.getMessage(exception.getMessageId().getValue(), exception.getFillChars(), exception.getMessage(), Locale.getDefault());

		// ログアウト処理
		SecurityContextHolder.clearContext();

		// エラーページへ遷移
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", msg);
		mav.setViewName("error/systemError");
        logger.error("[" + exception.getMessageId() + "] [" + msg + "]", e);
        return mav;
    }

}
