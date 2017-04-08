package jp.co.orangearch.workmanage.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.orangearch.workmanage.common.validator.CheckToken;
import jp.co.orangearch.workmanage.common.validator.GenerateToken;
import jp.co.orangearch.workmanage.component.CalendarComponent;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.logger.MessageHandler;
import jp.co.orangearch.workmanage.form.passwordChange.ChangePassowrdForm;
import jp.co.orangearch.workmanage.service.LoginUserInfo;
import jp.co.orangearch.workmanage.service.PasswordChangeService;

/**
 * パスワード変更画面のコントローラークラスです。
 * @author t-otsuka
 *
 */
@Controller
@RequestMapping(PasswordChangeController.FUNCTION_URI)
public class PasswordChangeController extends AbstractWorkManageController {

	/** 機能のURI */
	public static final String FUNCTION_URI = "/passwordChange";
	/** 更新画面のhtmlファイルパス */
	private static final String UPDATE_HTML = "/update";
	/** 一覧表示画面のURI */
	private static final String ROOT_URI = "/";
	/** 更新画面のURI */
	private static final String UPDAT_URI = "/update";
	/** 更新処理のURI */
	private static final String HANDLE_URI = "/handle";
	/** top画面のURI */
	private static final String TOP_URI = "/workTime/";
	
	//modelのキー名
	/** form名 */
	private static final String FORM_NAME = "changePassowrdForm";
	/** エラー情報キー名 */
	private static final String ERROR_OBJECT_NAME = "error";

	/** パスワード有効期間。 */
	@Value("${password.expiredDuration}")
	private int passwordExpiredDuration;
	
	/** カレンダーコンポーネント。 */
	@Autowired
	private CalendarComponent calendarComponent;

	@Autowired
	private PasswordChangeService passwordChangeService;
	
	@Autowired
	private MessageHandler messageHandler;
	
	
	/**
	 * 遷移先の振り分けを行います。
	 * <br>
	 * パスワード有効期限が切れている場合はエラー情報を遷移先に引き継いでパスワード変更画面表示処理にリダイレクトする。<br>
	 * その他の場合はトップページへ遷移します。
	 * @param model モデル
	 * @param attributes リダイレクト先に引き継ぐ情報
	 * @return 遷移先
	 */
	@RequestMapping(value=ROOT_URI, method=RequestMethod.GET)
	public String dispatch(Model model, RedirectAttributes attributes){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null && auth.getPrincipal() != null){
			Object principal = auth.getPrincipal();
			if(principal instanceof LoginUserInfo){
				LoginUserInfo loginUserInfo = LoginUserInfo.class.cast(principal);
				LocalDate lastChangeDate = loginUserInfo.getPasswordLastChangeDate();
				LocalDate now = calendarComponent.getSystemDate();
				if(now.isAfter(lastChangeDate.plusDays(passwordExpiredDuration))){
					attributes.addFlashAttribute("result", messageHandler.getMessage(MessageId.M005, null, null, null).getMessage());
					return REDIRECT_ACTION + FUNCTION_URI + UPDAT_URI;
				}
			}
		}
		return REDIRECT_ACTION + TOP_URI;
	}

	/**
	 * パスワード変更画面表示処理
	 * <br>
	 * 楽観排他用にバージョンを取得してモデルに追加(ModelAttributeがformに付与されているので、formに追加すればモデルに追加される)する。<br>
	 * 2重登録防止用にトークン生成を行う。
	 * @param form フォーム
	 * @param model モデル
	 * @return 遷移先
	 */
	@GenerateToken
	@RequestMapping(value=UPDAT_URI, method=RequestMethod.GET)
	public String update(@ModelAttribute(FORM_NAME) ChangePassowrdForm form, Model model){
		Integer version = passwordChangeService.selectUserVersion(getLoginUserId());
		form.setVersion(version);
		
		//モデルにエラー情報が存在していれば、フォームの入力エラー情報としてモデルに登録
		if(model.containsAttribute(ERROR_OBJECT_NAME)){
			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + FORM_NAME, model.asMap().get(ERROR_OBJECT_NAME));
		}
		return FUNCTION_URI + UPDATE_HTML;
	}

	/**
	 * パスワード更新処理を行います。
	 * <br>
	 * 2重登録防止用にトークンチェックを行う。
	 * 
	 * @param form フォーム
	 * @param bindingResult 入力エラー情報
	 * @param model モデル
	 * @param attributes リダイレクト先に引き継ぐ情報
	 * @return 遷移先
	 */
	@CheckToken
	@RequestMapping(value=HANDLE_URI, method=RequestMethod.POST)
	public String handle(@Validated ChangePassowrdForm form, BindingResult bindingResult, Model model, RedirectAttributes attributes){
		//入力エラー(単体チェック)があればエラー情報をリダイレクト先に引き継いでパスワード変更画面にリダイレクト
		if(bindingResult.hasErrors()){
			attributes.addFlashAttribute(ERROR_OBJECT_NAME, bindingResult);
			return REDIRECT_ACTION + FUNCTION_URI + UPDAT_URI;
		}
		
		passwordChangeService.update(getLoginUserId(), form.getPassword(), form.getNewPassword(), form.getVersion(), bindingResult);
		//入力エラー(パスワード突合結果)があればエラー情報をリダイレクト先に引き継いでパスワード変更画面にリダイレクト
		if(bindingResult.hasErrors()){
			attributes.addFlashAttribute(ERROR_OBJECT_NAME, bindingResult);
			return REDIRECT_ACTION + FUNCTION_URI + UPDAT_URI;
		}
		return REDIRECT_ACTION + TOP_URI;
	}

}
