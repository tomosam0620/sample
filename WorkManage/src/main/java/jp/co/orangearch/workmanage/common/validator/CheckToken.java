package jp.co.orangearch.workmanage.common.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * トークンチェックを行うアノテーション用インターフェースクラスです。
 * <br>
 * トークンチェックを行うメソッドに本アノテーションを付与することで、
 * トークンチェックを行います。
 *
 * @see {@link CheckToken}
 * @author t-otsuka
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckToken {

	/** チェック後のトークン削除フラグ(デフォルトではtrue) */
	boolean remove() default true;

}
