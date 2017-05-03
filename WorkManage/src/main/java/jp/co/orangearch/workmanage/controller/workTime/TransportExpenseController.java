package jp.co.orangearch.workmanage.controller.workTime;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.orangearch.workmanage.common.validator.CheckToken;
import jp.co.orangearch.workmanage.common.validator.DateValid;
import jp.co.orangearch.workmanage.common.validator.GenerateToken;
import jp.co.orangearch.workmanage.component.util.DateUtils;
import jp.co.orangearch.workmanage.component.util.DateUtils.DateTimeFormat;
import jp.co.orangearch.workmanage.controller.AbstractWorkManageController;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.constant.OnewayRoundType;
import jp.co.orangearch.workmanage.domain.constant.TransportionType;
import jp.co.orangearch.workmanage.domain.entity.TransportionExpense;
import jp.co.orangearch.workmanage.domain.logger.MessageHandler;
import jp.co.orangearch.workmanage.domain.logger.MessageInfo;
import jp.co.orangearch.workmanage.form.workTime.TransportExpenseForm;
import jp.co.orangearch.workmanage.service.TransportExpenseService;


/**
 * 勤務時間のコントローラクラスです。
 *
 * @author t-otsuka
 *
 */
@Controller
@Validated
@RequestMapping(TransportExpenseController.FUNCTION_URI)
public class TransportExpenseController extends AbstractWorkManageController{

	/** 入力画面のhtmlファイルパス */
	private static final String INPUT_HTML = "transportExpense/input";
	/** 機能のURI */
	public static final String FUNCTION_URI = "/transportExpense";
	/** 一覧表示画面のURI */
	private static final String ROOT_URI = "/";
	/** 入力画面のURI */
	private static final String INPUT_URI = "/input";
	/** 更新画面のURI */
	private static final String UPDATE_URI = "/{date}/{number}/update";
	/** 更新処理のURI */
	private static final String HANDLE_URI = "/handle";

	//modelのキー名
	/** form名 */
	private static final String FORM_NAME = "transportExpenseForm";
	/** エラー情報キー名 */
	private static final String ERROR_OBJECT_NAME = "error";
	/** 交通手段選択状態キー名 */
	private static final String SELECTED_TRANSPORT_TYPE_NAME = "registedTransportType";
	/** 片道往復選択状態キー名 */
	private static final String SELECTED_ONEWAY_ROUND_TYPE_NAME = "registedOnewayRoundType";

	/** 勤務時間サービス。 */
	@Autowired
	private TransportExpenseService transportExpenseService;

	/** メッセージ管理。 */
	@Autowired
	private MessageHandler messagehandler;
	
	@GenerateToken
	@RequestMapping(value=INPUT_URI, method=RequestMethod.GET)
	public String input(Model model){
		//更新用entity作成。
		TransportExpenseForm form = new TransportExpenseForm();
		form.setUserId(getLoginUserId());
		form.setTransportNumber("0");
		
		model.addAttribute(SELECTED_TRANSPORT_TYPE_NAME, TransportionType.電車.getKey());
		model.addAttribute(SELECTED_ONEWAY_ROUND_TYPE_NAME, OnewayRoundType.片道.getKey());
		model.addAttribute(FORM_NAME, form);
		return INPUT_HTML;
	}
	
	/**
	 * 交通費登録画面を表示します。
	 * 
	 * @param date 日付
	 * @param number 通番
	 * @param model モデル
	 * @return 遷移先
	 */
	@GenerateToken
	@RequestMapping(value=UPDATE_URI, method=RequestMethod.GET)
	public String update(@DateValid(pattern="uuuu-MM-dd") @PathVariable String date, @PathVariable Integer number, Model model){
		Optional<TransportionExpense> transportExpense = transportExpenseService.select(getLoginUserId(), DateUtils.convertToLocalDate(date), number);
		TransportExpenseForm form = new TransportExpenseForm();
		form.setWorkDate(date);

		if(transportExpense.isPresent()){
			form = new TransportExpenseForm(transportExpense.get());
		}
		
		//モデルにエラー情報が存在していれば、フォームの入力エラー情報としてモデルに登録
		if(model.containsAttribute(ERROR_OBJECT_NAME)){
			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + FORM_NAME, model.asMap().get(ERROR_OBJECT_NAME));
			form = TransportExpenseForm.class.cast(model.asMap().get(FORM_NAME));
			if(transportExpense.isPresent()){
				form.setVersion(transportExpense.get().getVersion());
			}
		}

		model.addAttribute(SELECTED_TRANSPORT_TYPE_NAME, form.getTransportType());
		model.addAttribute(SELECTED_ONEWAY_ROUND_TYPE_NAME, form.getOnewayRoundType());
		model.addAttribute(FORM_NAME, form);
		return INPUT_HTML;
	}

	/**
	 * 交通費の登録を行います。
	 *
	 * @param form フォーム
	 * @param bindingResult 入力チェックエラー情報
	 * @param model モデル
	 * @param attributes リダイレクト先に渡すパラメータ情報
	 * @return 遷移先
	 */
	@CheckToken
	@RequestMapping(value=HANDLE_URI, method=RequestMethod.POST)
	public String handle(@ModelAttribute(FORM_NAME) @Validated TransportExpenseForm form, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
		//入力チェック。
		if(bindingResult.hasErrors()){
			attributes.addFlashAttribute(FORM_NAME, form);
			attributes.addFlashAttribute(ERROR_OBJECT_NAME, bindingResult);
			return REDIRECT_ACTION + FUNCTION_URI + INPUT_URI;
		}

		//更新用entity作成。
		String userId = getLoginUserId();
		if(!StringUtils.isEmpty(form.getUserId())){
			userId = form.getUserId();
		}

		TransportionExpense entity = form.toEntity();
		entity.setUserId(userId);

		//更新
		transportExpenseService.update(entity, bindingResult);
		if(bindingResult.hasErrors()){
			attributes.addFlashAttribute(ERROR_OBJECT_NAME, bindingResult);
			return REDIRECT_ACTION + FUNCTION_URI + "/" + form.getWorkDate() + "/input";
		}

		MessageInfo messageInfo = messagehandler.getMessage(MessageId.M004, null, null, null);
		attributes.addFlashAttribute("result", messageInfo.getMessage());
		String param = DateUtils.convert(DateUtils.convertToLocalDate(form.getWorkDate()), DateTimeFormat.UUUU_MM);
		return REDIRECT_ACTION + "/workTime" + ROOT_URI + param + ROOT_URI + userId;
	}
	
}
