package vn.vmg.api.server.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.vertx.core.Verticle;
import javax.annotation.Generated;
import javax.inject.Provider;
import vn.vmg.api.thread.ReloadConfigVerticle;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ThreadModule_RouteReloadConfigVerticleFactory implements Factory<Verticle> {
  private final ThreadModule module;

  private final Provider<ReloadConfigVerticle> reloadConfigVerticleProvider;

  public ThreadModule_RouteReloadConfigVerticleFactory(
      ThreadModule module, Provider<ReloadConfigVerticle> reloadConfigVerticleProvider) {
    this.module = module;
    this.reloadConfigVerticleProvider = reloadConfigVerticleProvider;
  }

  @Override
  public Verticle get() {
    return provideInstance(module, reloadConfigVerticleProvider);
  }

  public static Verticle provideInstance(
      ThreadModule module, Provider<ReloadConfigVerticle> reloadConfigVerticleProvider) {
    return proxyRouteReloadConfigVerticle(module, reloadConfigVerticleProvider.get());
  }

  public static ThreadModule_RouteReloadConfigVerticleFactory create(
      ThreadModule module, Provider<ReloadConfigVerticle> reloadConfigVerticleProvider) {
    return new ThreadModule_RouteReloadConfigVerticleFactory(module, reloadConfigVerticleProvider);
  }

  public static Verticle proxyRouteReloadConfigVerticle(
      ThreadModule instance, ReloadConfigVerticle reloadConfigVerticle) {
    return Preconditions.checkNotNull(
        instance.routeReloadConfigVerticle(reloadConfigVerticle),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
