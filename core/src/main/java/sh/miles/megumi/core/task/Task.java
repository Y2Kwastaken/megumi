package sh.miles.megumi.core.task;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Task {

    public static final int NO_DELAY = -999;
    public static final int NO_PERIOD = -999;

    private final TaskType type;
    private final TaskModifierType modifier;
    private final TaskTime timeUnit;
    private final Runnable task;
    private final long delay;
    private final long period;

    private BukkitTask bukkitTask;

    public Task(TaskType type, TaskModifierType modifier, TaskTime timeUnit, Runnable task, long delay, long period) {
        this.type = type;
        this.modifier = modifier;
        this.timeUnit = timeUnit;
        this.task = task;
        this.delay = delay;
        this.period = period;
    }

    public void run(@NonNull final Plugin plugin) {
        switch (this.type) {
            case DELAY -> {
                final long convertedDelay = this.timeUnit.convert(this.delay);
                if (this.modifier == TaskModifierType.SYNC) {
                    this.bukkitTask = plugin.getServer().getScheduler().runTaskLater(plugin, this.task, convertedDelay);
                } else {
                    this.bukkitTask = plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, this.task,
                            convertedDelay);
                }
            }
            case REPEAT -> {
                final long convertedPeriod = this.timeUnit.convert(this.period);
                final long convertedDelay = this.timeUnit.convert(this.delay);
                if (this.modifier == TaskModifierType.SYNC) {
                    this.bukkitTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this.task, convertedDelay,
                            convertedPeriod);
                } else {
                    this.bukkitTask = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this.task,
                            convertedDelay,
                            convertedPeriod);
                }
            }
            case RUN -> {
                if (this.modifier == TaskModifierType.SYNC) {
                    this.bukkitTask = plugin.getServer().getScheduler().runTask(plugin, this.task);
                } else {
                    this.bukkitTask = plugin.getServer().getScheduler().runTaskAsynchronously(plugin, this.task);
                }
            }
            default -> {
                throw new IllegalStateException("Unexpected value: " + this.type);
            }
        }
    }

    public void cancel() {
        if (this.bukkitTask != null) {
            this.bukkitTask.cancel();
        }
    }

    public static Task runAsync(@NonNull final Runnable task, @NonNull final Plugin plugin) {
        final Task t = new Task(TaskType.RUN, TaskModifierType.ASYNC, TaskTime.TICKS, task, NO_DELAY, NO_PERIOD);
        t.run(plugin);
        return t;
    }

    public static Task runSync(@NonNull final Runnable task, @NonNull final Plugin plugin) {
        final Task t = new Task(TaskType.RUN, TaskModifierType.SYNC, TaskTime.TICKS, task, NO_DELAY, NO_PERIOD);
        t.run(plugin);
        return t;
    }

}
