package vn.vmg.api.server;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import vn.vmg.api.mapkey.ServerPortKey;

/**
 * Create Listen Server Port with vertx
 * 
 * @author VMG
 *
 */
public class ServerVerticle extends AbstractVerticle {
	private final Logger logger = Logger.getLogger(this.getClass());
	private final Router router;
	private final int port;

	@Inject
	public ServerVerticle(Router router, 
			@ServerPortKey int port) {
		this.router = router;
		this.port = port;
	}

	@Override
	public void start(Future<Void> future) {
		/*
		 * Extend header size (original was 8192) for long JWT token + Cookies Turn
		 * compression on Set max initial line length (e.g. "GET / HTTP 1.0") for long
		 * URLs
		 */
		HttpServerOptions httpServerOptions = new HttpServerOptions().setMaxHeaderSize(8192 * 2)
				.setCompressionSupported(true).setMaxInitialLineLength(4096 * 4);

		vertx.createHttpServer(httpServerOptions).requestHandler(router::accept).listen(this.port, res -> {
			if (res.succeeded()) {
				logger.info("=====================================");
				logger.info("HTTP Server started at port " + port);
				logger.info("=====================================");
				future.complete();
			} else {
				future.fail(res.cause());
			}
		});
	}

}
