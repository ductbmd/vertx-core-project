package vn.vmg.api.server;

import dagger.internal.Factory;
import io.vertx.ext.web.Router;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BaseRouter_Factory implements Factory<BaseRouter> {
  private final Provider<Router> routerProvider;

  public BaseRouter_Factory(Provider<Router> routerProvider) {
    this.routerProvider = routerProvider;
  }

  @Override
  public BaseRouter get() {
    return provideInstance(routerProvider);
  }

  public static BaseRouter provideInstance(Provider<Router> routerProvider) {
    return new BaseRouter(routerProvider.get());
  }

  public static BaseRouter_Factory create(Provider<Router> routerProvider) {
    return new BaseRouter_Factory(routerProvider);
  }

  public static BaseRouter newBaseRouter(Router router) {
    return new BaseRouter(router);
  }
}
