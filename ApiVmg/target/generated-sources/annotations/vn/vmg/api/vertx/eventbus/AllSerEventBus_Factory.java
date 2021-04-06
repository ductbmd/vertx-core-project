package vn.vmg.api.vertx.eventbus;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import vn.vmg.api.vertx.service.handle.AllSerHandle;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AllSerEventBus_Factory implements Factory<AllSerEventBus> {
  private final Provider<AllSerHandle> allSerProvider;

  public AllSerEventBus_Factory(Provider<AllSerHandle> allSerProvider) {
    this.allSerProvider = allSerProvider;
  }

  @Override
  public AllSerEventBus get() {
    return provideInstance(allSerProvider);
  }

  public static AllSerEventBus provideInstance(Provider<AllSerHandle> allSerProvider) {
    return new AllSerEventBus(allSerProvider.get());
  }

  public static AllSerEventBus_Factory create(Provider<AllSerHandle> allSerProvider) {
    return new AllSerEventBus_Factory(allSerProvider);
  }

  public static AllSerEventBus newAllSerEventBus(AllSerHandle allSer) {
    return new AllSerEventBus(allSer);
  }
}
