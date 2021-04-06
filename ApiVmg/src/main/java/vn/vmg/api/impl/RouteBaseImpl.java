package vn.vmg.api.impl;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import vn.vmg.api.common.SerConfig;
import vn.vmg.api.common.utils.ApiUtils;
import vn.vmg.api.common.utils.JsonUtils;

public abstract class RouteBaseImpl extends AbstractVerticle {
	protected Logger logger = this.getLogger();
	private int timeOutEvent = this.setTimeOutEventBus();

	public abstract int setTimeOutEventBus();

	public abstract Logger getLogger();

	protected void sendEventBus(RoutingContext ctx, String eventId, ParseInputImpl handleIn,
			ParseOutputImpl handleOut) {
		EventBus event = vertx.eventBus();
		String ipRemote = "";
		try {
			ipRemote = ApiUtils.getClientIpAddr(ctx.request());
			logger.info("Receiver [" + eventId + "]. ip request: " + ipRemote);
			String input = "";
			if (handleIn != null) {
				input = handleIn.loadInPut(ctx);
			} else {
				input = ctx.getBodyAsString();
			}

			event.send(eventId, input, new DeliveryOptions().setSendTimeout(this.timeOutEvent), r -> {
				try {
					if (r.succeeded()) {
						ctx.response().end(handleOut != null ? handleOut.loadOutput(r.result().body())
								: r.result().body().toString());
					} else {
						ctx.response().setStatusCode(400).end(r.cause().getMessage());
					}
				} catch (Exception e) {
					ctx.response().setStatusCode(400).end("System Busy!");
					logger.error("[" + eventId + "] response event err!", e);
				}

			});

		} catch (Exception ex) {
			logger.error("[" + eventId + "] handle error. ip request: " + ipRemote, ex);
			ctx.response().setStatusCode(500).end();
		}
	}

	protected void loadRouteStandar(Router router, String routeName,String routePath) {
		JsonNode lstRoute=SerConfig.routeStandar.getRoute(routeName);
		if(lstRoute==null || !lstRoute.isArray()) {
			logger.info("Config route empty. Route Name: "+routeName);
			return;
		}
		
		StringBuilder br=new StringBuilder();
		for (JsonNode node : SerConfig.routeStandar.getRoute(routeName)) {
			String method = JsonUtils.getString(node, "method");
			String path = JsonUtils.getString(node, "path");
			String eventId = JsonUtils.getString(node, "eventId");
			
			path=routePath+path;

			switch (method.toUpperCase()) {
			case "POST":
				router.post(path).handler(ctx -> {
					ParseInputImpl in = null;
					ParseOutputImpl out = null;
					this.sendEventBus(ctx, eventId, in, out);
				});
				break;
			case "GET":
				router.get(path).handler(ctx -> {
					ParseInputImpl in = null;
					ParseOutputImpl out = null;
					this.sendEventBus(ctx, eventId, in, out);
				});
				break;

			default:
				break;
			}
			
			if(br.length()>0) br.append("\n");
			br.append(" - ").append(method).append(": ").append(path);
			
		}
		this.logger.info("***Create-Route***\n"+br.toString());
	}

	@FunctionalInterface
	protected interface ParseOutputImpl {
		public String loadOutput(Object body);
	}

	@FunctionalInterface
	protected interface ParseInputImpl {
		public String loadInPut(RoutingContext ctx);
	}
}
