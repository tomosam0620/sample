package jp.co.orangearch.workmanage.controller.horidayManage;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.orangearch.workmanage.common.validator.CheckToken;
import jp.co.orangearch.workmanage.common.validator.GenerateToken;
import jp.co.orangearch.workmanage.controller.AbstractWorkManageController;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.entity.Horiday;
import jp.co.orangearch.workmanage.domain.exception.CsvHandleException;
import jp.co.orangearch.workmanage.domain.exception.SystemException;
import jp.co.orangearch.workmanage.domain.logger.ApplicationLogger;
import jp.co.orangearch.workmanage.domain.logger.MessageHandler;
import jp.co.orangearch.workmanage.domain.logger.MessageInfo;
import jp.co.orangearch.workmanage.form.horidayManage.FileUploadForm;
import jp.co.orangearch.workmanage.service.HoridayCsvBean;
import jp.co.orangearch.workmanage.service.HoridayManageService;

/**
 * 休日管理機能のコントローラークラスです。
 * @author t-otsuka
 *
 */
@Controller
@RequestMapping(HoridayManageController.FUNCTION_URI)
public class HoridayManageController extends AbstractWorkManageController{
	/** 機能のURI */
	public static final String FUNCTION_URI = "/admin/horidayManage";
	
	/** indexページのURI */
	private static final String INDEX_URI = "/";
	/** upload画面のURI */
	private static final String UPLOAD_URI = "/upload";
	/** 更新処理のURI */
	private static final String HANDLE_URI = "/handle";
	
	/** index画面のHTMLファイルパス */
	private static final String SHOW_HTML = "/horidayManage/index";
	/** upload画面のHTMLファイルパス */
	private static final String UPLOAD_HTML = "/horidayManage/upload";
	
	/** ロガー。 */
	@Autowired
	private ApplicationLogger applicationLogger;

	/** 休日管理サービス。 */
	@Autowired
	private HoridayManageService horidayManageService;

	/** メッセージ管理 */
	@Autowired
	private MessageHandler messagehandler;
	
	/**
	 * ｉｎｄｅｘページ表示処理
	 * <br>
	 * 登録されている休日情報を取得し、表示します。
	 * @param model モデル
	 * @return 遷移先ページのHTMLファイルパス
	 */
	@RequestMapping(value=INDEX_URI, method=RequestMethod.GET)
	public String index(Model model) {
		List<Horiday> horidays = horidayManageService.selectAll();
		model.addAttribute("horidays", horidays);
		return SHOW_HTML;
	}

	/**
	 * upload画面表示処理
	 * <br>
	 * upload画面を表示します。
	 * @return 遷移先ページのHTMLファイルパス
	 */
	@GenerateToken
	@RequestMapping(value=UPLOAD_URI, method=RequestMethod.GET)
	public String upload() {
		return UPLOAD_HTML;
	}

	/**
	 * 更新処理
	 * @param fileUploadForm フォーム
	 * @param attributes リダイレクト先に渡すパラメータを格納するクラス
	 * @return 遷移先
	 */
	@CheckToken
	@RequestMapping(value = HANDLE_URI, method = RequestMethod.POST)
	public String handle(FileUploadForm fileUploadForm, RedirectAttributes attributes) {
		applicationLogger.log(MessageId.M003, new String[]{fileUploadForm.getFileData().getName(), String.valueOf(fileUploadForm.getFileData().getSize())});
		try {
			List<HoridayCsvBean> lines = horidayManageService.read(fileUploadForm.getFileData().getInputStream());
			
			horidayManageService.update(lines);
			
		} catch (CsvHandleException e) {
			attributes.addFlashAttribute("messages", e.getErrors());
			return REDIRECT_ACTION + FUNCTION_URI + UPLOAD_URI;
			
		} catch (IOException e) {
			throw new SystemException(MessageId.S003, e);
		}
		
		MessageInfo messageInfo = messagehandler.getMessage(MessageId.M004, null, null, null);
		attributes.addFlashAttribute("result", messageInfo.getMessage());
		return REDIRECT_ACTION + FUNCTION_URI + INDEX_URI;
	}
}