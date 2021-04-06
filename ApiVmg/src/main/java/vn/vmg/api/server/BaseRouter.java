package vn.vmg.api.server;

import static io.vertx.core.http.HttpMethod.POST;
import static io.vertx.core.http.HttpMethod.GET;

import java.util.Arrays;
import java.util.HashSet;

import javax.inject.Inject;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.CorsHandler;
import vn.vmg.api.common.Constant;

public class BaseRouter extends AbstractVerticle {
	private final Router router;
	@Inject public BaseRouter(Router router) {
		this.router=router;
	}
	
	@Override
	public void start() throws Exception {
		 	router.route().handler(BodyHandler.create());
			router.route().handler(CookieHandler.create());
			router.route().handler(CorsHandler.create("*").allowedMethods(new HashSet<>(Arrays.asList(POST,GET)))
					.allowedHeaders(new HashSet<>(Arrays.asList("Authorization", "Content-Type"))));
			router.route(Constant.TAG_API+"/process_status").handler(ctx -> {
				ctx.response().end("*** welcome ***");
			});
	}
	
}
