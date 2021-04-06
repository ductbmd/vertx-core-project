package vn.vmg.api.thread;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ReloadConfigVerticle_Factory implements Factory<ReloadConfigVerticle> {
  private static final ReloadConfigVerticle_Factory INSTANCE = new ReloadConfigVerticle_Factory();

  @Override
  public ReloadConfigVerticle get() {
    return provideInstance();
  }

  public static ReloadConfigVerticle provideInstance() {
    return new ReloadConfigVerticle();
  }

  public static ReloadConfigVerticle_Factory create() {
    return INSTANCE;
  }

  public static ReloadConfigVerticle newReloadConfigVerticle() {
    return new ReloadConfigVerticle();
  }
}
