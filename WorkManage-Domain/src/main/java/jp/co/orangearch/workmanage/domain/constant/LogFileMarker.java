package jp.co.orangearch.workmanage.domain.constant;

/**
 * ログファイル識別用のマーカーを定義するクラスです。
 *
 * @author t-otsuka
 *
 */
public final class LogFileMarker{
	/** applicationログ用マーカー。 */
	// ファイル分けたいだけだから、Markerにしなくても、logger取得時の文字列で十分。
	// TODO:階層化したければ要Marker対応
//	public static final Marker APP = MarkerFactory.getMarker("application");
	public static final String APP = "application";

	/** DBログ用マーカー。 */
//	public static final Marker DB = MarkerFactory.getMarker("db");
	public static final String DB = "db";

	/** operationnログ用マーカー。 */
//	public static final Marker OPE = MarkerFactory.getMarker("operation");
	public static final String OPE = "operation";

}
