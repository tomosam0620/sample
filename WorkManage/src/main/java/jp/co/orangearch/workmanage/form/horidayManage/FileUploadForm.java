package jp.co.orangearch.workmanage.form.horidayManage;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm implements Serializable{

	/** シリアルバージョン */
	private static final long serialVersionUID = 1L;
	
	/** ファイル情報 */
	private MultipartFile fileData;

	public MultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}

}
