package vn.vmg.api.server;

import dagger.internal.Factory;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import java.util.Map;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DeployServer_Factory implements Factory<DeployServer> {
  private final Provider<Vertx> vertxProvider;

  private final Provider<ServerVerticle> serverProvider;

  private final Provider<BaseRouter> baseRouteProvider;

  private final Provider<Map<Class<?>, DeploymentOptions>> verticleConfigsProvider;

  private final Provider<Map<Class<?>, Verticle>> verticlesProvider;

  public DeployServer_Factory(
      Provider<Vertx> vertxProvider,
      Provider<ServerVerticle> serverProvider,
      Provider<BaseRouter> baseRouteProvider,
      Provider<Map<Class<?>, DeploymentOptions>> verticleConfigsProvider,
      Provider<Map<Class<?>, Verticle>> verticlesProvider) {
    this.vertxProvider = vertxProvider;
    this.serverProvider = serverProvider;
    this.baseRouteProvider = baseRouteProvider;
    this.verticleConfigsProvider = verticleConfigsProvider;
    this.verticlesProvider = verticlesProvider;
  }

  @Override
  public DeployServer get() {
    return provideInstance(
        vertxProvider,
        serverProvider,
        baseRouteProvider,
        verticleConfigsProvider,
        verticlesProvider);
  }

  public static DeployServer provideInstance(
      Provider<Vertx> vertxProvider,
      Provider<ServerVerticle> serverProvider,
      Provider<BaseRouter> baseRouteProvider,
      Provider<Map<Class<?>, DeploymentOptions>> verticleConfigsProvider,
      Provider<Map<Class<?>, Verticle>> verticlesProvider) {
    return new DeployServer(
        vertxProvider.get(),
        serverProvider.get(),
        baseRouteProvider.get(),
        verticleConfigsProvider.get(),
        verticlesProvider.get());
  }

  public static DeployServer_Factory create(
      Provider<Vertx> vertxProvider,
      Provider<ServerVerticle> serverProvider,
      Provider<BaseRouter> baseRouteProvider,
      Provider<Map<Class<?>, DeploymentOptions>> verticleConfigsProvider,
      Provider<Map<Class<?>, Verticle>> verticlesProvider) {
    return new DeployServer_Factory(
        vertxProvider,
        serverProvider,
        baseRouteProvider,
        verticleConfigsProvider,
        verticlesProvider);
  }

  public static DeployServer newDeployServer(
      Vertx vertx,
      ServerVerticle server,
      BaseRouter baseRoute,
      Map<Class<?>, DeploymentOptions> verticleConfigs,
      Map<Class<?>, Verticle> verticles) {
    return new DeployServer(vertx, server, baseRoute, verticleConfigs, verticles);
  }
}
