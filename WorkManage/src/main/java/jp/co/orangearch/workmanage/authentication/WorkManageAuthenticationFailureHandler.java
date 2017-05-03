package jp.co.orangearch.workmanage.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class WorkManageAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		if(exception instanceof CredentialsExpiredException){
			String userId = request.getParameter("username");
			response.sendRedirect("/passwordChange/?userId=" + userId);
		}else{
			setDefaultFailureUrl("/login");
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}
