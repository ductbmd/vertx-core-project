package vn.vmg.api.vertx.routers;

import dagger.internal.Factory;
import io.vertx.ext.web.Router;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AllSerRoute_Factory implements Factory<AllSerRoute> {
  private final Provider<Router> routerProvider;

  public AllSerRoute_Factory(Provider<Router> routerProvider) {
    this.routerProvider = routerProvider;
  }

  @Override
  public AllSerRoute get() {
    return provideInstance(routerProvider);
  }

  public static AllSerRoute provideInstance(Provider<Router> routerProvider) {
    return new AllSerRoute(routerProvider.get());
  }

  public static AllSerRoute_Factory create(Provider<Router> routerProvider) {
    return new AllSerRoute_Factory(routerProvider);
  }

  public static AllSerRoute newAllSerRoute(Router router) {
    return new AllSerRoute(router);
  }
}
