package jp.co.orangearch.workmanage.listener;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureDisabledEvent;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import jp.co.orangearch.workmanage.domain.dao.UserDao;
import jp.co.orangearch.workmanage.domain.entity.User;

@Component
public class AuthenticationEventListener {

	@Autowired
	private UserDao userDao;
	
	@EventListener
	public void handleEvent(AuthenticationSuccessEvent event){
		Optional<User> entity = userDao.selectById(event.getAuthentication().getName());
		entity.get().setPasswordMissCount(0);
		userDao.update(entity.get());
	}

	@EventListener
	public void handleEvent(AuthenticationFailureBadCredentialsEvent event){
		Optional<User> entity = userDao.selectById(event.getAuthentication().getName());
		entity.get().setPasswordMissCount(entity.get().getPasswordMissCount() + 1);
		userDao.update(entity.get());
	}

	@EventListener
	public void handleEvent(AuthenticationFailureLockedEvent event){
		//TODO message change. 誤入力回数オーバーです。
		System.out.println("receive AuthenticationFailureLockedEvent");
	}

	@EventListener
	public void handleEvent(AuthenticationFailureDisabledEvent event){
		//nop。 あえて削除されていることを見せない。
		System.out.println("receive AuthenticationFailureDisabledEvent");
	}

}
