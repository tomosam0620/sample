package jp.co.orangearch.workmanage.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.orangearch.workmanage.component.CalendarComponent;
import jp.co.orangearch.workmanage.domain.dao.join.JoinProjectUserDao;
import jp.co.orangearch.workmanage.domain.entity.join.JoinProjectUser;
import jp.co.orangearch.workmanage.service.UserManageService;

@Service
public class UserManageServiceImpl implements UserManageService {

	@Autowired
	private JoinProjectUserDao joinProjectUserDao;

	@Autowired
	private CalendarComponent calendarComponent;

	
	@Override
	public List<JoinProjectUser> selectAll() {
		LocalDate now = calendarComponent.getSystemDate();
		return joinProjectUserDao.selectAll(now);
	}


	@Override
	public List<JoinProjectUser> select(String roleId, Integer affiliation, Integer position, Integer projectId) {
		LocalDate now = calendarComponent.getSystemDate();
		SelectOptions option = SelectOptions.get().limit(1000);
		return joinProjectUserDao.selectByCondition(roleId, affiliation, position, projectId, now, option);
	}


	@Override
	public Optional<JoinProjectUser> select(String userId) {
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

}
