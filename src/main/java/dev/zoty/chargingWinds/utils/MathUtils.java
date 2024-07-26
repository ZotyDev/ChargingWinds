package dev.zoty.chargingWinds.utils;

import java.util.Random;

public class MathUtils {
    private static final Random random = new Random();

    // Helper to return a somewhat good offset position for the particles
    public static float randomParticleOffset(float multiplier) {
        return (-1.0F + random.nextFloat()) * Math.max(multiplier / 2.0F, 1.0F);
    }

    public static double lerp(double delta, double start, double end) {
        return start + delta * (end - start);
    }
}
