package jp.co.orangearch.workmanage.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import jp.co.orangearch.workmanage.component.CalendarComponent;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.dao.UserDao;
import jp.co.orangearch.workmanage.domain.entity.User;
import jp.co.orangearch.workmanage.domain.exception.SystemException;
import jp.co.orangearch.workmanage.service.PasswordChangeService;


/**
 * パスワード変更サービスの実相クラスです。
 *
 * @author t-otsuka
 *
 */
@Service
public class PasswordChangeServiceImpl implements PasswordChangeService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private CalendarComponent calendarComponent;

	/** パスワードエンコーダー。 */
	@Autowired
	private ShaPasswordEncoder passwordEncorder;

	@Override
	public Integer selectUserVersion(String userId){
		Optional<User> userInfo = userDao.selectById(userId);
		if(!userInfo.isPresent()){
			throw new SystemException(MessageId.S003);
		}
		return userInfo.get().getVersion();
	}

	@Transactional
	@Override
	public void update(String userId, String password, String newPassword, Integer version, BindingResult bindingResult) {
		Optional<User> userInfo = userDao.selectById(userId);
		if(!userInfo.isPresent()){
			throw new SystemException(MessageId.S003);
		}
		User entity = userInfo.get();
		//パスワードが誤っていればエラー情報をリダイレクト先に引き継いでパスワード変更画面にリダイレクト
		if(!passwordEncorder.isPasswordValid(entity.getPassword(), password, userId)){
			bindingResult.rejectValue("password", "V005");
			return;
		}
		
		String encodedNewPassword = passwordEncorder.encodePassword(newPassword, userId);
		entity.setPassword(encodedNewPassword);
		entity.setPasswordlastChangeDate(calendarComponent.getSystemDate());
		entity.setPasswordMissCount(0);
		entity.setPasswordInitialFlag(0);
		entity.setVersion(version);
		userDao.update(entity);
	}

}
