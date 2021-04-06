package vn.vmg.api.vertx.service.handle;


import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import vn.vmg.api.common.utils.JsonUtils;

public class AllSerHandle {
//	private Logger logger=Logger.getLogger(this.getClass());
	private final AllSerDb dbSer;

	@Inject
	public AllSerHandle(AllSerDb dbSer) {
		this.dbSer = dbSer;
	}

	public JsonNode loadAllSerAndPartner() throws Exception {
		JsonNode jsonData = JsonUtils.newEmptyObj();
		JsonUtils.addNodeValue(jsonData, "partner", JsonUtils.toJsonNode(this.dbSer.getAllPartner()));
		JsonUtils.addNodeValue(jsonData, "servicetype", JsonUtils.toJsonNode(this.dbSer.getAllServiceType()));
		return jsonData;
	}

	public JsonNode loadAllIndicatorOutput() throws Exception {
		return this.dbSer.getAllIndicatorOutput();
	}


}
