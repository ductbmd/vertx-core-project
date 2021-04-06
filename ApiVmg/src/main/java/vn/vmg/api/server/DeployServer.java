package vn.vmg.api.server;

import java.util.Map;

import javax.inject.Inject;


import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

/**
 * Build vertx server core
 * @author VMG
 *
 */
public class DeployServer {
	private final Vertx vertx;
	private final Map<Class<?>, DeploymentOptions> verticleConfigs;
	private final Map<Class<?>, Verticle> verticles;
	private final ServerVerticle server;
	private final BaseRouter baseRoute;
	
	@Inject
	DeployServer(Vertx vertx, 
			ServerVerticle server,
			BaseRouter baseRoute,
			Map<Class<?>, DeploymentOptions> verticleConfigs,
			Map<Class<?>, Verticle> verticles) {
		this.vertx = vertx;
		this.server=server;
		this.baseRoute=baseRoute;
		this.verticleConfigs = verticleConfigs;
		this.verticles = verticles;
	}

	public void start() {
		DeploymentOptions optionDefault = new DeploymentOptions();
		optionDefault.setInstances(1);
		
		DeploymentOptions tmp;

		// build router
		tmp=verticleConfigs.get(this.baseRoute.getClass());
		vertx.deployVerticle(this.baseRoute,tmp!=null ? tmp: optionDefault);

		//build Verticle
		verticles.keySet().forEach(key -> {
			if(verticleConfigs.containsKey(key)) {
				this.vertx.deployVerticle(verticles.get(key), verticleConfigs.get(key));
			}else {
				this.vertx.deployVerticle(verticles.get(key), optionDefault);
			}
		});
		
		//build server
		tmp=verticleConfigs.get(this.server.getClass());
		vertx.deployVerticle(server,tmp!=null ? tmp: optionDefault);
	}
	

}
