package vn.vmg.api.db.base;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.vmg.api.common.SerConfig;
import vn.vmg.api.common.utils.JsonUtils;
import vn.vmg.api.excel.ExcelUtils;

public class DbApp extends DbOraPool {

	@Override
	DbConfig loadDbConf() throws Exception {
		String confDir = SerConfig.app.dbConfig;
		return JsonUtils.mapFileToClass(confDir, DbConfig.class, true);
	}

	public static void main(String[] args) throws Exception {
		SerConfig.init();
		DbApp db = new DbApp();
		
		List<Map<String, Object>> callStoreData = db.callStoreData("PKG_IMPORT.GET_OUT_PUT_KYC_IMPORT(?,?)",22);
		
		Map<String, List<Map<String, Object>>> map=new HashMap<String, List<Map<String,Object>>>();
		map.put("data", callStoreData);
		
		String temIn="template/BC_XuatExcelKYC_template.xlsx";
		String tempOut="template/BC_XuatExcelKYC_template_out5.xlsx";
		ExcelUtils.export(temIn, tempOut, map);
	}



}
