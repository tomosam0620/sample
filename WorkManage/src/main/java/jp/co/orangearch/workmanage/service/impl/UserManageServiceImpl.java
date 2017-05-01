package jp.co.orangearch.workmanage.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.orangearch.workmanage.component.CalendarComponent;
import jp.co.orangearch.workmanage.domain.constant.Flag;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.dao.JoinProjectDao;
import jp.co.orangearch.workmanage.domain.dao.UserDao;
import jp.co.orangearch.workmanage.domain.dao.join.JoinProjectUserDao;
import jp.co.orangearch.workmanage.domain.entity.JoinProject;
import jp.co.orangearch.workmanage.domain.entity.User;
import jp.co.orangearch.workmanage.domain.entity.join.JoinProjectUser;
import jp.co.orangearch.workmanage.domain.exception.SystemException;
import jp.co.orangearch.workmanage.service.PasswordChangeService;
import jp.co.orangearch.workmanage.service.UserManageService;

@Service
public class UserManageServiceImpl implements UserManageService {

	@Value("${password.initialValue}")
	private String initalPassword;
	
	@Autowired
	private JoinProjectUserDao joinProjectUserDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JoinProjectDao joinProjectDao;

	@Autowired
	private CalendarComponent calendarComponent;

	@Autowired
	private PasswordChangeService passwordChangeService;
	

	@Override
	public List<JoinProjectUser> select(String roleId, Integer affiliation, Integer position, Integer projectId) {
		LocalDate now = calendarComponent.getSystemDate();
		SelectOptions option = SelectOptions.get().limit(1000);
		return joinProjectUserDao.selectByCondition(roleId, affiliation, position, projectId, now, option);
	}


	@Override
	public List<JoinProjectUser> select(String userId) {
		return joinProjectUserDao.selectById(userId);
	}

	@Override
	public Optional<JoinProjectUser> select(Integer affiliationCd, Integer projectId, String userId,
			LocalDate fromDate, LocalDate toDate){
		
		List<JoinProjectUser> userInfo =  joinProjectUserDao.selectByConditions(affiliationCd, projectId, userId,
				fromDate, toDate, SelectOptions.get().limit(1));
		if(userInfo.isEmpty()){
			return Optional.empty();
		}
		return Optional.of(userInfo.get(0));
		
	}

	@Override
	@Transactional
	public void update(User userEntity, JoinProject joinProjectEntity, boolean isInitializePassword){
		Optional<User> userInfo = userDao.selectById(userEntity.getUserId());
		LocalDate now = calendarComponent.getSystemDate();
		if(userInfo.isPresent()){
			User entity =new User();
			try {
				BeanUtils.copyProperties(entity, userInfo.get());
			} catch (Exception e) {
				throw new SystemException(MessageId.S003);
			}
			
			if(isInitializePassword){
				String encryptedPassword = passwordChangeService.encrypt(userEntity.getUserId(), initalPassword);
				entity.setPassword(encryptedPassword);
				entity.setPasswordInitialFlag(1);
				entity.setPasswordMissCount(0);
				entity.setPasswordlastChangeDate(now);
			}
			entity.setAffiliation(userEntity.getAffiliation());
			entity.setDeleteFlag(userEntity.getDeleteFlag());
			entity.setPosition(userEntity.getPosition());
			entity.setRoleId(userEntity.getRoleId());
			entity.setVersion(userEntity.getVersion());
			userDao.update(entity);
		}else{
			String encryptedPassword = passwordChangeService.encrypt(userEntity.getUserId(), initalPassword);
			userEntity.setPassword(encryptedPassword);
			userEntity.setPasswordInitialFlag(1);
			userEntity.setPasswordMissCount(0);
			userEntity.setPasswordlastChangeDate(now);
			userEntity.setDeleteFlag(Flag.FALSE);
			userDao.insert(userEntity);
		}
		
		//プロジェクトテーブル更新
		//参画中のプロジェクトを登録する新規プロジェクトの開始日の前日に終了日を設定して更新
		List<JoinProject> joinProject = joinProjectDao.selectByUserIdAndDate(joinProjectEntity.getUserId(), joinProjectEntity.getStartDate());
		if(joinProject.size() > 0){
			JoinProject registedEntity = joinProject.get(0);
			LocalDate endDate = joinProjectEntity.getStartDate().minusDays(1);
			registedEntity.setEndDate(endDate);
			registedEntity.setRegistUser(registedEntity.getRegistUser());
			registedEntity.setRegistDate(registedEntity.getRegistDate());
			joinProjectDao.update(registedEntity);
		}

		//新規参画プロジェクト登録
		joinProjectDao.insert(joinProjectEntity);
	}
}
