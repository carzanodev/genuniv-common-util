package carzanodev.genuniv.microservices.common.util.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public abstract class DataCache<K, V> {

    private Map<K, V> cache = new HashMap<>();
    private CacheContext context = null;
    private long lastLoaded = 0;

    public void registerContext(CacheContext context) {
        this.context = context;
    }

    public final void load(boolean force) {
        if (mustLoad() || force) {
            LoadResult result = retrieve();

            if (result.isSuccess) {
                Map<K, V> newMap = new HashMap<>();
                for (V v : result.result) {
                    K key = id(v);
                    newMap.put(key, v);
                }

                this.cache = newMap;
                this.lastLoaded = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

                Optional<CacheContext> optionalContext = Optional.ofNullable(this.context);
                if (optionalContext.isPresent()) {
                    this.context.loadToContext(this);
                }
            }
        }
    }

    protected abstract boolean mustLoad();

    protected abstract K id(V v);

    protected abstract LoadResult retrieve();

    @AllArgsConstructor
    @NoArgsConstructor
    protected class LoadResult {
        protected Collection<V> result;
        protected boolean isSuccess;
    }

}
