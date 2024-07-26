package dev.zoty.chargingWinds.utils;

import dev.zoty.chargingWinds.player.PlayerHelper;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Iterator;
import java.util.List;

public class CustomExplosions {
    // This entire code was created using deobf from Fabric as the reference, I don't know how exact this is, but I
    // tried my best at reimplementing the explosion mechanic from Minecraft 1.21. Sure, it could break after updates,
    // but this plugin was created just to fulfill a 5-day task.
    public static void windExplode(Location location, float power) {
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
                    } while (entity instanceof ArmorStand || entity instanceof Warden); // The only entities that are immune to explosions

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
            // Magic value needed to mimic minecraft's exact behavior
            // Note: I tried to find the reason behind this, but couldn't. At least it works, so we should not question
            // it too much :D
            dy /= 10.0;
            Vector explosionVector = new Vector(dx, dy, dz).multiply(power);
            entity.setVelocity(entity.getVelocity().add(explosionVector));

            // Additional logic to  handle
            if (entity instanceof Player player) {
                PlayerHelper playerHelper = PlayerHelper.getPlayer(player);
                if (playerHelper == null) {
                    return;
                } else {
                    playerHelper.setWindChargedLocation(player.getLocation());
                }
            }
        }
    }

    // This method was implemented using Fabric deobf as reference.
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
                    double n = MathUtils.lerp(k, box.getMinX(), box.getMaxX());
                    double o = MathUtils.lerp(l, box.getMinY(), box.getMaxY());
                    double p = MathUtils.lerp(m, box.getMinZ(), box.getMaxZ());
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

    // This method was implemented using Fabric deobf as reference.
    private static float getKnockbackModifier(Entity entity) {
        boolean isFlying = false;

        if (entity instanceof Player player) {
            isFlying = player.isFlying();
        }

        // This 1.2F value is not a magic number, it is the knockback modifier for wind charges
        return isFlying ? 0.0F : 1.2F;
    }
}
