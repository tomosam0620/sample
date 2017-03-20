package jp.co.orangearch.workmanage.common.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import jp.co.orangearch.workmanage.common.util.DateUtils;
import jp.co.orangearch.workmanage.common.util.DateUtils.DateTimeFormat;
import jp.co.orangearch.workmanage.common.validator.DateValid;


/**
 * 日付の妥当性チェックを行うバリデータクラスです。
 * <br>
 * DateValidアノテーションを付与された日付文字列について、妥当性のチェックを行います。<br>
 * 日付文字列が空文字やnullの場合はチェックを行いません。
 * @author t-otsuka
 *
 */
public class DateValidValidator implements ConstraintValidator<DateValid, String> {

	/** 日付書式。 */
	private String pattern;

	@Override
	public void initialize(DateValid constraintAnnotation) {
		pattern = constraintAnnotation.pattern();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// 未入力はチェックしない。
		if(StringUtils.isEmpty(value)){
			return true;
		}

		return DateUtils.isValidDate(value, DateTimeFormat.getInstance(pattern));
	}

}
