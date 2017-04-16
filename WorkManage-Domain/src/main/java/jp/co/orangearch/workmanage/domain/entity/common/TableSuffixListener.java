package jp.co.orangearch.workmanage.domain.entity.common;

import java.time.LocalDateTime;

import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;


public class TableSuffixListener <T extends TableSuffix> implements EntityListener<T> {
	private static final String MDC_KEY_USER_ID = "USER_ID";
	private static final String NOT_LOGIN_USER = "anonymousUser";
	@Override
	public void preInsert(T entity, PreInsertContext<T> context) {
		String userId = MDC.get(MDC_KEY_USER_ID);
		entity.setRegistUser(userId);
		entity.setUpdateUser(userId);
		entity.setVersion(0);
		EntityListener.super.preInsert(entity, context);
	}
	@Override
	public void preUpdate(T entity, PreUpdateContext<T> context) {
		String userId = MDC.get(MDC_KEY_USER_ID);
		if(!StringUtils.isEmpty(userId) && !NOT_LOGIN_USER.equals(userId)){
			entity.setUpdateUser(userId);
		}
		entity.setUpdateDate(LocalDateTime.now());
		EntityListener.super.preUpdate(entity, context);
	}

}
