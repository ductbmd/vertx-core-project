package vn.vmg.api.excel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.vmg.api.common.SerConfig;

public class ExcelTempExample {
	public static final String FM_DATE_FILE_NAME="yyyyMMdd'.'HHmmss'.'SSS'_'";
	public static final String FM_DATE_FILE_NAME_OUPUT="yyyyMMdd'.'HHmmss'.'SSS'_Ouput_'";
	
	public static String exportTempKYC(String fileName, List<String> header) throws IOException {
		String fileIn=SerConfig.app.templateImportKYC;
		String fileOut=ExcelUtils.buildPath(SerConfig.app.downloadTempImport, fileName);
		Map<String, List<String> > mapHeader=new HashMap<String, List<String>>();
		mapHeader.put("headers", header);
		return ExcelUtils.exportWithSheetName(fileIn, fileOut, "data!A1", mapHeader);
	}
	
	public static String checkName(String fileName, String fileType) {
		if(fileName.endsWith(fileType) ) return fileName;
		fileName=fileName.replaceAll("[.]\\w+$", fileType);
		return fileName;
	}
	
}
