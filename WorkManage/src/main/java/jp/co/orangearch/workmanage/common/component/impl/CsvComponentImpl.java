package jp.co.orangearch.workmanage.common.component.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.prefs.CsvPreference;

import com.github.mygreen.supercsv.io.CsvAnnotationBeanReader;

import jp.co.orangearch.workmanage.common.component.CsvComponent;
import jp.co.orangearch.workmanage.common.exception.CsvError;
import jp.co.orangearch.workmanage.common.exception.CsvHandleException;

/**
 * CSVコンポーネントクラスです。
 * <br>
 * CSVのread/writeを行います。
 *
 * @author t-otsuka
 */
@Component
public class CsvComponentImpl implements CsvComponent{

	@Override
	public <T> List<T> toBean(Class<T> clazz, InputStream stream, String charset, boolean isExistHeader) throws IOException, CsvHandleException {
		List<T> list = new ArrayList<T>();
		CsvAnnotationBeanReader<T> csvReader = null;
		try {
			csvReader = new CsvAnnotationBeanReader<T>(
					clazz,
					new InputStreamReader(stream, charset),
					CsvPreference.STANDARD_PREFERENCE);
			// ヘッダー行の読み込み
			csvReader.getHeader(isExistHeader);
			T record = null;
			while((record = csvReader.read()) != null) {
				list.add(record);
			}
		}catch(SuperCsvException  e){
			List<CsvError> csverros = new ArrayList<CsvError>();
			for(String message : csvReader.getErrorMessages()){
				csverros.add(new CsvError(csvReader.getLineNumber(), message));
			}
			throw new CsvHandleException(csverros);
		}finally{
			csvReader.close();
			stream.close();
		}
		return list;
	}

}
