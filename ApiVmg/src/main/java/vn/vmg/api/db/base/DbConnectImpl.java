package vn.vmg.api.db.base;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public interface DbConnectImpl {
	/**
	 * ----------------------------------------------------------------------
	 * SQL METHOD
	 * ----------------------------------------------------------------------
	 * @throws Exception 
	 */
	List<Map<String, Object>> getQueryData(String sqlQuery, Object... param) throws Exception;

	List<Map<String, Object>> getQueryReFormatData(String sqlQuery, Object... param) throws Exception;

	boolean excQueryUpdate(String sqlQuery, Object... param) throws Exception;

	/**
	 * ----------------------------------------------------------------------
	 * PROCEDURE METHOD
	 * ----------------------------------------------------------------------
	 */
	List<Map<String, Object>> callStoreData(String procedure, Object... param) throws Exception;

	List<Map<String, Object>> callStoreReFormatData(String procedure, Object... param) throws Exception;
	
	JsonNode callStoreDataJs(String procedure, Object... param) throws Exception;
	
	JsonNode callStoreReformatJs(String procedure, Object... param) throws Exception;
	
	List<Map<String, Object>> callStoreDetectClob(String procedure, Object... param) throws Exception ;
	
	List<Map<String, Object>> callStoreDetectClobReformat(String procedure, Object... param) throws Exception ;
	
	void excStoreData(String procedure, Object... param) throws Exception;
	
	void excStoreBatch(String procedure, List<Object[]> lstData) throws Exception;
}
