package jp.co.orangearch.workmanage.common.validator.impl;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import jp.co.orangearch.workmanage.common.constant.Values;
import jp.co.orangearch.workmanage.common.validator.EnumValue;


/**
 * enumの妥当性チェックを行うバリデータクラスです。
 * <br>
 * EnumValidアノテーションを付与された文字列について、指定されたENUMにvalueが等しい要素が存在するかチェックを行います。<br>
 * 文字列が空文字やnullの場合はチェックを行いません。
 * @author t-otsuka
 *
 */
public class EnumValidValidator implements ConstraintValidator<EnumValue, String> {

	/** ENUMの型。 */
	private Class<? extends Values> pattern;

	/** 除外要素。 */
	private String[] exclude = null;

	@Override
	public void initialize(EnumValue constraintAnnotation) {
		pattern = constraintAnnotation.type();
		exclude = constraintAnnotation.exclude();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// 未入力はチェックしない。
		if(StringUtils.isEmpty(value)){
			return true;
		}

		if(!ObjectUtils.isEmpty(exclude) && Arrays.asList(exclude).contains(value)){
			return false;
		}


		return Arrays.asList(pattern.getEnumConstants()).stream().anyMatch(v -> v.getValue().equals(value));
	}

}
