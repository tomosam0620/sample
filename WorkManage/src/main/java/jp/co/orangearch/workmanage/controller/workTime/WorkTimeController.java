package jp.co.orangearch.workmanage.controller.workTime;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.orangearch.workmanage.common.util.DateUtils;
import jp.co.orangearch.workmanage.common.util.DateUtils.DateTimeFormat;
import jp.co.orangearch.workmanage.common.validator.CheckToken;
import jp.co.orangearch.workmanage.common.validator.GenerateToken;
import jp.co.orangearch.workmanage.controller.AbstractWorkManageController;
import jp.co.orangearch.workmanage.entity.WorkTime;
import jp.co.orangearch.workmanage.form.WorkTimeForm;
import jp.co.orangearch.workmanage.service.WorkTimeService;


/**
 * 勤務時間のコントローラクラスです。
 *
 * @author t-otsuka
 *
 */
@Controller
@RequestMapping(WorkTimeController.FUNCTION_URI)
public class WorkTimeController extends AbstractWorkManageController{

	/** 一覧画面のhtmlファイルパス */
	private static final String ROOT_HTML = "workTime/index";
	/** 入力画面のhtmlファイルパス */
	private static final String INPUT_HTML = "workTime/input";
	/** 機能のURI */
	public static final String FUNCTION_URI = "/workTime";
	/** 一覧表示画面のURI */
	private static final String ROOT_URI = "/";
	/** 入力画面のURI */
	private static final String INPUT_URI = "/input.html";
	/** 更新処理のURI */
	private static final String UPDATE_URI = "/handle.html";

	@Autowired
	private WorkTimeService workTimeService;

//	/**
//	 * 初期表示を行います。
//	 *
//	 * @param month 年月(yyyy-MM形式)
//	 * @param model モデル
//	 * @return 遷移先
//	 */
//	@GenerateToken
//	@RequestMapping(value=SHOW_URI, method=RequestMethod.GET)
//	public String show(Model model) {
//		LocalDate date = DateUtils.getCurrentDate();
//		return show(DateUtils.convert(date, DateTimeFormat.UUUU_MM), model);
//	}
//
	/**
	 * 指定年月の勤務時間を表示します。
	 *
	 * @param month 年月(yyyy-MM形式)
	 * @param model モデル
	 * @return 遷移先
	 */
	@RequestMapping(value=ROOT_URI, method=RequestMethod.GET)
	public String showAll( String month, Model model) {

		String userId = getLoginUserId();
		LocalDate showMonthDate = DateUtils.getCurrentDate();
		if(!StringUtils.isEmpty(month)){
			showMonthDate =DateUtils.convertToLocalDate(month + "-01");
		}

		// 画面表示情報設定
		List<WorkTime> workTimes = workTimeService.selectAll(userId, showMonthDate);
		model.addAttribute("workTimeForm", new WorkTimeForm()); //入力用formを設定しておかないと落ちる
		model.addAttribute("workTimes", workTimes);
		model.addAttribute("months", workTimeService.getMonthList());
		model.addAttribute("currentMonth", DateUtils.convert(showMonthDate, DateTimeFormat.UUUU_MM));

		return ROOT_HTML;
	}

	@GenerateToken
	@RequestMapping(value=INPUT_URI, method=RequestMethod.GET)
	public String show(String date, Model model){
		Optional<WorkTime> workTime = workTimeService.select(getLoginUserId(), DateUtils.convertToLocalDate(date));
		WorkTimeForm form = new WorkTimeForm();
		if(workTime.isPresent()){
			form.setAttendanceCode(workTime.get().getAttendanceCode());
			form.setEndTime(workTime.get().getEndTime());
			form.setNote(workTime.get().getNotes());
			form.setStartTime(workTime.get().getStartTime());
			form.setUserId(workTime.get().getUserId());
			form.setWorkTimeTypeAsInt(workTime.get().getWorkTimeType());
			form.setVersion(workTime.get().getVersion());
		}
		form.setWorkDate(date);
		model.addAttribute("workTimeForm", form);
		return INPUT_HTML;
	}

	/**
	 * 勤務時間の登録を行います。
	 *
	 * @param form フォーム
	 * @param bindingResult 入力チェックエラー情報
	 * @param model モデル
	 * @param attributes リダイレクト先に渡すパラメータ情報
	 * @return 遷移先
	 */
	@CheckToken
	@GenerateToken
	@RequestMapping(value=UPDATE_URI, method=RequestMethod.POST)
	public String update(@Validated WorkTimeForm form, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
		//入力チェック。
		if(bindingResult.hasErrors()){
			model.addAttribute("workTimeForm", form);
			return INPUT_HTML;
		}

		//更新用entity作成。
		String userId = getLoginUserId();
		if(!StringUtils.isEmpty(form.getUserId())){
			userId = form.getUserId();
		}

		WorkTime entity = new WorkTime();
		entity.setUserId(userId);
		entity.setWorkTimeType(form.getWorkTimeTypeAsInt());
		entity.setWorkDate(form.getWorkDateAsLocalDate());
		entity.setStartTime(form.getStartTimeAsLocalTime());
		entity.setEndTime(form.getEndTimeAsLocalTime());
		entity.setAttendanceCode(form.getAttendanceCode());
		entity.setNotes(form.getNote());
		entity.setBusinessDayFlag(DateUtils.getBusinessDayFlag(form.getWorkDateAsLocalDate()));
		entity.setVersion(form.getVersion());

		//更新
		workTimeService.update(entity);

		attributes.addFlashAttribute("result", "登録しました。");
		String param = DateUtils.convert(DateUtils.convertToLocalDate(form.getWorkDate()), DateTimeFormat.UUUU_MM);
		attributes.addAttribute("month", param);
		return "redirect:" + FUNCTION_URI + ROOT_URI;
	}
}
