package jp.co.orangearch.workmanage.interceptor;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
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

    private static final Logger logger = LoggerFactory.getLogger(LogFileMarker.OPE);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

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
    	MDC.put("SESSION_ID", request.getSession().getId());
    	MDC.put("USER_ID", userId);
    	MDC.put("URL", request.getRequestURI());
    	MDC.put("METHOD", request.getMethod());

    	StringBuilder builder = new StringBuilder();
    	builder.append("[");
    	boolean isFirst = true;
        for(String name : Collections.list(request.getParameterNames())){
        	if(!isFirst){
        		builder.append(", ");
        	}
        	//TODO:パスワード変更画面を作ると、パラメータにパスワードが入ってくるので、マスクが必要。
        	builder.append(name +"="+ request.getParameter(name));
        	isFirst = false;
        }
    	builder.append("]");
    	logger.info(builder.toString());

        return super.preHandle(request, response, handler);
    }

}