package vn.vmg.api.server.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.vertx.core.Vertx;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideVertxFactory implements Factory<Vertx> {
  private final AppModule module;

  public AppModule_ProvideVertxFactory(AppModule module) {
    this.module = module;
  }

  @Override
  public Vertx get() {
    return provideInstance(module);
  }

  public static Vertx provideInstance(AppModule module) {
    return proxyProvideVertx(module);
  }

  public static AppModule_ProvideVertxFactory create(AppModule module) {
    return new AppModule_ProvideVertxFactory(module);
  }

  public static Vertx proxyProvideVertx(AppModule instance) {
    return Preconditions.checkNotNull(
        instance.provideVertx(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
