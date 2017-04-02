package jp.co.orangearch.workmanage.domain.dao;

import java.time.LocalDateTime;

import org.seasar.doma.Dao;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.builder.SelectBuilder;

@ConfigAutowireable
@Dao
public interface DualDao {
	
	default LocalDateTime getSystemDatetime(){
		 Config config = Config.get(this);
	        SelectBuilder builder = SelectBuilder.newInstance(config);
	        builder.sql("select now() from dual");
	        return builder.getScalarSingleResult(LocalDateTime.class);
	}
	
}
