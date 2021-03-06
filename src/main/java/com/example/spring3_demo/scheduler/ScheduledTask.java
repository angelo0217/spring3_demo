package com.example.spring3_demo.scheduler;

import java.util.concurrent.ScheduledFuture;

public final class ScheduledTask {
    public volatile ScheduledFuture<?> future;

    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}