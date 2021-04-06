package vn.vmg.api.db.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import com.fasterxml.jackson.databind.JsonNode;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import oracle.jdbc.internal.OracleTypes;
import vn.vmg.api.common.utils.ApiUtils;
import vn.vmg.api.common.utils.JsonUtils;

public abstract class DbOraPool implements DbConnectImpl {
	protected BoneCP connectionPool = null;

	abstract DbConfig loadDbConf() throws Exception;

	protected void init() throws Exception {
		try {
			DbConfig config = loadDbConf();
			Class.forName(config.getDriver());
			BoneCPConfig boneConf = new BoneCPConfig();
			boneConf.setJdbcUrl(config.getUrl());
			boneConf.setUsername(config.getUser());
			boneConf.setPassword(config.getPass());
			boneConf.setMinConnectionsPerPartition(config.getMinConnection());
			boneConf.setMaxConnectionsPerPartition(config.getMaxConnection());
			boneConf.setPartitionCount(config.getPartitionCount());
			boneConf.setConnectionTimeout(config.getTimeOutConnection(), TimeUnit.MILLISECONDS);
			boneConf.setDefaultAutoCommit(config.isAutoCommit());
			boneConf.setMaxConnectionAgeInSeconds(config.getMaxConnectionAgeInSeconds());
			boneConf.setConnectionTestStatement(config.getConnectionTestStatement());

			this.connectionPool = new BoneCP(boneConf);
		} catch (Exception e) {
			throw e;
		}
	}

	protected void ClosePool() {
		if (this.connectionPool != null) {
			this.connectionPool.shutdown();
		}
	}

	public Connection getConn() throws Exception {
		if (this.connectionPool == null) {
			init();
		}

		try {
			if (this.connectionPool != null) {
				return this.connectionPool.getConnection();
			}
		} catch (SQLException e) {
			throw new SQLException("Get Connection err!!! ", e);
		}
		return null;
	}

	public void closeConn(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
	}

