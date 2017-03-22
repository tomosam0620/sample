package jp.co.orangearch.workmanage.controller.horidayManage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.orangearch.workmanage.common.constant.LogFileMarker;
import jp.co.orangearch.workmanage.common.constant.MessageId;
import jp.co.orangearch.workmanage.common.exception.CsvError;
import jp.co.orangearch.workmanage.common.exception.CsvHandleException;
import jp.co.orangearch.workmanage.common.exception.SystemException;
import jp.co.orangearch.workmanage.common.validator.CheckToken;
import jp.co.orangearch.workmanage.common.validator.GenerateToken;
import jp.co.orangearch.workmanage.controller.AbstractWorkManageController;
import jp.co.orangearch.workmanage.entity.Horiday;
import jp.co.orangearch.workmanage.form.horidayManage.FileUploadForm;
import jp.co.orangearch.workmanage.service.HoridayCsvBean;
import jp.co.orangearch.workmanage.service.HoridayManageService;

@Controller
@RequestMapping(HoridayManageController.FUNCTION_URI)
public class HoridayManageController extends AbstractWorkManageController{
	/** 機能のURI */
	public static final String FUNCTION_URI = "/admin/horidayManage";
	
	private static final String ROOT_URI = "/";
	private static final String HANDLE_URI = "/handle";
	private static final String UPLOAD_URI = "/upload";
	
	private static final String UPLOAD_HTML = "/horidayManage/upload";
	private static final String SHOW_HTML = "/horidayManage/index";
	
	private static final Logger log = LoggerFactory.getLogger(LogFileMarker.APP); 

	@Autowired
	private HoridayManageService horidayManageService;
	
	@RequestMapping(value=ROOT_URI, method=RequestMethod.GET)
	public String index(Model model) {
		List<Horiday> horidays = horidayManageService.selectAll();
		model.addAttribute("horidays", horidays);
		return SHOW_HTML;
	}

	@GenerateToken
	@RequestMapping(value=UPLOAD_URI, method=RequestMethod.GET)
	public String upload() {
		return UPLOAD_HTML;
	}

	@CheckToken
	@RequestMapping(value = HANDLE_URI, method = RequestMethod.POST)
	public String handle(FileUploadForm fileUploadForm, RedirectAttributes attributes) {
		log.info(fileUploadForm.getFileData().getName() + ", " + fileUploadForm.getFileData().getSize());
		
		try {
			List<HoridayCsvBean> lines = horidayManageService.read(fileUploadForm.getFileData().getInputStream());
			
			horidayManageService.update(lines);
			
		} catch (CsvHandleException e) {
			List<String> messages = new ArrayList<String>();
			for(CsvError error : e.getErrors()){
				messages.add(error.lineNum + ":" + error.message);
			}
			attributes.addFlashAttribute("messages", messages);
			return REDIRECT_ACTION + FUNCTION_URI + UPLOAD_URI;
			
		} catch (IOException e) {
			throw new SystemException(MessageId.S003, e);
		}
		
		attributes.addFlashAttribute("result","登録しました。");
		return REDIRECT_ACTION + FUNCTION_URI + ROOT_URI;
	}
}