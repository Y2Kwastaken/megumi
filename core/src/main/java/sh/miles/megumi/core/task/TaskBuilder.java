package sh.miles.megumi.core.task;

import lombok.NonNull;

/**
 * Builder for creating bukkit tasks easily
 */
public class TaskBuilder {

    private TaskType type;
    private TaskModifierType modifier;
    private Runnable task;
    private TaskTime timeUnit;
    private long delay;
    private long period;

    private TaskBuilder() {
        this.type = TaskType.RUN;
        this.modifier = TaskModifierType.SYNC;
        this.task = () -> {
        };
        this.timeUnit = TaskTime.TICKS;
    }

    public TaskBuilder type(@NonNull final TaskType type) {
        this.type = type;
        return this;
    }

    public TaskBuilder modifier(@NonNull final TaskModifierType modifier) {
        this.modifier = modifier;
        return this;
    }

    public TaskBuilder task(@NonNull final Runnable task) {
        this.task = task;
        return this;
    }

    public TaskBuilder delay(final long delay) {

        if (delay < 0) {
            throw new IllegalArgumentException("Delay cannot be negative");
        }

        this.delay = delay;
        return this;
    }

    public TaskBuilder period(final long period) {

        if (period < 0) {
            throw new IllegalArgumentException("Period cannot be negative");
        }

        this.period = period;
        return this;
    }

    public TaskBuilder timeUnit(@NonNull final TaskTime timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    public Task build() {
        return new Task(this.type, this.modifier, this.timeUnit, this.task, this.delay, this.period);
    }

    public static TaskBuilder builder() {
        return new TaskBuilder();
    }

    public static TaskBuilder of(@NonNull final Runnable task) {
        return new TaskBuilder().task(task);
    }

    public static TaskBuilder of(@NonNull final TaskType type, @NonNull final Runnable task) {
        return new TaskBuilder().type(type).task(task);
    }

    public static TaskBuilder of(@NonNull final TaskType type, @NonNull final TaskModifierType modifier,
            @NonNull final Runnable task) {
        return new TaskBuilder().type(type).modifier(modifier).task(task);
    }

}
