package dev.zoty.chargingWinds;

public class Settings {

    private static final String POWER = "power";
    private static final Double DEFAULT_POWER = 1.2D;
    private static final String VELOCITY = "velocity";
    private static final Double DEFAULT_VELOCITY = 1.0D;
    private static final String KNOCKBACK_SIZE = "knockbackSize";
    private static final Double DEFAULT_KNOCKBACK_SIZE = 1.0D;

    public Float getPower() {
        return ((Double) ChargingWinds.getInstance().getConfig().getDouble(POWER, DEFAULT_POWER)).floatValue();
    }

    public Float getVelocity() {
        return ((Double) ChargingWinds.getInstance().getConfig().getDouble(VELOCITY, DEFAULT_VELOCITY)).floatValue();
    }

    public Float getKnockbackSize() {
        return ((Double) ChargingWinds.getInstance().getConfig().getDouble(KNOCKBACK_SIZE, DEFAULT_KNOCKBACK_SIZE)).floatValue();
    }
}
