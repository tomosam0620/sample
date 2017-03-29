package jp.co.orangearch.workmanage.component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jp.co.orangearch.workmanage.domain.exception.CsvHandleException;

public interface CsvComponent {

	<T> List<T> toBean(Class<T> clazz, InputStream stream, String charset, boolean isExistHeader) throws IOException, CsvHandleException;
	
	<T> ByteArrayOutputStream write(List<T> beans, Class<T> clazz, String charset) throws IOException;

}
