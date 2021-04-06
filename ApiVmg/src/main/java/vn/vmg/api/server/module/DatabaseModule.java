package vn.vmg.api.server.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vn.vmg.api.db.base.DbApp;
import vn.vmg.api.db.base.DbConnectImpl;
import vn.vmg.api.mapkey.DbAppKey;
@Module
public class DatabaseModule {

	@Provides
	@Singleton
	@DbAppKey
	DbConnectImpl providerDb() {
		return new DbApp();
	}
	
}
