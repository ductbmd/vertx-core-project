package vn.vmg.api.server.module;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import vn.vmg.api.mapkey.VerticleMapKey;
import vn.vmg.api.thread.ReloadConfigVerticle;

@Module
public class ThreadModule {

	@Provides
	@IntoMap
	@VerticleMapKey(verticleClass = ReloadConfigVerticle.class)
	public Verticle routeReloadConfigVerticle(ReloadConfigVerticle reloadConfigVerticle) {
		return reloadConfigVerticle;
	}

	
	@Provides
	@IntoMap
	@VerticleMapKey(verticleClass = ReloadConfigVerticle.class)
	public DeploymentOptions reloadConfigVerticleOption() {
		return new DeploymentOptions().setInstances(1).setWorker(true);
	}

}
