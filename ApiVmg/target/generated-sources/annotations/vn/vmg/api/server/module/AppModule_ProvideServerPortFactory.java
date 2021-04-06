package vn.vmg.api.server.module;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideServerPortFactory implements Factory<Integer> {
  private final AppModule module;

  public AppModule_ProvideServerPortFactory(AppModule module) {
    this.module = module;
  }

  @Override
  public Integer get() {
    return provideInstance(module);
  }

  public static Integer provideInstance(AppModule module) {
    return proxyProvideServerPort(module);
  }

  public static AppModule_ProvideServerPortFactory create(AppModule module) {
    return new AppModule_ProvideServerPortFactory(module);
  }

  public static int proxyProvideServerPort(AppModule instance) {
    return instance.provideServerPort();
  }
}
