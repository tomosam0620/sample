package jp.co.orangearch.workmanage.controller.workTime;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.orangearch.workmanage.common.validator.CheckToken;
import jp.co.orangearch.workmanage.common.validator.DateValid;
import jp.co.orangearch.workmanage.common.validator.GenerateToken;
import jp.co.orangearch.workmanage.component.CalendarComponent;
import jp.co.orangearch.workmanage.component.util.DateUtils;
import jp.co.orangearch.workmanage.component.util.DateUtils.DateTimeFormat;
import jp.co.orangearch.workmanage.controller.AbstractWorkManageController;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.entity.TransportionExpense;
import jp.co.orangearch.workmanage.domain.entity.WorkTime;
import jp.co.orangearch.workmanage.domain.entity.WorkTimeType;
import jp.co.orangearch.workmanage.domain.logger.MessageHandler;
import jp.co.orangearch.workmanage.domain.logger.MessageInfo;
import jp.co.orangearch.workmanage.dto.OperationTime;
import jp.co.orangearch.workmanage.form.workTime.SelectMonthForm;
import jp.co.orangearch.workmanage.form.workTime.WorkTimeForm;
import jp.co.orangearch.workmanage.service.WorkTimeService;


/**
 * 勤務時間のコントローラクラスです。
 *
 * @author t-otsuka
 *
 */
