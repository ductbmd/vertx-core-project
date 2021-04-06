package vn.vmg.api.dto;

import com.fasterxml.jackson.databind.JsonNode;

import vn.vmg.api.common.utils.JsonUtils;

public class RouteConfig {
	
	public JsonNode routeAll;

	public JsonNode getRoute(String routeSerNode) {
		return JsonUtils.getValue(routeAll,routeSerNode);
	}
}
