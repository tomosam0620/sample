package jp.co.orangearch.workmanage.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.orangearch.workmanage.domain.dao.UserDao;
import jp.co.orangearch.workmanage.domain.entity.User;
import jp.co.orangearch.workmanage.service.LoginUserInfo;

/**
 * ログイン認証時のユーザー情報を取得するサービスクラスです。
 *
 * @author t-otsuka
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	/** 最大誤入力回数 */
	private int maxPasswordMissCount = 5;
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	     // 認証を行うユーザー情報を格納する
        Optional<User> user = null;
        try {
            // 入力したユーザーIDから認証を行うユーザー情報を取得する
        	user = userDao.selectById(username);
        } catch (Exception e) {
            // 取得時にExceptionが発生した場合
            throw new UsernameNotFoundException("It can not be acquired User");
        }

        // ユーザー情報を取得できなかった場合
        if(!user.isPresent()){
            throw new UsernameNotFoundException("User not found for login id: " + username);
        }

        boolean isAccountNonExpired = true;
        boolean isAccountNonLocked = true;
        boolean isCredentialsNonExpired = true;
        boolean isEnabled = true;
        
        if(user.get().getDeleteFlag() == 1){
        	isEnabled = false;
        }
        
        if(user.get().getPasswordMissCount() > maxPasswordMissCount){
        	isAccountNonLocked = false;
        }
        // ユーザー情報が取得できたらSpring Securityで認証できる形で戻す
        return new LoginUserInfo(user.get(), 
        		isAccountNonExpired, 
        		isAccountNonLocked, 
        		isCredentialsNonExpired, 
        		isEnabled);
	}

}
