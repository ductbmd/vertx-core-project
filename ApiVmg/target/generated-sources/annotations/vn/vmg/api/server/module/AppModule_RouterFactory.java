package vn.vmg.api.server.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_RouterFactory implements Factory<Router> {
  private final AppModule module;

  private final Provider<Vertx> vertxProvider;

  public AppModule_RouterFactory(AppModule module, Provider<Vertx> vertxProvider) {
    this.module = module;
    this.vertxProvider = vertxProvider;
  }

  @Override
  public Router get() {
    return provideInstance(module, vertxProvider);
  }

  public static Router provideInstance(AppModule module, Provider<Vertx> vertxProvider) {
    return proxyRouter(module, vertxProvider.get());
  }

  public static AppModule_RouterFactory create(AppModule module, Provider<Vertx> vertxProvider) {
    return new AppModule_RouterFactory(module, vertxProvider);
  }

  public static Router proxyRouter(AppModule instance, Vertx vertx) {
    return Preconditions.checkNotNull(
        instance.Router(vertx), "Cannot return null from a non-@Nullable @Provides method");
  }
}
