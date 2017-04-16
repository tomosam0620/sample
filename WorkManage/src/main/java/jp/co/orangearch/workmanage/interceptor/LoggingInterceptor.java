package jp.co.orangearch.workmanage.interceptor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import jp.co.orangearch.workmanage.domain.constant.LogFileMarker;

/**
 * ログ出力を行うinterceptorです。
 *
 * @author t-otsuka
 *
 */
@Component
public class LoggingInterceptor extends HandlerInterceptorAdapter {

	private static final String MDC_KEY_SESSION_ID = "SESSION_ID";
	private static final String MDC_KEY_USER_ID = "USER_ID";
	private static final String MDC_KEY_URI = "URL";
	private static final String MDC_KEY_METHOD = "METHOD";
	private static final String MDC_KEY_IN_OUT = "INOUT";
	private static final String START_ATTRIBUTE_KEY = "@proc_start_time";
	private static final Logger logger = LoggerFactory.getLogger(LogFileMarker.OPE);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		LocalDateTime from = LocalDateTime.now();
		request.setAttribute(START_ATTRIBUTE_KEY, from);

		String userId = null;
		try{
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (principal instanceof UserDetails) {
				userId = ((UserDetails)principal).getUsername();
			} else {
				userId = principal.toString();
			}
		}catch(Exception e){
			//セッションなしでのアクセス。ログ出力処理のため、処理なし。
		}

		//ログ用にMDCに設定
		MDC.put(MDC_KEY_SESSION_ID, request.getSession().getId());
		MDC.put(MDC_KEY_USER_ID, userId);
		MDC.put(MDC_KEY_URI, request.getRequestURI());
		MDC.put(MDC_KEY_METHOD, request.getMethod());
		MDC.put(MDC_KEY_IN_OUT, "I");

		StringBuilder builder = new StringBuilder();
		builder.append("[");
		boolean isFirst = true;
		for(String name : Collections.list(request.getParameterNames())){
			if(!isFirst){
				builder.append(", ");
			}
			String value = request.getParameter(name);
			//パスワードはマスクする。
			if(name.matches(".*[p|P]assword$")){
				value = "********";
			}
			builder.append(name +"="+ value);
			isFirst = false;
		}
		builder.append("]");
		logger.info(builder.toString());

		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		LocalDateTime from = (LocalDateTime) request.getAttribute(START_ATTRIBUTE_KEY);
		LocalDateTime to = LocalDateTime.now();
		Duration duration = Duration.between(from, to);
		MDC.put(MDC_KEY_IN_OUT, "O");
		logger.info("[" + duration.getSeconds() + "." + String.format("%03d", duration.getNano()/1000000) + "]");
		super.postHandle(request, response, handler, modelAndView);
	}
}