package jp.co.orangearch.workmanage.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import jp.co.orangearch.workmanage.common.validator.CheckToken;
import jp.co.orangearch.workmanage.common.validator.GenerateToken;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.exception.BusinessException;


/**
 * トークンチェックを行うインターセプターです。<br>
 *
 * トークン生成用の@GenerateTokenが付与されたメソッドが実行された場合にトークン生成を行います。
 * トークンチェック用の@CheckTokenが付与されたメソッドが実行された場合にトークンチェックを行います。
 *
 * @see {@link GenerateToken}
 * @see {@link CheckToken}
 * @author t-otsuka
 *
 */
@Component
public class TokenCheckInterceptor extends HandlerInterceptorAdapter {

	private static final String TOKEN_NAME = "token";
	private static final String SESSION_TOKEN_KEY = "@_token";
	
	/**
	 * トークンチェックを行います。
	 * <br>
	 * トークンチェック用の@CheckTokenが付与されたメソッドが実行された場合にトークンチェックを行います。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		HandlerMethod method = (HandlerMethod) handler;

		CheckToken annotation = method.getMethodAnnotation(CheckToken.class);
		if (annotation != null) {
			String token = request.getParameter(TOKEN_NAME);

			if(!isValid(token, request)){
				throw new BusinessException(MessageId.M001, new String[]{request.getRequestURI()});
			}

			if(annotation.remove()){
				remove(request);
			}
		}

		return super.preHandle(request, response, handler);
	}

	/**
	 * トークン生成を行います。
	 * <br>
	 * トークン生成用の@GenerateTokenが付与されたメソッドが実行された場合にトークン生成を行います。
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		HandlerMethod method = (HandlerMethod) handler;

		GenerateToken annotation = method.getMethodAnnotation(GenerateToken.class);
		if (annotation != null) {
			String token = generate(request.getSession().getId(), request);
			String viewName = modelAndView.getViewName();
			if(!viewName.contains("redirect:")){
				modelAndView.addObject(TOKEN_NAME, token);
			}
		}

		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * トークン生成。
	 * トークンを生成し、sessionに設定します。
	 *
	 * @param namespace トークン名の識別子
	 * @return 生成したトークンの値
	 */
	private String generate(String namespace, HttpServletRequest request) {
		String token = UUID.randomUUID().toString();
		request.getSession().setAttribute(SESSION_TOKEN_KEY, token);
		return token;
	}

	/**
	 * トークンチェック。
	 *
	 * 保持しているトークンとチェックします。
	 *
	 * @param token トークン
	 * @param namespace トークン名の識別子
	 * @return true(正常)／false(異常)
	 */
	private boolean isValid(String token, HttpServletRequest request){
		String registedToken = (String)request.getSession().getAttribute(SESSION_TOKEN_KEY);

		if (StringUtils.isEmpty(token) || StringUtils.isEmpty(registedToken)) {
			return false;
		} else if (!token.equals(registedToken)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * トークン破棄。
	 *
	 * 保持しているトークンを破棄します。
	 *
	 * @param namespace トークン名の識別子
	 */
	private void remove(HttpServletRequest request){
		request.getSession().removeAttribute(SESSION_TOKEN_KEY);
	}

}