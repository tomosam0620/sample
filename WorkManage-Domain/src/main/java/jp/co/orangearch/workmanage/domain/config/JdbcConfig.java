package jp.co.orangearch.workmanage.domain.config;

import javax.sql.DataSource;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.JdbcLogger;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.MysqlDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.orangearch.workmanage.domain.logger.MessageHandler;
import jp.co.orangearch.workmanage.domain.logger.WorkManagJdbcLogger;

//@EnableTransactionManagement
@Configuration
public class JdbcConfig implements Config {

	@Autowired
	private DataSource dataSource;

	@Bean
	public MysqlDialect getMySqlDialect(){
		return new MysqlDialect();
	}

	@Override
	public Dialect getDialect() {
		return getMySqlDialect();
	}
	
	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

	@Override
	public JdbcLogger getJdbcLogger(){
		return new WorkManagJdbcLogger();
	}
	
	@Bean
	public MessageHandler messageHandler(){
		return new MessageHandler();
	}
}
