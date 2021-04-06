package vn.vmg.api.server.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.vertx.core.DeploymentOptions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ThreadModule_ReloadConfigVerticleOptionFactory
    implements Factory<DeploymentOptions> {
  private final ThreadModule module;

  public ThreadModule_ReloadConfigVerticleOptionFactory(ThreadModule module) {
    this.module = module;
  }

  @Override
  public DeploymentOptions get() {
    return provideInstance(module);
  }

  public static DeploymentOptions provideInstance(ThreadModule module) {
    return proxyReloadConfigVerticleOption(module);
  }

  public static ThreadModule_ReloadConfigVerticleOptionFactory create(ThreadModule module) {
    return new ThreadModule_ReloadConfigVerticleOptionFactory(module);
  }

  public static DeploymentOptions proxyReloadConfigVerticleOption(ThreadModule instance) {
    return Preconditions.checkNotNull(
        instance.reloadConfigVerticleOption(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
