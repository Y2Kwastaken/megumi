package sh.miles.megumi.core.task;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import lombok.RequiredArgsConstructor;
import sh.miles.megumi.core.PluginDistributor;

@RequiredArgsConstructor
public final class MegumiTask {

    private final MegumiTaskType type;
    private final TimeUnit timeUnit;
    private final Runnable runnable;
    private final long delay;
    private final long period;

    private BukkitTask bukkitTask;

    public void run() {
        switch (this.type) {
            case DELAYED -> this.bukkitTask = this.runDelay(false);
            case REPEATING -> this.bukkitTask = this.runRepeat(false);
            case INSTANT -> this.bukkitTask = this.runInstant(false);
        }
    }

    public void runAsync() {
        switch (this.type) {
            case DELAYED -> this.bukkitTask = this.runDelay(true);
            case REPEATING -> this.bukkitTask = this.runRepeat(true);
            case INSTANT -> this.bukkitTask = this.runInstant(true);
        }
    }

    public void cancel() {
        if (this.bukkitTask != null) {
            this.bukkitTask.cancel();
        }
    }

    private BukkitTask runDelay(boolean async) {
        if (async) {
            return Bukkit.getScheduler().runTaskLaterAsynchronously(PluginDistributor.getInstance(), this.runnable,
                    convertToTicks(this.delay));
        }

        return Bukkit.getScheduler().runTaskLater(PluginDistributor.getInstance(), this.runnable,
                convertToTicks(this.delay));
    }

    private BukkitTask runRepeat(boolean async) {
        if (async) {
            return Bukkit.getScheduler().runTaskTimerAsynchronously(PluginDistributor.getInstance(), this.runnable,
                    convertToTicks(this.delay), convertToTicks(this.period));
        }

        return Bukkit.getScheduler().runTaskTimer(PluginDistributor.getInstance(), this.runnable,
                convertToTicks(this.delay),
                convertToTicks(this.period));
    }

    private BukkitTask runInstant(boolean async) {
        if (async) {
            return Bukkit.getScheduler().runTaskAsynchronously(PluginDistributor.getInstance(), this.runnable);
        }

        return Bukkit.getScheduler().runTask(PluginDistributor.getInstance(), this.runnable);
    }

    private long convertToTicks(long time) {
        return TimeUnit.SECONDS.convert(time, this.timeUnit) * 20;
    }

    public static MegumiTask sync(Runnable runnable) {
        final MegumiTask task = new MegumiTask(MegumiTaskType.INSTANT, TimeUnit.SECONDS, runnable, 0, 0);
        task.run();
        return task;
    }

    public static MegumiTask async(Runnable runnable) {
        final MegumiTask task = new MegumiTask(MegumiTaskType.INSTANT, TimeUnit.SECONDS, runnable, 0, 0);
        task.runAsync();
        return task;
    }
}
