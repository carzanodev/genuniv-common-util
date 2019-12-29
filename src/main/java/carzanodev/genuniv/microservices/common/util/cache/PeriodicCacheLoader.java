package carzanodev.genuniv.microservices.common.util.cache;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PeriodicCacheLoader<T extends DataCache> {

    private final DataCache dataCache;
    private final int periodIntervalSeconds;
    private final int forceIntervalSeconds;
    private final ScheduledExecutorService executor;

    private ScheduledFuture<?> future;

    public PeriodicCacheLoader(DataCache dataCache, int periodIntervalSeconds, int forceIntervalSeconds) {
        this.dataCache = dataCache;
        this.periodIntervalSeconds = periodIntervalSeconds;
        this.forceIntervalSeconds = forceIntervalSeconds;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() throws IllegalStateException {
        if (future == null) {
            future =
                    executor.scheduleWithFixedDelay(
                            this::load,
                            0,
                            periodIntervalSeconds,
                            TimeUnit.SECONDS);
        } else {
            throw new IllegalStateException("Loader had already started!");
        }
    }

    public void stop(boolean forceStop) {
        this.future.cancel(forceStop);
        this.executor.shutdown();
    }

    private void load() {
        long lastLoadedSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - dataCache.getLastLoaded();
        boolean isForceLoad = lastLoadedSeconds >= forceIntervalSeconds;

        dataCache.load(isForceLoad);
    }

}
