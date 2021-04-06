package vn.vmg.api.server.module;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.vertx.core.Verticle;
import vn.vmg.api.mapkey.VerticleMapKey;
import vn.vmg.api.vertx.routers.AllSerRoute;

@Module
public class RoutersModule {
	
	@Provides
	@IntoMap
	@VerticleMapKey(verticleClass = AllSerRoute.class)
	public Verticle routeAllSerVerticle(AllSerRoute routeServerVerticle) {
		return routeServerVerticle;
	}

}
