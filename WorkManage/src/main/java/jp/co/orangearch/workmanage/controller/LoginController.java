package jp.co.orangearch.workmanage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ログインページのコントローラです。
 *
 * @author t-otsuka
 *
 */
@Controller
public class LoginController {

	public static final String URI = "/login";
	private static final String HTML = "login";

	@RequestMapping(value = URI, method = RequestMethod.GET)
	public String top(Model model){
		return HTML;
	}
}
