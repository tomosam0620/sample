package jp.co.orangearch.workmanage.domain.config;

import javax.sql.DataSource;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.JdbcLogger;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.MysqlDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import jp.co.orangearch.workmanage.domain.logger.MessageHandler;
import jp.co.orangearch.workmanage.domain.logger.WorkManagJdbcLogger;

/**
 * データベース接続に関する設定を行うconfigクラスです。
 * 
 * @author t-otsuka
 *
 */
@Configuration
public class JdbcConfig implements Config {

	private DataSource dataSource;

	/**
	 * データソース設定。
	 * <br>
	 * DOMA2とspringを連携させてspringのトランザクション管理下になるよう<br>
	 * datasouceを{@link TransactionAwareDataSourceProxy}に渡す。
	 * 
	 * @param dataSource データソース
	 */
	@Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new TransactionAwareDataSourceProxy(dataSource);
    }
	
	/**
	 * mysqlのDialect取得。
	 * <br>
	 * mysqlの方言吸収用のdialectを取得します。
	 * 
	 * @return mysqlのDialect
	 */
	@Bean
	public MysqlDialect getMySqlDialect(){
		return new MysqlDialect();
	}

	/**
	 * dialect取得。
	 * <br>
	 * データベースの方言(dialect)を取得します。<br>
	 * データベース毎にある表現の違いをDOMAに指定するため、データベースに応じたdialectを取得します。
	 */
	@Override
	public Dialect getDialect() {
		return getMySqlDialect();
	}
	
	/**
	 * データソース取得
	 * <br>
	 * データベースに接続するデータソースを取得します。
	 */
	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * JDBCロガー取得
	 * <br>
	 * データベースアクセス用のロガーを取得します。
	 */
	@Override
	public JdbcLogger getJdbcLogger(){
		return new WorkManagJdbcLogger();
	}
	
	/**
	 * ログ出力時に使用するメッセージハンドルクラスを取得します。
	 * 
	 * @return メッセージハンドルクラス
	 */
	@Bean
	public MessageHandler messageHandler(){
		return new MessageHandler();
	}
}
