package vn.vmg.api.vertx.service.handle;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.databind.JsonNode;

import vn.vmg.api.db.base.DbConnectImpl;
import vn.vmg.api.mapkey.DbAppKey;

import java.util.List;
import java.util.Map;

@Singleton
public class AllSerDb {
	private final DbConnectImpl db;

	@Inject
	public AllSerDb(@DbAppKey DbConnectImpl db) {
		this.db = db;
	}
	
	//get all config process handle
	public List<Map<String, Object>> getAllPartner() throws Exception {
		return this.db.callStoreReFormatData("SYS.Partner_GetAll(?)");
	}

	public List<Map<String, Object>> getAllServiceType() throws Exception {
		return this.db.callStoreReFormatData("SYS.ServiceType_GetAll(?)");
	}

	public JsonNode getAllIndicatorOutput() throws Exception {
		return this.db.callStoreReformatJs("SYS.IndicatorOutput_GetList(?)");
	}
	
}
