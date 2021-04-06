package vn.vmg.api.server.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.vertx.core.Verticle;
import javax.annotation.Generated;
import javax.inject.Provider;
import vn.vmg.api.vertx.routers.AllSerRoute;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RoutersModule_RouteAllSerVerticleFactory implements Factory<Verticle> {
  private final RoutersModule module;

  private final Provider<AllSerRoute> routeServerVerticleProvider;

  public RoutersModule_RouteAllSerVerticleFactory(
      RoutersModule module, Provider<AllSerRoute> routeServerVerticleProvider) {
    this.module = module;
    this.routeServerVerticleProvider = routeServerVerticleProvider;
  }

  @Override
  public Verticle get() {
    return provideInstance(module, routeServerVerticleProvider);
  }

  public static Verticle provideInstance(
      RoutersModule module, Provider<AllSerRoute> routeServerVerticleProvider) {
    return proxyRouteAllSerVerticle(module, routeServerVerticleProvider.get());
  }

  public static RoutersModule_RouteAllSerVerticleFactory create(
      RoutersModule module, Provider<AllSerRoute> routeServerVerticleProvider) {
    return new RoutersModule_RouteAllSerVerticleFactory(module, routeServerVerticleProvider);
  }

  public static Verticle proxyRouteAllSerVerticle(
      RoutersModule instance, AllSerRoute routeServerVerticle) {
    return Preconditions.checkNotNull(
        instance.routeAllSerVerticle(routeServerVerticle),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
