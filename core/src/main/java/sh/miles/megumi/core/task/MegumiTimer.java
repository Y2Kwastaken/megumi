package sh.miles.megumi.core.task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import lombok.Getter;

public final class MegumiTimer implements Runnable {

    private final Map<Long, Set<Runnable>> tasks;
    @Getter
    private final long counter;
    private MegumiTask task;

    @Getter
    private long tick;

    private MegumiTimer(final long seconds) {
        this.tasks = new HashMap<>();
        this.counter = seconds;
        this.tick = seconds;
    }

    public void listen(final long tick, final Runnable task) {
        final Set<Runnable> runnables = this.tasks.getOrDefault(tick, new HashSet<>());
        runnables.add(task);
        this.tasks.put(tick, runnables);
    }

    public void start() {
        this.task = MegumiTaskBuilder.builder()
                .runnable(this)
                .timeUnit(TimeUnit.SECONDS)
                .delay(0)
                .period(this.counter)
                .build()
                .make();

        this.task.runAsync();
    }

    public void stop() {
        this.task.cancel();
        this.task = null;
    }

    @Override
    public void run() {

        final Set<Runnable> runnables = this.tasks.get(this.tick);

        if (runnables != null) {
            runnables.forEach(Runnable::run);
        }

        if (this.tick <= 0) {
            this.task.cancel();
        }

        this.tick--;
    }

    public static MegumiTimer of(final long seconds) {
        return new MegumiTimer(seconds);
    }
}
