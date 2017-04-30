package jp.co.orangearch.workmanage.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.seasar.doma.jdbc.SelectOptions;

import jp.co.orangearch.workmanage.domain.entity.join.JoinProjectUser;

public interface UserManageService {
	List<JoinProjectUser> selectAll();

	List<JoinProjectUser> select(String roleId, Integer affiliation, Integer position, Integer projectId);

	Optional<JoinProjectUser> select(String userId);
	
	Optional<JoinProjectUser> select(Integer affiliationCd, Integer projectId, String userId,
			LocalDate fromDate, LocalDate toDate);
}
