package vn.vmg.api.server.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import vn.vmg.api.db.base.DbConnectImpl;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DatabaseModule_ProviderDbFactory implements Factory<DbConnectImpl> {
  private final DatabaseModule module;

  public DatabaseModule_ProviderDbFactory(DatabaseModule module) {
    this.module = module;
  }

  @Override
  public DbConnectImpl get() {
    return provideInstance(module);
  }

  public static DbConnectImpl provideInstance(DatabaseModule module) {
    return proxyProviderDb(module);
  }

  public static DatabaseModule_ProviderDbFactory create(DatabaseModule module) {
    return new DatabaseModule_ProviderDbFactory(module);
  }

  public static DbConnectImpl proxyProviderDb(DatabaseModule instance) {
    return Preconditions.checkNotNull(
        instance.providerDb(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
