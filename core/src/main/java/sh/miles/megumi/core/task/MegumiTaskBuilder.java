package sh.miles.megumi.core.task;

import java.util.concurrent.TimeUnit;

import lombok.experimental.SuperBuilder;

/**
 * MegumiTaskBuilder is used to create a MegumiTask. More about why you should
 * use MegumiTask over BukkitTask can be found in the MegumiTask class.
 * {@link MegumiTask}
 */
@SuperBuilder
public class MegumiTaskBuilder {

    private MegumiTaskType type;
    private TimeUnit timeUnit;
    private Runnable runnable;
    private long delay;
    private long period;

    public MegumiTask make() {
        return new MegumiTask(type, timeUnit, runnable, delay, period);
    }
}
