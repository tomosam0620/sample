package jp.co.orangearch.workmanage.common.util;

import org.junit.Assert;
import org.junit.Test;

import jp.co.orangearch.workmanage.common.util.DateUtils.DateTimeFormat;

public class DateUtilsTest {

	@Test
	public void test() {

		String date1="2017/01/01";
		Assert.assertTrue(DateUtils.isValidDate(date1, DateTimeFormat.YYYY_MM_DD));	//true
		Assert.assertTrue(DateUtils.isValidDate(date1, DateTimeFormat.YYYY_M_D));	//true

		String date2="2017/1/1";
		Assert.assertTrue(DateUtils.isValidDate(date2, DateTimeFormat.YYYY_M_D));	//true
		Assert.assertFalse(DateUtils.isValidDate(date2, DateTimeFormat.YYYY_MM_DD));	//false

		String date3="2017-01-01";
		Assert.assertTrue(DateUtils.isValidDate(date3, DateTimeFormat.UUUU_MM_DD));	//true
		Assert.assertTrue(DateUtils.isValidDate(date3, DateTimeFormat.UUUU_M_D));	//true

		String date4="2017-1-1";
		Assert.assertTrue(DateUtils.isValidDate(date4, DateTimeFormat.UUUU_M_D));	//true
		Assert.assertFalse(DateUtils.isValidDate(date4, DateTimeFormat.UUUU_MM_DD));	//false

		String time1="09:01";
		Assert.assertTrue(DateUtils.isValidDate(time1, DateTimeFormat.HH_MM));	//true
		Assert.assertTrue(DateUtils.isValidDate(time1, DateTimeFormat.H_M));	//true

		String time2="9:01";
		Assert.assertTrue(DateUtils.isValidDate(time2, DateTimeFormat.H_M));	//true
		Assert.assertFalse(DateUtils.isValidDate(time2, DateTimeFormat.HH_MM));	//false

		String time3="9:1";
		Assert.assertTrue(DateUtils.isValidDate(time3, DateTimeFormat.H_M));	//true
		Assert.assertFalse(DateUtils.isValidDate(time3, DateTimeFormat.HH_MM));	//false

		String time4="9:01";
		Assert.assertTrue(DateUtils.isValidDate(time4, DateTimeFormat.H_M));	//true
		Assert.assertFalse(DateUtils.isValidDate(time4, DateTimeFormat.HH_MM));	//false

		String dateTime1 ="2017-01-01 01:01:01.000";
		Assert.assertTrue(DateUtils.isValidDate(dateTime1, DateTimeFormat.UUUU_MM_DD_HH_MM_SS_SSS));	//true
		Assert.assertFalse(DateUtils.isValidDate(dateTime1, DateTimeFormat.UUUU_M_D_H_M_S_S));	//false

		String dateTime2 ="2017-1-1 1:1:1.0";
		Assert.assertTrue(DateUtils.isValidDate(dateTime2, DateTimeFormat.UUUU_M_D_H_M_S_S));		//true
		Assert.assertFalse(DateUtils.isValidDate(dateTime2, DateTimeFormat.UUUU_MM_DD_HH_MM_SS_SSS));	//false

		String dateTime3 ="2017-10-10 10:10:10.1";
		Assert.assertTrue(DateUtils.isValidDate(dateTime3, DateTimeFormat.UUUU_M_D_H_M_S_S));	//true
		Assert.assertFalse(DateUtils.isValidDate(dateTime3, DateTimeFormat.UUUU_MM_DD_HH_MM_SS_SSS));	//false

		String dateTime4 ="2017-10-10 10:10:10.100";
		Assert.assertTrue(DateUtils.isValidDate(dateTime4, DateTimeFormat.UUUU_MM_DD_HH_MM_SS_SSS));	//false
		Assert.assertFalse(DateUtils.isValidDate(dateTime4, DateTimeFormat.UUUU_M_D_H_M_S_S));	//false
	}

}
