package dev.zoty.chargingWinds.utils;

import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CustomExplosions {
    private static final Random RANDOM = new Random();

    public static void windExplode(@Nullable Entity source, Location location, float power) {
        float q = power * 2.0F;

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        List<Entity> entityList = (List<Entity>) location.getWorld().getNearbyEntities(new BoundingBox(
                Math.floor(x - (double) q - 1.0),
                Math.floor(y - (double) q - 1.0),
                Math.floor(z - (double) q - 1.0),
                Math.floor(x + (double) q + 1.0),
                Math.floor(y + (double) q + 1.0),
                Math.floor(z + (double) q + 1.0)
        ));
        Vector vector = new Vector(x, y, z);
        Iterator<Entity> iterator = entityList.iterator();

        while (true) {
            Entity entity;

            double dx;
            double dy;
            double dz;
            double dw;
            double dv;

            do {
                do {
                    do {
                        if (!iterator.hasNext()) {
                            return;
                        }

                        entity = iterator.next();
                    } while (entity instanceof ArmorStand || entity instanceof Warden);

                    dw = Math.sqrt(entity.getLocation().distanceSquared(location)) / (double) q;
                } while (!(dw <= 1.0));

                dx = entity.getLocation().getX() - x;
                dy = (entity instanceof TNTPrimed ? entity.getLocation().getY() : (entity instanceof LivingEntity ? ((LivingEntity) entity).getEyeLocation().getY() : entity.getLocation().getY())) - y;
                dz = entity.getLocation().getZ() - z;
                dv = Math.sqrt(dx * dx + dy * dy + dz * dz);
            } while (dv == 0.0);

            dx /= dv;
            dy /= dv;
            dz /= dv;

            double aa = (1.0 - dw) * (double) getExposure(location, entity) * (double) getKnockbackModifier(entity);
            double ab;
            if (entity instanceof LivingEntity livingEntity) {
                ab = aa * (1.0 - livingEntity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getValue());
            } else {
                ab = aa;
            }

            dx *= ab;
            dy *= ab;
            dz *= ab;
            Vector explosionVector = new Vector(dx, dy, dz);
            entity.setVelocity(entity.getVelocity().add(explosionVector));
        }
    }

    private static float getExposure(Location source, Entity entity) {
        BoundingBox box = entity.getBoundingBox();
        double d = 1.0 / ((box.getMaxX() - box.getMinX()) * 2.0 + 1.0);
        double e = 1.0 / ((box.getMaxY() - box.getMinY()) * 2.0 + 1.0);
        double f = 1.0 / ((box.getMaxZ() - box.getMinZ()) * 2.0 + 1.0);
        double g = (1.0 - Math.floor(1.0 / d) * d) / 2.0;
        double h = (1.0 - Math.floor(1.0 / f) * f) / 2.0;

        if (d < 0.0 || e < 0.0 || f < 0.0) {
            return 0.0F;
        }

        int i = 0;
        int j = 0;
        World world = entity.getWorld();
        Vector sourceVector = source.toVector();

        for (double k = 0.0; k <= 1.0; k += d) {
            for (double l = 0.0; l <= 1.0; l += e) {
                for (double m = 0.0; m <= 1.0; m += f) {
                    double n = lerp(k, box.getMinX(), box.getMaxX());
                    double o = lerp(l, box.getMinY(), box.getMaxY());
                    double p = lerp(m, box.getMinZ(), box.getMaxZ());
                    Vector vec3d = new Vector(n + g, o, p + h);

                    RayTraceResult result = world.rayTrace(vec3d.toLocation(world), sourceVector.subtract(vec3d), sourceVector.distance(vec3d), FluidCollisionMode.NEVER, true, 0.0, entt -> entt == entity);

                    if (result == null || result.getHitBlock() == null) {
                        ++i;
                    }
                    ++j;
                }
            }
        }
        return (float) i / (float) j;
    }

    private static double lerp(double delta, double start, double end) {
        return start + delta * (end - start);
    }

    private static float getKnockbackModifier(Entity entity) {
        boolean isFlying = false;

        if (entity instanceof Player player) {
            isFlying = player.isFlying();
        }

        return isFlying ? 0.0F : 1.2F;
    }
}
