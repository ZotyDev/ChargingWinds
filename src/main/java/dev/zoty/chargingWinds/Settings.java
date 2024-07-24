package dev.zoty.chargingWinds;

public class Settings {

    private static final String POWER = "power";
    private static final String VELOCITY = "velocity";
    private static final String KNOCKBACK_SIZE = "knockbackSize";

    public Float getPower() {
        return ((Double) ChargingWinds.getInstance().getConfig().getDouble(POWER, 1.0D)).floatValue();
    }

    public Float getVelocity() {
        return ((Double) ChargingWinds.getInstance().getConfig().getDouble(VELOCITY, 1.0D)).floatValue();
    }

    public Float getKnockbackSize() {
        return ((Double) ChargingWinds.getInstance().getConfig().getDouble(KNOCKBACK_SIZE, 1.0D)).floatValue();
    }

    public void setPower(Float force) {
        ChargingWinds.getInstance().getConfig().set(POWER, force);
    }

    public void setVelocity(Float velocity) {
        ChargingWinds.getInstance().getConfig().set(VELOCITY, velocity);
    }

    public void setKnockbackSize(Float knockbackSize) {
        ChargingWinds.getInstance().getConfig().set(KNOCKBACK_SIZE, knockbackSize);
    }
}
