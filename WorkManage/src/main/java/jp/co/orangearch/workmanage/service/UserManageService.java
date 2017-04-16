package jp.co.orangearch.workmanage.service;

import java.util.List;
import java.util.Optional;

import jp.co.orangearch.workmanage.domain.entity.join.JoinProjectUser;

public interface UserManageService {
	List<JoinProjectUser> selectAll();

	List<JoinProjectUser> select(String roleId, Integer affiliation, Integer position, Integer projectId);

	Optional<JoinProjectUser> select(String userId);
}
