package jp.co.orangearch.workmanage.common.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * トークン生成を行うアノテーション用インターフェースクラスです。<br>
 *
 * トークン生成を行うメソッドに本アノテーションを付与することで、
 * トークン生成を行います。
 *
 * @see {@link CheckToken}
 * @author t-otsuka
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GenerateToken {

}
