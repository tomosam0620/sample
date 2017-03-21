package jp.co.orangearch.workmanage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.orangearch.workmanage.dao.UserDao;
import jp.co.orangearch.workmanage.entity.User;
import jp.co.orangearch.workmanage.service.LoginUserInfo;

/**
 * ログイン認証時のユーザー情報を取得するサービスクラスです。
 *
 * @author t-otsuka
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	     // 認証を行うユーザー情報を格納する
        User user = null;
        try {
            // 入力したユーザーIDから認証を行うユーザー情報を取得する
        	user = userDao.selectById(username);

            // 処理内容は省略
        } catch (Exception e) {
            // 取得時にExceptionが発生した場合
            throw new UsernameNotFoundException("It can not be acquired User");
        }

        // ユーザー情報を取得できなかった場合
        if(user == null){
            throw new UsernameNotFoundException("User not found for login id: " + username);
        }

        // ユーザー情報が取得できたらSpring Securityで認証できる形で戻す
        return new LoginUserInfo(user);
	}

}