package jp.co.orangearch.workmanage.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import jp.co.orangearch.workmanage.common.constant.MessageId;
import jp.co.orangearch.workmanage.common.exception.BussinessException;
import jp.co.orangearch.workmanage.common.validator.CheckToken;
import jp.co.orangearch.workmanage.common.validator.GenerateToken;


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
    @Autowired
    private TokenHandler tokenHandler;

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

    		if(!tokenHandler.isValid(token, request)){
    			throw new BussinessException(MessageId.M001.getValue(), new String[]{request.getRequestURI()});
    		}

    		if(annotation.remove()){
    			tokenHandler.remove(request);
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
    		String token = tokenHandler.generate(request.getSession().getId(), request);
    		modelAndView.addObject(TOKEN_NAME, token);
    	}

    	super.postHandle(request, response, handler, modelAndView);
    }

}