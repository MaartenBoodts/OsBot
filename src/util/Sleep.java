package util;

import org.osbot.rs07.utility.ConditionalSleep;

import java.util.function.BooleanSupplier;

public final class Sleep extends ConditionalSleep {

    private final BooleanSupplier condition;

    private Sleep(final BooleanSupplier condition, final int timeout) {
        super(timeout);
        this.condition = condition;
    }

    private Sleep(final BooleanSupplier condition, final int timeout, final int sleeptime) {
        super(timeout, sleeptime);
        this.condition = condition;
    }

    @Override
    public final boolean condition() throws InterruptedException {
        return condition.getAsBoolean();
    }

    public static boolean sleepUntil(final BooleanSupplier condition, final int timeout) {
        return new Sleep(condition, timeout).sleep();
    }

    public static boolean sleepUntil(final BooleanSupplier condition, final int timeout, final int sleeptime) {
        return new Sleep(condition, timeout, sleeptime).sleep();
    }
}