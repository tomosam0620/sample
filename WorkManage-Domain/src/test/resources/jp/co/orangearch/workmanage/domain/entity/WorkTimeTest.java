package jp.co.orangearch.workmanage.domain.entity;

import org.junit.Test;

import jp.co.orangearch.workmanage.domain.domain.WorkTimeCode;

public class WorkTimeTest {

	@Test
	public void test() {
		WorkTimeCode type = new WorkTimeCode(1);
		System.out.println(type.getValue() + ":" + type.getValue());
		type = new WorkTimeCode(2);
		System.out.println(type.getValue() + ":" + type.getValue());
		
	}

}
