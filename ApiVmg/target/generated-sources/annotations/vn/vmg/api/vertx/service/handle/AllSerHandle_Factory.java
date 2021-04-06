package vn.vmg.api.vertx.service.handle;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AllSerHandle_Factory implements Factory<AllSerHandle> {
  private final Provider<AllSerDb> dbSerProvider;

  public AllSerHandle_Factory(Provider<AllSerDb> dbSerProvider) {
    this.dbSerProvider = dbSerProvider;
  }

  @Override
  public AllSerHandle get() {
    return provideInstance(dbSerProvider);
  }

  public static AllSerHandle provideInstance(Provider<AllSerDb> dbSerProvider) {
    return new AllSerHandle(dbSerProvider.get());
  }

  public static AllSerHandle_Factory create(Provider<AllSerDb> dbSerProvider) {
    return new AllSerHandle_Factory(dbSerProvider);
  }

  public static AllSerHandle newAllSerHandle(AllSerDb dbSer) {
    return new AllSerHandle(dbSer);
  }
}
