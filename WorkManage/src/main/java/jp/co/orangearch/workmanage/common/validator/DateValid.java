package jp.co.orangearch.workmanage.common.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.orangearch.workmanage.common.validator.impl.DateValidValidator;


/**
 * 日時書式チェック用のバリデータインターフェースクラスです。
 * <br>
 * 日付として妥当かチェックする日時文字列のフィールド、あるいは、パラメータに本アノテーションを付与して下さい。<br>
 * 書式をパラメータ（pattern）に指定することで、指定した書式でチェックを行います。<br>
 * 空文字、nullはチェックしません。<br>
 * 書式には"yyyy/MM/dd HH:mm:ss.SSS"、"uuuu-MM-dd"など({@link DateTimeFormat}参照)の文字列を指定して下さい。
 * @see {@link LocalDate}、{@linkplain DateTimeFormatter}
 * @author t-otsuka
 *
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateValidValidator.class)
public @interface DateValid {
	/** エラーメッセージ。 */
	String message() default "{V001}";

	/** グループ。 */
	Class<?>[] groups() default {};

	/** ペイロード。 */
	Class<? extends Payload>[] payload() default {};

	/** 書式。 */
	String pattern() default "uuuu-MM-dd";
}