	public void closeSt(PreparedStatement pre) {
		if (pre != null) {
			try {
				pre.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
	}

	public void closeRe(ResultSet re) {
		if (re != null) {
			try {
				re.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * ---------------------------------------------------------------------------------------------------
	 * SQL METHOD
	 * ---------------------------------------------------------------------------------------------------
	 */

	public <T> List<T> getQueryData(String sqlQuery, Class<T> mapValue, Object... param) throws Exception {
		return getQueryData(sqlQuery, mapValue, true, param);
	}

	public <T> List<T> getQueryData(String sqlQuery, Class<T> mapValue, boolean reFmName, Object... param)
			throws Exception {
		List<T> lstData = null;

		List<Map<String, Object>> loadDb = getQueryData(sqlQuery, reFmName, param);

		if (loadDb != null) {
			lstData = new ArrayList<>();
			for (Map<String, Object> map : loadDb) {
				lstData.add(JsonUtils.mapJsonObjectToClass(map, mapValue, true));
			}
		}
		return lstData;
	}

	public <T> List<T> getStoreData(String procedure, Class<T> mapValue, Object... param) throws Exception {
		return getStoreData(procedure, mapValue, true, param);
	}

	public <T> List<T> getStoreData(String procedure, Class<T> mapValue, boolean reFmName, Object... param)
			throws Exception {
		List<T> lstData = null;

		List<Map<String, Object>> loadDb = callStoreQuery(procedure, reFmName, param);

		if (loadDb != null) {
			lstData = new ArrayList<>();
			for (Map<String, Object> map : loadDb) {
				lstData.add(JsonUtils.mapJsonObjectToClass(map, mapValue, true));
			}
		}
		return lstData;
	}
	
	public List<Map<String, Object>> getQueryData(String sqlQuery, Object... param) throws Exception {
		return getQueryData(sqlQuery, false, param);
	}

	
	public List<Map<String, Object>> getQueryReFormatData(String sqlQuery, Object... param) throws Exception {
		return getQueryData(sqlQuery, true, param);
	}

	private List<Map<String, Object>> getQueryData(String sqlQuery, boolean reFmName, Object... param)
			throws Exception {
		Connection conn = null;
		ResultSet re = null;
		PreparedStatement pre = null;
		try {
			List<Map<String, Object>> lstData = null;
			conn = this.getConn();

			pre = conn.prepareStatement(sqlQuery);
			this.addParam(pre, false, param);

			re = pre.executeQuery();

			// if no data --> return
			if (!re.isBeforeFirst()) {
				return lstData;
			}

			lstData = new ArrayList<>();
			ResultSetMetaData rsmd = re.getMetaData();

			Map<String, Object> obj;
			int numColumns;
			String column_name;

			while (re.next()) {
				obj = new HashMap<String, Object>();
				numColumns = rsmd.getColumnCount();

				// convert row data to jsonObject
				for (int i = 1; i <= numColumns; i++) {
					column_name = rsmd.getColumnName(i);
					obj.put(this.reFmName(column_name, reFmName), re.getObject(column_name));
				}

				lstData.add(obj);
			}
			return lstData;
		} catch (Exception throwable) {
			throw throwable;
		} finally {
			this.closeRe(re);
			this.closeSt(pre);
			this.closeConn(conn);
		}
	}

	
	public boolean excQueryUpdate(String sqlQuery, Object... param) throws Exception {
		Connection conn = null;
		PreparedStatement pre = null;
		try {
			conn = this.getConn();
			pre = conn.prepareStatement(sqlQuery);
			this.addParam(pre, false, param);

			boolean result = pre.execute();
			if (!conn.getAutoCommit())
				conn.commit();
			return result;
		} catch (Exception throwable) {
			if (conn != null && !conn.getAutoCommit())
				conn.rollback();
			throw throwable;
		} finally {
			this.closeSt(pre);
			this.closeConn(conn);
		}
	}

	/**
	 * ---------------------------------------------------------------------------------------------------
	 * PROCEDURE METHOD
	 * ---------------------------------------------------------------------------------------------------
	 */
	
	public List<Map<String, Object>> callStoreData(String procedure, Object... param) throws Exception {
		return this.callStoreQuery(procedure, false, param);
	}

	
	public List<Map<String, Object>> callStoreReFormatData(String procedure, Object... param) throws Exception {
		return this.callStoreQuery(procedure, true, param);
	}

	private List<Map<String, Object>> callStoreQuery(String procedure, boolean reFmName, Object... param)
			throws Exception {
		Connection conn = null;
		ResultSet re = null;
		CallableStatement pre = null;
		try {
			List<Map<String, Object>> lstData = null;
			conn = this.getConn();

			pre = conn.prepareCall(this.buildQueryStoreOra(procedure,this.getParamSize(param),true));
			pre.registerOutParameter(1, OracleTypes.CURSOR);

			this.addParam(pre, true, param);

			pre.executeQuery();
			if (!conn.getAutoCommit())
				conn.commit();

			re = (ResultSet) pre.getObject(1);

			// if no data --> return
			if (!re.isBeforeFirst()) {
				return lstData;
			}

			lstData = new ArrayList<>();
			ResultSetMetaData rsmd = re.getMetaData();

			Map<String, Object> obj;
			int numColumns;
			String column_name;

			while (re.next()) {
				obj = new HashMap<String, Object>();
				numColumns = rsmd.getColumnCount();

				// convert row data to jsonObject
				for (int i = 1; i <= numColumns; i++) {
					column_name = rsmd.getColumnName(i);
					obj.put(this.reFmName(column_name, reFmName), re.getObject(column_name));
				}

				lstData.add(obj);
			}
			
			return lstData;
		} catch (Exception throwable) {
			if (conn != null && !conn.getAutoCommit()) {
				conn.rollback();
			}
			throw throwable;
		} finally {
			this.closeRe(re);
			this.closeSt(pre);
			this.closeConn(conn);
		}
	}

	public JsonNode callStoreDataJs(String procedure, Object... param) throws Exception {
		return this.callStoreDataJs(procedure, false, param);
	}

	public JsonNode callStoreReformatJs(String procedure, Object... param) throws Exception {
		return this.callStoreDataJs(procedure, true, param);
	}

	private JsonNode callStoreDataJs(String procedure, boolean reFmName, Object... param) throws Exception {
		Connection conn = null;
		ResultSet re = null;
		CallableStatement pre = null;
		try {
			JsonNode lstData = JsonUtils.newEmptyArr();
			conn = this.getConn();

			pre = conn.prepareCall(this.buildQueryStoreOra(procedure,this.getParamSize(param),true));
			pre.registerOutParameter(1, OracleTypes.CURSOR);

			this.addParam(pre, true, param);

			pre.executeQuery();
			if (!conn.getAutoCommit())
				conn.commit();

			re = (ResultSet) pre.getObject(1);

			// if no data --> return
			if (!re.isBeforeFirst()) {
				return lstData;
			}

			ResultSetMetaData rsmd = re.getMetaData();

			int numColumns;
			String column_name;

			JsonNode obj;
			while (re.next()) {
				obj = JsonUtils.newEmptyObj();
				numColumns = rsmd.getColumnCount();

				// convert row data to jsonObject
				for (int i = 1; i <= numColumns; i++) {
					column_name = rsmd.getColumnName(i);
					JsonUtils.addNodeValue(obj, this.reFmName(column_name, reFmName),
							this.getObjData(re.getObject(column_name)));
				}

				JsonUtils.appendNode(lstData, obj);
			}
			return lstData;
		} catch (Exception throwable) {
			if (conn != null && !conn.getAutoCommit()) {
				conn.rollback();
			}
			throw throwable;
		} finally {
			this.closeRe(re);
			this.closeSt(pre);
			this.closeConn(conn);
		}
	}
	
	public List<Map<String, Object>> callStoreDetectClob(String procedure, Object... param) throws Exception {
		return this.callStoreDetectClob(procedure, false, param);
	}

	public List<Map<String, Object>> callStoreDetectClobReformat(String procedure, Object... param) throws Exception {
		return this.callStoreDetectClob(procedure, true, param);
	}
	
	private List<Map<String, Object>> callStoreDetectClob(String procedure, boolean reFmName, Object... param)
			throws Exception {
		Connection conn = null;
		ResultSet re = null;
		CallableStatement pre = null;
		try {
			List<Map<String, Object>> lstData = null;
			conn = this.getConn();

			pre = conn.prepareCall("{call ".concat(procedure).concat(" }"));
			pre.registerOutParameter(1, OracleTypes.CURSOR);

			this.addParam(pre, true, param);

			pre.executeQuery();
			if (!conn.getAutoCommit())
				conn.commit();

			re = (ResultSet) pre.getObject(1);

			// if no data --> return
			if (!re.isBeforeFirst()) {
				return lstData;
			}

			lstData = new ArrayList<>();
			ResultSetMetaData rsmd = re.getMetaData();

			Map<String, Object> obj;
			int numColumns;
			String column_name;

			while (re.next()) {
				obj = new HashMap<String, Object>();
				numColumns = rsmd.getColumnCount();

				// convert row data to jsonObject
				for (int i = 1; i <= numColumns; i++) {
					column_name = rsmd.getColumnName(i);
					obj.put(this.reFmName(column_name, reFmName), this.getObjVal(re.getObject(column_name)));
				}
				lstData.add(obj);
			}
			
			return lstData;
		} catch (Exception throwable) {
			if (conn != null && !conn.getAutoCommit()) {
				conn.rollback();
			}
			throw throwable;
		} finally {
			this.closeRe(re);
			this.closeSt(pre);
			this.closeConn(conn);
		}
	}

	public void addParam(PreparedStatement pre, boolean isStore, Object... param) throws SQLException {
		if (param.length < 1) {
			return;
		}

		int count = 1;
		if (isStore)
			count = 2;

		// if input list param
		if (param[0] instanceof List) {
			List<?> lst = (List<?>) param[0];
			for (int i = 0, size = lst.size(); i < size; i++) {
				pre.setObject((i + count), lst.get(i));
			}
			return;
		}

		for (int i = 0, size = param.length; i < size; i++) {
			pre.setObject((i + count), param[i]);
		}
	}

	private Object getObjData(Object obj) {

		if (obj instanceof java.sql.Date) {
			return ((java.sql.Date) obj).getTime();
		}

		if (obj instanceof java.util.Date) {
			return ((java.util.Date) obj).getTime();
		}

		return obj;
	}

	public void excStoreData(String procedure, Object... param) throws Exception {
		Connection conn = null;
		CallableStatement pre = null;
		try {
			conn = this.getConn();
			pre = conn.prepareCall(this.buildQueryStoreOra(procedure,this.getParamSize(param),false));
			this.addParam(pre, false, param);

			pre.executeQuery();
			if (!conn.getAutoCommit())
				conn.commit();
		} catch (Exception throwable) {
			if (conn != null && !conn.getAutoCommit()) {
				conn.rollback();
			}
			throw throwable;
		} finally {
			this.closeSt(pre);
			this.closeConn(conn);
		}
	}
	
	
	public void excStoreBatch(String procedure, List<Object[]> lstData) throws Exception {
		Connection conn = null;
		CallableStatement pre = null;
		try {
			conn = this.getConn();
			pre = conn.prepareCall(this.buildQueryStoreOra(procedure,lstData.get(0).length,false));
			for(Object[] param:lstData) {
				this.addParam(pre, false, param);
				pre.addBatch();
			}
			pre.executeBatch();
			if (!conn.getAutoCommit())
				conn.commit();
		} catch (Exception throwable) {
			if (conn != null && !conn.getAutoCommit()) {
				conn.rollback();
			}
			throw throwable;
		} finally {
			this.closeSt(pre);
			this.closeConn(conn);
		}
	}

	private String reFmName(String columName, boolean isReformat) {
		if (!isReformat) {
			return columName;
		}

		if (!columName.contains("_")) {
			return (columName.toUpperCase().equals(columName) ? columName.toLowerCase() : columName);
		}

		StringBuilder sb = new StringBuilder();
		int upper = 0;
		for (char c : columName.toCharArray()) {
			if (c == '_') {
				upper = 1;
			} else if (upper > 0) {
				sb.append(Character.toUpperCase(c));
				upper = 0;
			} else {
				sb.append(Character.toLowerCase(c));
			}
		}

		return sb.toString();
	}
	
	@SuppressWarnings("deprecation")
	private Object getObjVal(Object objVal) {
		try {
			if (objVal instanceof oracle.sql.NCLOB || objVal instanceof oracle.sql.CLOB) {
				return ApiUtils.clobToStr(objVal);
			}
		} catch (Exception e) {
			System.out.println("getObjVal: "+e);
		}
		return objVal;
	}
	
	private String buildQueryStoreOra(String procedure, int paramSize, boolean isQueryStore) {
		if (procedure.contains("("))
			return  ("{call ".concat(procedure).concat(" }"));
		StringBuffer br = new StringBuffer();
		if (isQueryStore) {
			br.append("?");
		}

		for (int i = 0; i < paramSize; i++) {
			if (br.length() > 0)
				br.append(",");
			br.append("?");
		}

		return ("{call ".concat(procedure).concat("(").concat(br.toString()).concat(") }"));
	}
	
	private int getParamSize(Object...param) {
		if(param.length<1) return 0;
		if(param[0] instanceof List) return ((List<?>)param[0]).size();
		return param.length;
	}
	
}
