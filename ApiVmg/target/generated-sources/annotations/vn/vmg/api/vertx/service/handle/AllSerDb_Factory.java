package vn.vmg.api.vertx.service.handle;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import vn.vmg.api.db.base.DbConnectImpl;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AllSerDb_Factory implements Factory<AllSerDb> {
  private final Provider<DbConnectImpl> dbProvider;

  public AllSerDb_Factory(Provider<DbConnectImpl> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AllSerDb get() {
    return provideInstance(dbProvider);
  }

  public static AllSerDb provideInstance(Provider<DbConnectImpl> dbProvider) {
    return new AllSerDb(dbProvider.get());
  }

  public static AllSerDb_Factory create(Provider<DbConnectImpl> dbProvider) {
    return new AllSerDb_Factory(dbProvider);
  }

  public static AllSerDb newAllSerDb(DbConnectImpl db) {
    return new AllSerDb(db);
  }
}
