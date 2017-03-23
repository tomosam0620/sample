package jp.co.orangearch.workmanage.common.component.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.prefs.CsvPreference;

import com.github.mygreen.supercsv.io.CsvAnnotationBeanReader;

import jp.co.orangearch.workmanage.common.component.CsvComponent;
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

	@Autowired
	private MessageSource messages;

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
			List<String> errorMessages = new ArrayList<String>();
			String msg = null;
			for(String message : csvReader.getErrorMessages()){
				msg = message.replaceAll("\\{|\\}", "");
				msg = messages.getMessage(msg, null, null, Locale.getDefault());
				errorMessages.add(StringUtils.isEmpty(msg) ? message : ("[" + csvReader.getLineNumber() + "行] : " + msg));
			}
			throw new CsvHandleException(errorMessages);
		}finally{
			csvReader.close();
			stream.close();
		}
		return list;
	}

}
