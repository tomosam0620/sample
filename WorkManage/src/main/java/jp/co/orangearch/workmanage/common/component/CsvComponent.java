package jp.co.orangearch.workmanage.common.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jp.co.orangearch.workmanage.common.exception.CsvHandleException;

public interface CsvComponent {

	<T> List<T> toBean(Class<T> clazz, InputStream stream, String charset, boolean isExistHeader) throws IOException, CsvHandleException;
}
