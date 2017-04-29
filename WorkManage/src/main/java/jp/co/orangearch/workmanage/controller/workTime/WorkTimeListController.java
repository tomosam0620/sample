package jp.co.orangearch.workmanage.controller.workTime;

import java.util.ArrayList;
import java.util.List;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.orangearch.workmanage.controller.AbstractWorkManageController;
import jp.co.orangearch.workmanage.dto.OperationInfoOfMonth;
import jp.co.orangearch.workmanage.form.workTime.SearchForm;
import jp.co.orangearch.workmanage.service.WorkTimeService;

/**
 * 稼働一覧機能のコントローラークラスです。
 * 
 * @author t-otsuka
 *
 */
@Controller
@RequestMapping(WorkTimeListController.FUNCTION_URI)
public class WorkTimeListController extends AbstractWorkManageController{
	protected static final String FUNCTION_URI = "/workTimeList";
	private static final String ROOT_URI = "/";
	private static final String INDEX_HTML = "/index";

	@Value("${seachResult.maxRow}")
	private String maxRow;
	
	@Autowired
	private WorkTimeService workTimeService;
	
	/**
	 * 検索
	 * 
	 * @param form 検索フォーム
	 * @param errors エラー情報
	 * @param model モデル
	 * @return 遷移先
	 */
	@RequestMapping(value = ROOT_URI, method = {RequestMethod.GET,RequestMethod.POST})
	public String search(@Validated SearchForm form, BindingResult errors, Model model){
		AccessScope scope = hasAuthority(form.getAffiliationAsInt(), form.getProjectAsInt(), form.getUserId());
		List<OperationInfoOfMonth> workTimeSammarys = new ArrayList<OperationInfoOfMonth>();
		if(scope != null){
			SelectOptions options = SelectOptions.get().count().limit(Integer.valueOf(maxRow));
			workTimeSammarys = workTimeService.selectSummary(form.getFromMonth(), form.getToMonth(), scope.getAffiliationCd(), scope.getProjectId(), scope.getUserId(), options);
			model.addAttribute("count", options.getCount());
		}
		model.addAttribute("list", workTimeSammarys);
		return FUNCTION_URI + INDEX_HTML;
	}
	
}
