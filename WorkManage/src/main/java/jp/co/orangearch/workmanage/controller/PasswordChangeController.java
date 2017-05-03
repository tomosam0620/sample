package jp.co.orangearch.workmanage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import jp.co.orangearch.workmanage.common.validator.CheckToken;
import jp.co.orangearch.workmanage.common.validator.GenerateToken;
import jp.co.orangearch.workmanage.form.passwordChange.ChangePassowrdForm;
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
	/** 更新処理のURI */
	private static final String HANDLE_URI = "/handle";
	/** top画面のURI */
	private static final String TOP_URI = "/workTime/";
	
	//modelのキー名
	/** form名 */
	private static final String FORM_NAME = "changePassowrdForm";
	/** エラー情報キー名 */
	private static final String ERROR_OBJECT_NAME = "error";

	@Autowired
	private PasswordChangeService passwordChangeService;
	
	
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
	@RequestMapping(value=ROOT_URI, method=RequestMethod.GET)
	public String update(@ModelAttribute(FORM_NAME) ChangePassowrdForm form, Model model){
		String userId = form.getUserId();
		if(StringUtils.isEmpty(userId)){
			userId = getLoginUserId();
			form.setUserId(userId);
		}
		Integer version = passwordChangeService.selectUserVersion(userId);
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
			attributes.addFlashAttribute(FORM_NAME, form);
			return REDIRECT_ACTION + FUNCTION_URI + ROOT_URI;
		}
		
		passwordChangeService.update(form.getUserId(), form.getPassword(), form.getNewPassword(), form.getVersion(), bindingResult);
		//入力エラー(パスワード突合結果)があればエラー情報をリダイレクト先に引き継いでパスワード変更画面にリダイレクト
		if(bindingResult.hasErrors()){
			attributes.addFlashAttribute(ERROR_OBJECT_NAME, bindingResult);
			attributes.addFlashAttribute(FORM_NAME, form);
			return REDIRECT_ACTION + FUNCTION_URI + ROOT_URI;
		}
		return REDIRECT_ACTION + TOP_URI;
	}

}
