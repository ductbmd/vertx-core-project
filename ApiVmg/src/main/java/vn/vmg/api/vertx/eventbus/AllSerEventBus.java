package vn.vmg.api.vertx.eventbus;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;

import vn.vmg.api.common.BuildRes;
import vn.vmg.api.common.Constant;
import vn.vmg.api.common.utils.JsonUtils;
import vn.vmg.api.enumcfg.ApiErr;
import vn.vmg.api.impl.EventBusBaseImpl;
import vn.vmg.api.impl.EventIdEnum;
import vn.vmg.api.vertx.service.handle.AllSerHandle;

public class AllSerEventBus extends EventBusBaseImpl {
	private final AllSerHandle allSer;

	@Inject
	public AllSerEventBus(AllSerHandle allSer) {
		this.allSer = allSer;
	}

	@Override
	public void start() throws Exception {

		// ===================================================================================
		// GET CONFIG SERVICE INFO
		// ===================================================================================
		this.createEventBus(EventIdEnum.GET_ALL_SERVICE_AND_PARTNER, body -> {
			JsonNode result = JsonUtils.newEmptyObj();
			JsonUtils.addNodeValue(result, Constant.JSON_NODE_DATA, this.allSer.loadAllSerAndPartner());
			BuildRes.addResSer(result, ApiErr.SUCCESS.getValue(), ApiErr.SUCCESS);
			return result.toString();
		});
		

		this.createEventBus(EventIdEnum.GET_ALL_INDICATOR_OUTPUT, body -> {
			JsonNode result = JsonUtils.newEmptyObj();
			JsonUtils.addNodeValue(result, Constant.JSON_NODE_DATA,this.allSer.loadAllIndicatorOutput());

			BuildRes.addResSer(result, ApiErr.SUCCESS.getValue(), ApiErr.SUCCESS);
			return result.toString();
		});

	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return Logger.getLogger(this.getClass());
	}

}