@Controller
@Validated
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
	/** 一覧表示画面のURI */
	private static final String SHOWALL_URI = "/showAll";
	/** 入力画面のURI */
	private static final String INPUT_URI = "/{date}/input";
	/** 更新処理のURI */
	private static final String UPDATE_URI = "/handle";

	//modelのキー名
	/** form名 */
	private static final String FORM_NAME = "workTimeForm";
	/** エラー情報キー名 */
	private static final String ERROR_OBJECT_NAME = "error";

	/** 勤務時間サービス。 */
	@Autowired
	private WorkTimeService workTimeService;

	/** カレンダーコンポーネント。 */
	@Autowired
	private CalendarComponent calendarComponent;

	/** メッセージ管理。 */
	@Autowired
	private MessageHandler messagehandler;
	
	/**
	 * 初期表示を行います。
	 *
	 * @param month 年月(yyyy-MM形式)
	 * @param model モデル
	 * @return 遷移先
	 */
	@GenerateToken
	@RequestMapping(value=ROOT_URI, method=RequestMethod.GET)
	public String init(Model model) {
		LocalDate date = calendarComponent.getSystemDate();
		return show(DateUtils.convert(date, DateTimeFormat.UUUU_MM), model);
	}

	/**
	 * 指定年月の勤務時間を表示します。
	 *
	 * @param month 年月(yyyy-MM形式)
	 * @param model モデル
	 * @return 遷移先
	 */
	@RequestMapping(value=ROOT_URI + "{month}", method=RequestMethod.GET)
	public String show(@DateValid(pattern="uuuu-MM") @PathVariable String month, Model model) {

		String userId = getLoginUserId();
		LocalDate showMonthDate = calendarComponent.getSystemDate();
		
		if(!StringUtils.isEmpty(month)){
			showMonthDate =DateUtils.convertToLocalDate(month + "-01");
		}

		// 画面表示情報設定
		List<OperationTime> workTimes = workTimeService.selectWorkTimeInfoInMonth(userId, showMonthDate);
		List<TransportionExpense> transportInfos = workTimeService.selectTransportionInfo(userId, showMonthDate);
		model.addAttribute(FORM_NAME, new WorkTimeForm()); //入力用formを設定しておかないと落ちる
		model.addAttribute("workTimes", workTimes);
		model.addAttribute("transportInfos", transportInfos);
		model.addAttribute("currentMonth", new SelectMonthForm(DateUtils.convert(showMonthDate, DateTimeFormat.UUUU_MM)));

		return ROOT_HTML;
	}

	/**
	 * 指定年月の勤務時間を表示します。
	 *
	 * @param month 年月(yyyy-MM形式)
	 * @param model モデル
	 * @return 遷移先
	 */
	@RequestMapping(value=SHOWALL_URI + "{month}", method=RequestMethod.GET)
	public String showAll(@DateValid(pattern="uuuu-MM") @PathVariable String month, Model model) {

//		String roleId = getLoginUserInfo().getRoleId();
//		LocalDate showMonthDate = DateUtils.convertToLocalDate(month + "-01");
//
//		// 画面表示情報設定
//		List<WorkTime> workTimes = workTimeService.selectAll(userId,showMonthDate);
//		List<TransportionExpense> transportInfos = workTimeService.selectTransportionInfo(userId, showMonthDate);
//		model.addAttribute(FORM_NAME, new WorkTimeForm()); //入力用formを設定しておかないと落ちる
//		model.addAttribute("workTimes", workTimes);
//		model.addAttribute("transportInfos", transportInfos);
//		model.addAttribute("currentMonth", new SelectMonthForm(DateUtils.convert(showMonthDate, DateTimeFormat.UUUU_MM)));

		return ROOT_HTML;
	}

	@GenerateToken
	@RequestMapping(value=INPUT_URI, method=RequestMethod.GET)
	public String input(@DateValid(pattern="uuuu-MM-dd") @PathVariable String date, Model model){
		Optional<WorkTime> workTime = workTimeService.select(getLoginUserId(), DateUtils.convertToLocalDate(date));
		WorkTimeForm form = new WorkTimeForm();
		if(workTime.isPresent()){
			form = new WorkTimeForm(workTime.get());

			if(workTime.isPresent()){
				form = new WorkTimeForm(workTime.get());
			}
		}
		
		//モデルにエラー情報が存在していれば、フォームの入力エラー情報としてモデルに登録
		if(model.containsAttribute(ERROR_OBJECT_NAME)){
			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + FORM_NAME, model.asMap().get(ERROR_OBJECT_NAME));
			form = WorkTimeForm.class.cast(model.asMap().get(FORM_NAME));
			if(workTime.isPresent()){
				form.setVersion(workTime.get().getVersion());
			}
		}

		form.setWorkDate(date);
		List<WorkTimeType> workTimeTypes = workTimeService.getWorkTimeType();
		model.addAttribute("workTimeTypes", workTimeTypes);
		model.addAttribute(FORM_NAME, form);
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
	@RequestMapping(value=UPDATE_URI, method=RequestMethod.POST)
	public String handle(@Validated WorkTimeForm form, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
		//入力チェック。
		if(bindingResult.hasErrors()){
			attributes.addFlashAttribute(FORM_NAME, form);
			attributes.addFlashAttribute(ERROR_OBJECT_NAME, bindingResult);
			return REDIRECT_ACTION + FUNCTION_URI + "/" + form.getWorkDate() + "/input";
		}

		//更新用entity作成。
		String userId = getLoginUserId();
		if(!StringUtils.isEmpty(form.getUserId())){
			userId = form.getUserId();
		}

		WorkTime entity = form.toEntity();
		entity.setUserId(userId);
		entity.setHoridayType(calendarComponent.getHoridayType(form.getWorkDateAsLocalDate()));

		//更新
		workTimeService.update(entity);

		MessageInfo messageInfo = messagehandler.getMessage(MessageId.M004, null, null, null);
		attributes.addFlashAttribute("result", messageInfo.getMessage());
		String param = DateUtils.convert(DateUtils.convertToLocalDate(form.getWorkDate()), DateTimeFormat.UUUU_MM);
		return REDIRECT_ACTION + FUNCTION_URI + ROOT_URI + param;
	}
	
	/**
	 * ダウンロード。
	 * @param month 年月(yyyy-mm)
	 * @return ダウンロードリソース
	 */
	@RequestMapping("/download/{month}")
	public ResponseEntity<byte[]> download(@DateValid(pattern="uuuu-MM") @NotEmpty @PathVariable String month){
		String userId = getLoginUserId();
		LocalDate from_date = DateUtils.getFirstDayOfMonth(month + "-01");
		LocalDate to_date = DateUtils.getFinalDayOfMonth(month + "-01");
		byte[] bytes = workTimeService.createCsv(userId, from_date, to_date);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.set("Content-Disposition", "filename=\""+month + ".csv\"");

		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}
}
