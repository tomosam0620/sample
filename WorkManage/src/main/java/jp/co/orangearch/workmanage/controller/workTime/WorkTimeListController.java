package jp.co.orangearch.workmanage.controller.workTime;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.orangearch.workmanage.component.CalendarComponent;
import jp.co.orangearch.workmanage.component.util.DateUtils;
import jp.co.orangearch.workmanage.component.util.DateUtils.DateTimeFormat;
import jp.co.orangearch.workmanage.controller.AbstractWorkManageController;
import jp.co.orangearch.workmanage.dto.OperationInfoOfMonth;
import jp.co.orangearch.workmanage.form.workTime.SearchForm;
import jp.co.orangearch.workmanage.service.WorkTimeService;

@Controller
@RequestMapping(WorkTimeListController.FUNCTION_URI)
public class WorkTimeListController extends AbstractWorkManageController{

	protected static final String FUNCTION_URI = "/workTimeList";
	private static final String ROOT_URI = "/";
	private static final String INDEX_HTML = "/index";
	
	@Autowired
	private WorkTimeService workTimeService;
	
	@Autowired
	private CalendarComponent calendarComponent;
	
	@RequestMapping(value = ROOT_URI, method = {RequestMethod.GET,RequestMethod.POST})
	public String serch(@Validated SearchForm form, BindingResult errors, Model model){
		AccessScope scope = hasAuthority(form.getAffiliationAsInt(), form.getProjectAsInt(), form.getUserId());
		List<OperationInfoOfMonth> workTimeSammarys = new ArrayList<OperationInfoOfMonth>();
		if(scope != null){
			if(StringUtils.isEmpty(form.getFromMonth()) && StringUtils.isEmpty(form.getToMonth())){
				String month = DateUtils.convert(calendarComponent.getSystemDate(), DateTimeFormat.UUUU_MM);
				form.setFromMonth(month);
				form.setToMonth(month);
			}
			workTimeSammarys = workTimeService.selectSummary(form.getFromMonth(), form.getToMonth(), scope.getAffiliationCd(), scope.getProjectId(), scope.getUserId());
		}
		model.addAttribute("list", workTimeSammarys);
		model.addAttribute("searchForm", form);
		model.addAttribute("searchmonth", "");
		model.addAttribute("searchaffiliation", "");
		model.addAttribute("searchproject", "");
		model.addAttribute("searchlist", "");
		model.addAttribute("searchuserId", "");
		return FUNCTION_URI + INDEX_HTML;
	}
	
}
