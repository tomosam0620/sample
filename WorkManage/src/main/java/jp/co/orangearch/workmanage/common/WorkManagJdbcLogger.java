package jp.co.orangearch.workmanage.common;

import org.seasar.doma.jdbc.Sql;
import org.seasar.doma.jdbc.UtilLoggingJdbcLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SQLログを出力するロガークラスです。
 * 
 * @author t-otsuka
 *
 */
public class WorkManagJdbcLogger extends UtilLoggingJdbcLogger{

	/** ロガー */
	private static final Logger logger = LoggerFactory.getLogger(WorkManagJdbcLogger.class);

	/**
	 * 実行したSQLをログ出力します。
	 */
	@Override
	public void logSql(String callerClassName, String callerMethodName, Sql<?> sql) {
		logger.info(sql.getFormattedSql());
	}
	
	/**
	 * Daoメソッドが呼び出されたことを受け付けます。
	 * 処理なし。
	 */
	@Override
	public void logDaoMethodEntering(String callerClassName, String callerMethodName, Object... args) {
	}

	/**
	 * Daoメソッドが終了されたことを受け付けます。
	 * 処理なし。
	 */
	@Override
	public void logDaoMethodExiting(String callerClassName, String callerMethodName, Object result) {
	}
	
	/**
	 * Daoメソッドが例外で終了されたことを受け付けます。
	 */
    @Override
    public void logDaoMethodThrowing(String callerClassName, String callerMethodName, RuntimeException e) {
		logger.error(e.getMessage());
    }
}
