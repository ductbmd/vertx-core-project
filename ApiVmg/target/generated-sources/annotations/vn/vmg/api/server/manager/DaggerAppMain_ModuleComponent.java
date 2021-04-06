package vn.vmg.api.server.manager;

import com.google.common.collect.ImmutableMap;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import java.util.Map;
import javax.annotation.Generated;
import javax.inject.Provider;
import vn.vmg.api.db.base.DbConnectImpl;
import vn.vmg.api.server.BaseRouter;
import vn.vmg.api.server.DeployServer;
import vn.vmg.api.server.DeployServer_Factory;
import vn.vmg.api.server.ServerVerticle;
import vn.vmg.api.server.module.AppModule;
import vn.vmg.api.server.module.AppModule_ProvideServerPortFactory;
import vn.vmg.api.server.module.AppModule_ProvideVertxFactory;
import vn.vmg.api.server.module.AppModule_RouterFactory;
import vn.vmg.api.server.module.DatabaseModule;
import vn.vmg.api.server.module.DatabaseModule_ProviderDbFactory;
import vn.vmg.api.server.module.EventBusModule;
import vn.vmg.api.server.module.EventBusModule_AllSerEventBusFactory;
import vn.vmg.api.server.module.EventBusModule_AllSerEventBusOptionFactory;
import vn.vmg.api.server.module.RoutersModule;
import vn.vmg.api.server.module.RoutersModule_RouteAllSerVerticleFactory;
import vn.vmg.api.server.module.ThreadModule;
import vn.vmg.api.server.module.ThreadModule_ReloadConfigVerticleOptionFactory;
import vn.vmg.api.server.module.ThreadModule_RouteReloadConfigVerticleFactory;
import vn.vmg.api.thread.ReloadConfigVerticle;
import vn.vmg.api.vertx.eventbus.AllSerEventBus;
import vn.vmg.api.vertx.routers.AllSerRoute;
import vn.vmg.api.vertx.service.handle.AllSerDb;
import vn.vmg.api.vertx.service.handle.AllSerDb_Factory;
import vn.vmg.api.vertx.service.handle.AllSerHandle;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerAppMain_ModuleComponent implements AppMain.ModuleComponent {
  private EventBusModule eventBusModule;

  private ThreadModule threadModule;

  private RoutersModule routersModule;

  private Provider<Vertx> provideVertxProvider;

  private Provider<Router> RouterProvider;

  private Provider<Integer> provideServerPortProvider;

  private Provider<DbConnectImpl> providerDbProvider;

  private Provider<AllSerDb> allSerDbProvider;

  private DaggerAppMain_ModuleComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static AppMain.ModuleComponent create() {
    return new Builder().build();
  }

  private ServerVerticle getServerVerticle() {
    return new ServerVerticle(RouterProvider.get(), provideServerPortProvider.get());
  }

  private BaseRouter getBaseRouter() {
    return new BaseRouter(RouterProvider.get());
  }

  private Map<Class<?>, DeploymentOptions> getMapOfClassOfAndDeploymentOptions() {
    return ImmutableMap.<Class<?>, DeploymentOptions>of(
        AllSerEventBus.class,
        EventBusModule_AllSerEventBusOptionFactory.proxyAllSerEventBusOption(eventBusModule),
        ReloadConfigVerticle.class,
        ThreadModule_ReloadConfigVerticleOptionFactory.proxyReloadConfigVerticleOption(
            threadModule));
  }

  private AllSerRoute getAllSerRoute() {
    return new AllSerRoute(RouterProvider.get());
  }

  private Verticle getMapOfClassOfAndProviderOfVerticle() {
    return RoutersModule_RouteAllSerVerticleFactory.proxyRouteAllSerVerticle(
        routersModule, getAllSerRoute());
  }

  private AllSerHandle getAllSerHandle() {
    return new AllSerHandle(allSerDbProvider.get());
  }

  private AllSerEventBus getAllSerEventBus() {
    return new AllSerEventBus(getAllSerHandle());
  }

  private Verticle getMapOfClassOfAndProviderOfVerticle2() {
    return EventBusModule_AllSerEventBusFactory.proxyAllSerEventBus(
        eventBusModule, getAllSerEventBus());
  }

  private Verticle getMapOfClassOfAndProviderOfVerticle3() {
    return ThreadModule_RouteReloadConfigVerticleFactory.proxyRouteReloadConfigVerticle(
        threadModule, new ReloadConfigVerticle());
  }

  private Map<Class<?>, Verticle> getMapOfClassOfAndVerticle() {
    return ImmutableMap.<Class<?>, Verticle>of(
        AllSerRoute.class,
        getMapOfClassOfAndProviderOfVerticle(),
        AllSerEventBus.class,
        getMapOfClassOfAndProviderOfVerticle2(),
        ReloadConfigVerticle.class,
        getMapOfClassOfAndProviderOfVerticle3());
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.provideVertxProvider =
        DoubleCheck.provider(AppModule_ProvideVertxFactory.create(builder.appModule));
    this.RouterProvider =
        DoubleCheck.provider(
            AppModule_RouterFactory.create(builder.appModule, provideVertxProvider));
    this.provideServerPortProvider =
        DoubleCheck.provider(AppModule_ProvideServerPortFactory.create(builder.appModule));
    this.eventBusModule = builder.eventBusModule;
    this.threadModule = builder.threadModule;
    this.routersModule = builder.routersModule;
    this.providerDbProvider =
        DoubleCheck.provider(DatabaseModule_ProviderDbFactory.create(builder.databaseModule));
    this.allSerDbProvider = DoubleCheck.provider(AllSerDb_Factory.create(providerDbProvider));
  }

  @Override
  public DeployServer server() {
    return DeployServer_Factory.newDeployServer(
        provideVertxProvider.get(),
        getServerVerticle(),
        getBaseRouter(),
        getMapOfClassOfAndDeploymentOptions(),
        getMapOfClassOfAndVerticle());
  }

  public static final class Builder {
    private AppModule appModule;

    private EventBusModule eventBusModule;

    private ThreadModule threadModule;

    private RoutersModule routersModule;

    private DatabaseModule databaseModule;

    private Builder() {}

    public AppMain.ModuleComponent build() {
      if (appModule == null) {
        this.appModule = new AppModule();
      }
      if (eventBusModule == null) {
        this.eventBusModule = new EventBusModule();
      }
      if (threadModule == null) {
        this.threadModule = new ThreadModule();
      }
      if (routersModule == null) {
        this.routersModule = new RoutersModule();
      }
      if (databaseModule == null) {
        this.databaseModule = new DatabaseModule();
      }
      return new DaggerAppMain_ModuleComponent(this);
    }

    public Builder appModule(AppModule appModule) {
      this.appModule = Preconditions.checkNotNull(appModule);
      return this;
    }

    public Builder databaseModule(DatabaseModule databaseModule) {
      this.databaseModule = Preconditions.checkNotNull(databaseModule);
      return this;
    }

    public Builder routersModule(RoutersModule routersModule) {
      this.routersModule = Preconditions.checkNotNull(routersModule);
      return this;
    }

    public Builder eventBusModule(EventBusModule eventBusModule) {
      this.eventBusModule = Preconditions.checkNotNull(eventBusModule);
      return this;
    }

    public Builder threadModule(ThreadModule threadModule) {
      this.threadModule = Preconditions.checkNotNull(threadModule);
      return this;
    }
  }
}
