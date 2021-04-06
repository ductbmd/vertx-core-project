package vn.vmg.api.server.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.vertx.core.Verticle;
import javax.annotation.Generated;
import javax.inject.Provider;
import vn.vmg.api.vertx.eventbus.AllSerEventBus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class EventBusModule_AllSerEventBusFactory implements Factory<Verticle> {
  private final EventBusModule module;

  private final Provider<AllSerEventBus> eventProvider;

  public EventBusModule_AllSerEventBusFactory(
      EventBusModule module, Provider<AllSerEventBus> eventProvider) {
    this.module = module;
    this.eventProvider = eventProvider;
  }

  @Override
  public Verticle get() {
    return provideInstance(module, eventProvider);
  }

  public static Verticle provideInstance(
      EventBusModule module, Provider<AllSerEventBus> eventProvider) {
    return proxyAllSerEventBus(module, eventProvider.get());
  }

  public static EventBusModule_AllSerEventBusFactory create(
      EventBusModule module, Provider<AllSerEventBus> eventProvider) {
    return new EventBusModule_AllSerEventBusFactory(module, eventProvider);
  }

  public static Verticle proxyAllSerEventBus(EventBusModule instance, AllSerEventBus event) {
    return Preconditions.checkNotNull(
        instance.allSerEventBus(event), "Cannot return null from a non-@Nullable @Provides method");
  }
}
