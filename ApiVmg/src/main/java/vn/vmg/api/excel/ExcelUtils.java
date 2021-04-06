package vn.vmg.api.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

public class ExcelUtils {

	/**
	 *  ? : list class object or list Map<String, object>
	 *  in/out type: xls or xlsx
	 *  HDSD: http://jxls.sourceforge.net/reference/xls_area.html 
	 * @param dirTemplate
	 * @param dirOutput
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static <T> String export(String dirTemplate, String dirOutput, Map<String, List<T> > data)
			throws IOException {
		FileInputStream is=null;
		OutputStream os=null;
		try {
			is = new FileInputStream(new File(dirTemplate));
			os = new FileOutputStream(dirOutput);
			Context context = new Context();
			for (String key : data.keySet()) {
				context.putVar(key, data.get(key));
			}
			JxlsHelper.getInstance().processTemplate(is, os, context);	
		} finally {
			try {if(os !=null)os.close();} catch (Exception e) {}
			try {if(is !=null)is.close();} catch (Exception e) {}
		}
		return dirOutput;
	}
	
	/**
	 * sheet name: "<sheetName>!A1". example: "Sheet333!A1"
	 * @param <T>
	 * @param dirTemplate
	 * @param dirOutput
	 * @param sheetName
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static <T> String exportWithSheetName(String dirTemplate, String dirOutput, String sheetName, Map<String, List<T> > data)
			throws IOException {
		FileInputStream is=null;
		OutputStream os=null;
		try {
			is = new FileInputStream(new File(dirTemplate));
			os = new FileOutputStream(dirOutput);
			Context context = new Context();
			for (String key : data.keySet()) {
				context.putVar(key, data.get(key));
			}
			JxlsHelper.getInstance().processGridTemplateAtCell(is, os, context, "", sheetName);
		} finally {
			try {if(os !=null)os.close();} catch (Exception e) {}
			try {if(is !=null)is.close();} catch (Exception e) {}
		}
		return dirOutput;
	}
	
	
	/**
	 * Create map Data with object input
	 * @param <T>
	 * @param dataExport
	 * @param keyNode
	 * @param data
	 */
	public static<T> void createMap(Map<String, List<T> > dataExport, String keyNode, T data) {
		if(!dataExport.containsKey(keyNode)) {
			List<T> lstTmp=new ArrayList<T>();
			lstTmp.add(data);
			dataExport.put(keyNode, lstTmp);
		}else {
			dataExport.get(keyNode).add(data);
		}
	}
	
	/**
	 * Create map Data with list object input
	 * @param <T>
	 * @param dataExport
	 * @param keyNode
	 * @param data
	 */
	public static<T> void createMap(Map<String, List<T> > dataExport, String keyNode, List<T> data) {
		if(!dataExport.containsKey(keyNode)) {
			dataExport.put(keyNode, data);
		}else {
			dataExport.get(keyNode).addAll(data);
		}
	}
	
	
	public static synchronized String buildPath(String path, String fileName) {
		String newPath;
		if(path.endsWith("/") || fileName.startsWith("/")) {
			newPath=path+fileName;
		}else {
			newPath=path+"/"+fileName;
		} 
		File file=new File(newPath);
		if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
		
		return newPath;
	}
	
	
	
}
