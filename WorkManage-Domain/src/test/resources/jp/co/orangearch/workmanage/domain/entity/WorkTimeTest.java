package jp.co.orangearch.workmanage.domain.entity;

import org.junit.Test;

import jp.co.orangearch.workmanage.domain.constant.WorkTimeType;

public class WorkTimeTest {

	@Test
	public void test() {
		WorkTimeType type = WorkTimeType.of(1);
		System.out.println(type.getValue() + ":" + type.getText());
		type = WorkTimeType.of(2);
		System.out.println(type.getValue() + ":" + type.getText());
		
	}

}
