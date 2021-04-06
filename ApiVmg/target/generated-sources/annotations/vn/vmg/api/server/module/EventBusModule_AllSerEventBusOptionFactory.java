package vn.vmg.api.server.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.vertx.core.DeploymentOptions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class EventBusModule_AllSerEventBusOptionFactory
    implements Factory<DeploymentOptions> {
  private final EventBusModule module;

  public EventBusModule_AllSerEventBusOptionFactory(EventBusModule module) {
    this.module = module;
  }

  @Override
  public DeploymentOptions get() {
    return provideInstance(module);
  }

  public static DeploymentOptions provideInstance(EventBusModule module) {
    return proxyAllSerEventBusOption(module);
  }

  public static EventBusModule_AllSerEventBusOptionFactory create(EventBusModule module) {
    return new EventBusModule_AllSerEventBusOptionFactory(module);
  }

  public static DeploymentOptions proxyAllSerEventBusOption(EventBusModule instance) {
    return Preconditions.checkNotNull(
        instance.allSerEventBusOption(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
