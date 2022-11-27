package sh.miles.megumi.task;

public enum TaskTime {

    TICKS,
    SECONDS,
    MINUTES,
    HOURS;

    TaskTime() {
    }

    public long convert(long time) {
        switch (this) {
            case TICKS:
                return time;
            case SECONDS:
                return time * 20;
            case MINUTES:
                return time * 1200;
            case HOURS:
                return time * 72000;
            default:
                return 0;
        }
    }

}
