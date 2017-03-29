package jp.co.orangearch.workmanage.component.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
import com.github.mygreen.supercsv.io.CsvAnnotationBeanWriter;

import jp.co.orangearch.workmanage.component.CsvComponent;
import jp.co.orangearch.workmanage.domain.exception.CsvHandleException;

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
	
	
	public <T> ByteArrayOutputStream write(List<T> beans, Class<T> clazz, String charset) throws IOException{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(os, charset);
		CsvAnnotationBeanWriter<T> csvWriter = new CsvAnnotationBeanWriter<>(
				clazz,
                writer,
                CsvPreference.STANDARD_PREFERENCE);
		
		csvWriter.writeHeader();
		for(T item : beans){
			csvWriter.write(item);
		}
		
		csvWriter.flush();
		csvWriter.close();
		writer.close();
		return os;
	}

}
