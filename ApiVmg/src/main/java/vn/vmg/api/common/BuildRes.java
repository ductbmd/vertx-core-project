package vn.vmg.api.common;

import com.fasterxml.jackson.databind.JsonNode;

import vn.vmg.api.common.utils.ApiUtils;
import vn.vmg.api.common.utils.JsonUtils;


public class BuildRes {

	public static void addResSer(JsonNode jsonData, int err, Object message) {
		JsonUtils.addNodeValue(jsonData, "errorCode", err);
		JsonUtils.addNodeValue(jsonData, "message", ApiUtils.toStr(message));
	}
}
