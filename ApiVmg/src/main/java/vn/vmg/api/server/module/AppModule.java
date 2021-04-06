package vn.vmg.api.server.module;

import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import vn.vmg.api.common.SerConfig;
import vn.vmg.api.mapkey.ServerPortKey;

import javax.inject.Singleton;

/**
 * Create module provider dagger object
 * 
 * @author VMG
 *
 */
@Module
public class AppModule {
	
	@Provides
	@Singleton
	Vertx provideVertx() {
		return Vertx.vertx();
	}

	@Provides
	@Singleton
	Router Router(Vertx vertx) {
		return Router.router(vertx);
	}
	
	@Provides
	@Singleton
	@ServerPortKey
	int provideServerPort() {
		return SerConfig.app.serverPort;
	}
	
	
	
	

}
