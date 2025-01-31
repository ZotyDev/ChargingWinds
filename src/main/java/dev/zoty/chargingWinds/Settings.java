package dev.zoty.chargingWinds;

import org.bukkit.Particle;

// Helper to manage configs, I called it Settings because this way there are no conflicts with .getConfig().
// The methods inside this class are wrappers that are intended to be used instead of, i.e. .getConfig().getDouble().
public class Settings {
    private static final String POWER = "power";
    private static final Double DEFAULT_POWER = 1.2D;
    private static final String VELOCITY = "velocity";
    private static final Double DEFAULT_VELOCITY = 1.0D;
    private static final String PARTICLE = "particle";
    private static final String DEFAULT_PARTICLE = "GUST_EMITTER_SMALL"; // Make sure this is valid, otherwise the code will crash
    private static final String PARTICLE_AMOUNT = "particle-amount";
    private static final int DEFAULT_PARTICLE_AMOUNT = 1;

    public static float getPower() {
        return ((Double) ChargingWinds.getInstance().getConfig().getDouble(POWER, DEFAULT_POWER)).floatValue();
    }

    public static float getVelocity() {
        return ((Double) ChargingWinds.getInstance().getConfig().getDouble(VELOCITY, DEFAULT_VELOCITY)).floatValue();
    }

    // Will convert the string into a valid Particle, if the String doesn't represent a valid particle then it returns
    // the default particle. If the default particle is invalid there is not much we can do.
    public static Particle getParticle() {
        String particleString = ChargingWinds.getInstance().getConfig().getString(PARTICLE, DEFAULT_PARTICLE).toUpperCase();
        try {
            return Particle.valueOf(particleString);
        } catch (IllegalArgumentException e) {
            ChargingWinds.getInstance().getLogger().warning("Invalid particle name in config.yml: " + particleString);
            ChargingWinds.getInstance().getLogger().warning("Defaulting to: " + DEFAULT_PARTICLE);
            return Particle.valueOf(DEFAULT_PARTICLE);
        }
    }

    public static int getParticleAmount() {
        int particleAmount = ChargingWinds.getInstance().getConfig().getInt(PARTICLE_AMOUNT, DEFAULT_PARTICLE_AMOUNT);
        if (particleAmount < 1) {
            return  DEFAULT_PARTICLE_AMOUNT;
        } else {
            return particleAmount;
        }
    }
}
