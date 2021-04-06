package vn.vmg.api.server.manager;

import dagger.Component;
import vn.vmg.api.common.SerConfig;
import vn.vmg.api.common.utils.ParamMain;
import vn.vmg.api.server.DeployServer;
import vn.vmg.api.server.module.AppModule;
import vn.vmg.api.server.module.DatabaseModule;
import vn.vmg.api.server.module.EventBusModule;
import vn.vmg.api.server.module.RoutersModule;
import vn.vmg.api.server.module.ThreadModule;

import java.util.TimeZone;

import javax.inject.Singleton;

public class AppMain {
	@Singleton
	@Component(modules = { 
			AppModule.class, 
			DatabaseModule.class, 
			RoutersModule.class, 
			EventBusModule.class,
			ThreadModule.class,
			})
	public interface ModuleComponent {
		DeployServer server();
	}

	public static void main(String[] args) throws Exception {
		ParamMain.onLoad(args);
		SerConfig.init();
		TimeZone.setDefault(TimeZone.getTimeZone(SerConfig.app.timeZone));
		ModuleComponent vertxServer = DaggerAppMain_ModuleComponent.builder().build();
		vertxServer.server().start();
	}
}
