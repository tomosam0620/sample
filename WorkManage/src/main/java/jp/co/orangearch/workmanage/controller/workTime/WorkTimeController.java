package jp.co.orangearch.workmanage.controller.workTime;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import jp.co.orangearch.workmanage.domain.constant.ClosingState;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.entity.TransportionExpense;
import jp.co.orangearch.workmanage.domain.entity.WorkTime;
import jp.co.orangearch.workmanage.domain.entity.WorkTimeStatus;
import jp.co.orangearch.workmanage.domain.entity.WorkTimeType;
import jp.co.orangearch.workmanage.domain.exception.BusinessException;
import jp.co.orangearch.workmanage.domain.logger.MessageHandler;
import jp.co.orangearch.workmanage.domain.logger.MessageInfo;
import jp.co.orangearch.workmanage.dto.WorkTimesOfMonth;
import jp.co.orangearch.workmanage.form.workTime.SelectMonthForm;
import jp.co.orangearch.workmanage.form.workTime.StatusUpdateForm;
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
	@RequestMapping(value=ROOT_URI, method=RequestMethod.GET)
	public String init(Model model) {
		LocalDate date = calendarComponent.getSystemDate();
		String userId = getLoginUserId();
		return show(DateUtils.convert(date, DateTimeFormat.UUUU_MM), userId, model);
	}

	/**
	 * 指定年月の勤務時間を表示します。
	 *
	 * @param month 年月(yyyy-MM形式)
	 * @param model モデル
	 * @return 遷移先
	 */
	@GenerateToken
	@RequestMapping(value=ROOT_URI + "{month}/{userId}", method=RequestMethod.GET)
	public String show(@DateValid(pattern="uuuu-MM") @NotEmpty @PathVariable String month, @PathVariable @Length(min=7, max=7) String userId, Model model) {

		LocalDate showMonthDate =DateUtils.convertToLocalDate(month + "-01");
		AccessScope scope = hasAuthority(null, null, userId);

		if(scope == null){
			throw new AccessDeniedException("アクセス権がありません。");
		}
		
		// 画面表示情報設定
		WorkTimesOfMonth workTimes = workTimeService.selectWorkTimeInfoInMonth(userId, showMonthDate);
		List<TransportionExpense> transportInfos = workTimeService.selectTransportionInfo(userId, showMonthDate);
		Optional<WorkTimeStatus> status = workTimeService.selectStatusVersion(userId, month);
		model.addAttribute("userId", userId);
		model.addAttribute(FORM_NAME, new WorkTimeForm()); //入力用formを設定しておかないと落ちる
		model.addAttribute("workTimes", workTimes.getOperaionTimes());
		model.addAttribute("transportInfos", transportInfos);
		model.addAttribute("currentMonth", new SelectMonthForm(DateUtils.convert(showMonthDate, DateTimeFormat.UUUU_MM)));
		StatusUpdateForm statusUpdateForm = new StatusUpdateForm();
		statusUpdateForm.setUserId(userId);
		statusUpdateForm.setMonth(month);
		statusUpdateForm.setStatus(status.isPresent() ? status.get().getStatus().getKey() : ClosingState.OPEN.getKey());
		statusUpdateForm.setVersion(status.isPresent() ? status.get().getVersion() : null);
		model.addAttribute("StatusUpdateForm", statusUpdateForm);

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
		workTimeService.update(entity, bindingResult);
		if(bindingResult.hasErrors()){
			attributes.addFlashAttribute(FORM_NAME, form);
			attributes.addFlashAttribute(ERROR_OBJECT_NAME, bindingResult);
			return REDIRECT_ACTION + FUNCTION_URI + "/" + form.getWorkDate() + "/input";
		}

		MessageInfo messageInfo = messagehandler.getMessage(MessageId.M004, null, null, null);
		attributes.addFlashAttribute("result", messageInfo.getMessage());
		String param = DateUtils.convert(DateUtils.convertToLocalDate(form.getWorkDate()), DateTimeFormat.UUUU_MM);
		return REDIRECT_ACTION + FUNCTION_URI + ROOT_URI + param + ROOT_URI + userId;
	}
	
	@CheckToken
	@RequestMapping(value="/updateStatus", method=RequestMethod.POST)
	public String updateStatus(@Validated StatusUpdateForm form, BindingResult errors, RedirectAttributes attributes){
		if(errors.hasErrors()){
			throw new BusinessException(MessageId.M001, null);
		}
		if(hasAuthority(null, null, form.getUserId()) == null){
			throw new BusinessException(MessageId.M001, null);
		}
		//更新
		//TODO:message複数の場合に複数行返す。
		String message = messagehandler.getMessage(MessageId.M004, null, null, null).getMessage();
		workTimeService.updateStatus(form.getUserId(), form.getMonth(), form.getStatusAsEnum(), form.getVersion(), errors);
		if(errors.hasErrors()){
			message = "";
			for(ObjectError error : errors.getAllErrors()){
				if(!StringUtils.isEmpty(message)){
					message = message + "\\n";
				}
				message = message + messagehandler.getMessage(MessageId.of(error.getCode()), error.getArguments(), error.getDefaultMessage(), null).getMessage();
			}
		}
		attributes.addFlashAttribute("result", message);
		return REDIRECT_ACTION + FUNCTION_URI + ROOT_URI + form.getMonth() + ROOT_URI + form.getUserId();
	}
	
	/**
	 * ダウンロード。
	 * @param month 年月(yyyy-mm)
	 * @return ダウンロードリソース
	 */
	@RequestMapping("/download/{month}/{userId}")
	public ResponseEntity<byte[]> download(@DateValid(pattern="uuuu-MM") @NotEmpty @PathVariable String month, @PathVariable @NotEmpty @Length(max=7) String userId){
		AccessScope scope = hasAuthority(null, null, userId);

		if(scope == null){
			throw new AccessDeniedException("アクセス権がありません。");
		}
		
		LocalDate from_date = DateUtils.getFirstDayOfMonth(month + "-01");
		LocalDate to_date = DateUtils.getFinalDayOfMonth(month + "-01");
		byte[] bytes = workTimeService.createCsv(userId, from_date, to_date);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.set("Content-Disposition", "filename=\""+month + ".csv\"");

		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}
}
