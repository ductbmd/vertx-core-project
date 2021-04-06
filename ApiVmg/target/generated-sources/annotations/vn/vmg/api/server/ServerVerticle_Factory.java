package vn.vmg.api.server;

import dagger.internal.Factory;
import io.vertx.ext.web.Router;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ServerVerticle_Factory implements Factory<ServerVerticle> {
  private final Provider<Router> routerProvider;

  private final Provider<Integer> portProvider;

  public ServerVerticle_Factory(Provider<Router> routerProvider, Provider<Integer> portProvider) {
    this.routerProvider = routerProvider;
    this.portProvider = portProvider;
  }

  @Override
  public ServerVerticle get() {
    return provideInstance(routerProvider, portProvider);
  }

  public static ServerVerticle provideInstance(
      Provider<Router> routerProvider, Provider<Integer> portProvider) {
    return new ServerVerticle(routerProvider.get(), portProvider.get());
  }

  public static ServerVerticle_Factory create(
      Provider<Router> routerProvider, Provider<Integer> portProvider) {
    return new ServerVerticle_Factory(routerProvider, portProvider);
  }

  public static ServerVerticle newServerVerticle(Router router, int port) {
    return new ServerVerticle(router, port);
  }
}
