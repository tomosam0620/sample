package jp.co.orangearch.workmanage.common.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.orangearch.workmanage.common.validator.impl.EnumValidValidator;
import jp.co.orangearch.workmanage.domain.constant.Values;


/**
 * enumチェック用のバリデータインターフェースクラスです。
 * <br>
 * enumのvalueとして妥当かチェックするフィールド、あるいは、パラメータに本アノテーションを付与して下さい。<br>
 * 除外要素を指定することで、enumの要素のうち除外要素を含まない要素に該当する値かチェックを行います。<br>
 * 空文字、nullはチェックしません。<br>
 * @author t-otsuka
 *
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidValidator.class)
public @interface EnumValue {
	/** エラーメッセージ。 */
	String message() default "{V001}";

	/** グループ。 */
	Class<?>[] groups() default {};

	/** ペイロード。 */
	Class<? extends Payload>[] payload() default {};

	/** enumの型。 */
	Class<? extends Values> type();
	/** 除外要素。 */
	String[] exclude() default {};
}
