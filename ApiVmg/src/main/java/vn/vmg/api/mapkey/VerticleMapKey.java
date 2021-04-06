package vn.vmg.api.mapkey;

import dagger.MapKey;

/**
 * Create dagger map <class, object>
 * @author VMG
 *
 */
@MapKey
public @interface VerticleMapKey {
  Class<?> verticleClass();
}
