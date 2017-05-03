package jp.co.orangearch.workmanage.controller.projectManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.orangearch.workmanage.common.validator.CheckToken;
import jp.co.orangearch.workmanage.common.validator.GenerateToken;
import jp.co.orangearch.workmanage.controller.AbstractWorkManageController;
import jp.co.orangearch.workmanage.domain.entity.Customer;
import jp.co.orangearch.workmanage.domain.entity.Project;
import jp.co.orangearch.workmanage.form.ProjectManageForm;
import jp.co.orangearch.workmanage.service.ProjectManageService;

/**
 * プロジェクト管理機能のコントローラークラスです。
 * @author t-otsuka
 *
 */
@Controller
@RequestMapping(ProjectManageController.FUNCTION_URI)
public class ProjectManageController extends AbstractWorkManageController{
	/** 機能のURI */
	public static final String FUNCTION_URI = "/admin/projectManage";
	
	/** indexページのURI */
	private static final String INDEX_URI = "/";
	/** 入力ページのURI */
	private static final String INPUT_URI = "/input";
	/** 更新処理のURI */
	private static final String HANDLE_URI = "/handle";
	
	/** index画面のHTMLファイルパス */
	private static final String SHOW_HTML = "projectManage/index";
	/** 入力画面のhtmlファイルパス */
	private static final String INPUT_HTML = "projectManage/input";
	
	//modelのキー名
	/** form名 */
	private static final String FORM_NAME = "projectManageForm";
	/** エラー情報キー名 */
	private static final String ERROR_OBJECT_NAME = "error";

	/** 休日管理サービス。 */
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
	public String index(Model model) {
		List<Project> projects = projectManageService.selectAll();
		model.addAttribute("projects", projects);
		return SHOW_HTML;
	}

	@GenerateToken
	@RequestMapping(value=INPUT_URI, method=RequestMethod.GET)
	public String input(Model model) {
		List<Customer> customers = projectManageService.selectCustomers();
		ProjectManageForm form = new ProjectManageForm();
		model.addAttribute(FORM_NAME, form);
		model.addAttribute("customers", customers);
		return INPUT_HTML;
	}

	@CheckToken
	@RequestMapping(value=HANDLE_URI, method=RequestMethod.POST)
	public String handle(@Validated ProjectManageForm form, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
		if(bindingResult.hasErrors()){
			attributes.addFlashAttribute(FORM_NAME, form);
			attributes.addFlashAttribute(ERROR_OBJECT_NAME, bindingResult);
			return REDIRECT_ACTION + FUNCTION_URI + INPUT_HTML;
		}
		
		Project entity = form.toEntity();
		projectManageService.create(entity);
		return REDIRECT_ACTION + FUNCTION_URI + INDEX_URI;
	}


}