package vn.vmg.api.vertx.routers;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import io.vertx.ext.web.Router;
import vn.vmg.api.common.Constant;
import vn.vmg.api.common.SerConfig;
import vn.vmg.api.impl.RouteBaseImpl;

/**
 * Route function chargGW
 * 
 * @author VMG
 *
 */
public class AllSerRoute extends RouteBaseImpl {
	private static final String ROUTE_NAME = "service";
	private static final String ROUTE_PATH=Constant.TAG_API+"/"+ROUTE_NAME+"/";
	private Router router;

	@Inject
	public AllSerRoute(Router router) {
		this.router = router;
	}

	@Override
	public void start() throws Exception {

		this.loadRouteStandar(router, ROUTE_NAME, ROUTE_PATH);
		
	}

	@Override
	public int setTimeOutEventBus() {
		return SerConfig.app.routeTimeOutStandard;
	}

	@Override
	public Logger getLogger() {
		return Logger.getLogger(this.getClass());
	}

}
