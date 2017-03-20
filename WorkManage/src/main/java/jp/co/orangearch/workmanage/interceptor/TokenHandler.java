package jp.co.orangearch.workmanage.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * トークン管理を行うクラスです。
 * sessionに生成したトークンを保存じ、sessionから取り出してチェックを行います。
 *
 * @author t-otsuka
 *
 */
@Component
public class TokenHandler {

	private static final String SESSION_TOKEN_KEY = "@_token";

    /**
     * トークン生成。
     * トークンを生成し、sessionに設定します。
     *
     * @param namespace トークン名の識別子
     * @return 生成したトークンの値
     */
    public String generate(String namespace, HttpServletRequest request) {
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
    public boolean isValid(String token, HttpServletRequest request){
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
    public void remove(HttpServletRequest request){
    	request.getSession().removeAttribute(SESSION_TOKEN_KEY);
    }

}