package jp.co.orangearch.workmanage.service;

import java.io.InputStream;
import java.util.List;

import jp.co.orangearch.workmanage.common.exception.CsvHandleException;
import jp.co.orangearch.workmanage.entity.Horiday;

public interface HoridayManageService {

	List<HoridayCsvBean> read(InputStream stream) throws CsvHandleException;

	void update(List<HoridayCsvBean> lines);

	List<Horiday> selectAll();
}
