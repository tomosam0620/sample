package jp.co.orangearch.workmanage.entity.listener;

import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;

import jp.co.orangearch.workmanage.entity.TableSuffix;

public class TableSuffixListener <T extends TableSuffix> implements EntityListener<T> {
	@Override
	public void preInsert(T entity, PreInsertContext<T> context) {
		entity.setRegistUser("common");
		entity.setUpdateUser("common");
		entity.setVersion(0);
		EntityListener.super.preInsert(entity, context);
	}
	@Override
	public void preUpdate(T entity, PreUpdateContext<T> context) {
		entity.setUpdateUser("common2");
		EntityListener.super.preUpdate(entity, context);
	}

}
