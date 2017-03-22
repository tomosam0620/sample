package jp.co.orangearch.workmanage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.prefs.CsvPreference;

import com.github.mygreen.supercsv.io.CsvAnnotationBeanReader;

import jp.co.orangearch.workmanage.service.HoridayCsvBean;

@ComponentScan(basePackages = {"jp.co.orangearch.workmanage"})
public class CsvTest {

	@Test
	public void test() {
        List<HoridayCsvBean> list = new ArrayList<>();
		CsvAnnotationBeanReader<HoridayCsvBean> csvReader = null;
		try {
			csvReader = new CsvAnnotationBeanReader<>(
					HoridayCsvBean.class,
			        Files.newBufferedReader(new File("C:\\work\\horiday_sample.csv").toPath(), Charset.forName("Windows-31j")),
			        CsvPreference.STANDARD_PREFERENCE);
	        // ヘッダー行の読み込み
	        String[] headers = csvReader.getHeader(true);
	        
	        Arrays.asList(headers).stream().forEach(v->System.out.println(v));

	        HoridayCsvBean record = null;
	        while((record = csvReader.read()) != null) {
	            list.add(record);
	            System.out.println(record);
	        }

	        csvReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(SuperCsvException  e){
			System.out.println("**********:" + e.getClass().getName() + ":" + e.getMessage());
			System.out.println(csvReader.getErrorMessages());
		}
		
	}

}
