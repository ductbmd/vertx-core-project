package vn.vmg.api.server.module;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import vn.vmg.api.common.SerConfig;
import vn.vmg.api.mapkey.VerticleMapKey;
import vn.vmg.api.vertx.eventbus.AllSerEventBus;

@Module
public class EventBusModule {

	@Provides
	@IntoMap
	@VerticleMapKey(verticleClass = AllSerEventBus.class)
	public Verticle allSerEventBus(AllSerEventBus event) {
		return event;
	}

	
	@Provides
	@IntoMap
	@VerticleMapKey(verticleClass = AllSerEventBus.class)
	public DeploymentOptions allSerEventBusOption() {
		return new DeploymentOptions().setInstances(1).setWorker(true)
				.setWorkerPoolSize(SerConfig.app.vertxWorkerMaxThreadStandard)
				.setMaxWorkerExecuteTime(SerConfig.app.vertxWorkerMaxTimeExcuteStandard)
				.setWorkerPoolName("AllSerEventBus").setMultiThreaded(true);
	}
	
}
