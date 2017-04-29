package jp.co.orangearch.workmanage.controller.userManage;

import java.util.List;
import java.util.Optional;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.orangearch.workmanage.common.validator.GenerateToken;
import jp.co.orangearch.workmanage.controller.AbstractWorkManageController;
import jp.co.orangearch.workmanage.domain.entity.Project;
import jp.co.orangearch.workmanage.domain.entity.join.JoinProjectUser;
import jp.co.orangearch.workmanage.form.userManage.SearchForm;
import jp.co.orangearch.workmanage.form.userManage.UserManageForm;
import jp.co.orangearch.workmanage.service.ProjectManageService;
import jp.co.orangearch.workmanage.service.UserManageService;

/**
 * ユーザー管理機能のコントローラークラスです。
 * @author t-otsuka
 *
 */
@Controller
@RequestMapping(userManageController.FUNCTION_URI)
public class userManageController extends AbstractWorkManageController{
	/** 機能のURI */
	public static final String FUNCTION_URI = "/userManage";
	
	/** indexページのURI */
	private static final String INDEX_URI = "/";
	/** 入力ページのURI */
	private static final String INPUT_URI = "/input";
	/** 更新処理のURI */
	private static final String HANDLE_URI = "/handle";
	
	/** index画面のHTMLファイルパス */
	private static final String SHOW_HTML = "userManage/index";
	/** 入力画面のhtmlファイルパス */
	private static final String INPUT_HTML = "userManage/input";
	
	//modelのキー名
	/** form名 */
	private static final String SEARCH_FORM_NAME = "searchForm";
	/** form名 */
	private static final String FORM_NAME = "userManageForm";
	/** エラー情報キー名 */
	private static final String ERROR_OBJECT_NAME = "error";

	/** ユーザー管理サービス。 */
	@Autowired
	private UserManageService userManageService;

	/** ユーザー管理サービス。 */
	@Autowired
	private ProjectManageService projectManageService;
	
	/**
	 * ｉｎｄｅｘページ表示処理
	 * <br>
	 * 登録されているプロジェクト情報を取得し、表示します。
	 * @param model モデル
	 * @return 遷移先ページのHTMLファイルパス
	 */
	@RequestMapping(value=INDEX_URI, method=RequestMethod.GET)
	public String search(@ModelAttribute(SEARCH_FORM_NAME) @Validated SearchForm form, BindingResult bindingResult, Model model) {
		List<JoinProjectUser> users = userManageService.select(form.getRoleId(), form.getAffiliationAsInt(), form.getPositionAsInt(), form.getProjectAsInt());
		model.addAttribute("users", users);
		return SHOW_HTML;
	}

	@GenerateToken
	@RequestMapping(value=INPUT_URI, method=RequestMethod.GET)
	public String input(Model model) {
		UserManageForm form = new UserManageForm();
		model.addAttribute(FORM_NAME, form);
		List<Project> projects = projectManageService.selectAll();
		model.addAttribute("projects", projects);
		return INPUT_HTML;
	}

	@GenerateToken
	@RequestMapping(value="/{userId}" +INPUT_URI, method=RequestMethod.GET)
	public String update(@PathVariable @Validated @Length(max=7) @NotEmpty String userId, Model model) {
		Optional<JoinProjectUser> user = userManageService.select(userId);
		UserManageForm form = new UserManageForm(user);
		model.addAttribute(FORM_NAME, form);
		List<Project> projects = projectManageService.selectAll();
		model.addAttribute("projects", projects);
		return INPUT_HTML;
	}

//	@CheckToken
//	@RequestMapping(value=HANDLE_URI, method=RequestMethod.POST)
//	public String handle(@Validated ProjectManageForm form, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
//		if(bindingResult.hasErrors()){
//			attributes.addFlashAttribute(FORM_NAME, form);
//			attributes.addFlashAttribute(ERROR_OBJECT_NAME, bindingResult);
//			return REDIRECT_ACTION + FUNCTION_URI + INPUT_HTML;
//		}
//		
//		Project entity = form.toEntity();
//		projectManageService.create(entity);
//		return REDIRECT_ACTION + FUNCTION_URI + INDEX_URI;
//	}
//

}