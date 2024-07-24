package dev.zoty.chargingWinds.listeners;

import dev.zoty.chargingWinds.ChargingWinds;
import dev.zoty.chargingWinds.player.PlayerHelper;
import dev.zoty.chargingWinds.utils.CustomExplosions;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public final class WindChargeListener implements Listener {

    private final Random random = new Random();

    @EventHandler
    public void onWindChargeLaunch(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();

        // Checks if the projectile is a Wind Charge
        if (projectile instanceof WindCharge windCharge) {
            // Change the speed
            float speedFactor = ChargingWinds.getInstance().getSettings().getVelocity();
            Vector newAcceleration;
            newAcceleration = windCharge.getAcceleration();
            newAcceleration.setX(newAcceleration.getX() * speedFactor);
            newAcceleration.setY(newAcceleration.getY() * speedFactor);
            newAcceleration.setZ(newAcceleration.getZ() * speedFactor);

            windCharge.setAcceleration(newAcceleration);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWindChargeHit(EntityExplodeEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof WindCharge windCharge) {
            event.setCancelled(true);

            Location location = event.getLocation();
            World world = location.getWorld();

            if (world != null) {
                // Play the sound
                world.playSound(
                        location,
                        Sound.ENTITY_WIND_CHARGE_WIND_BURST,
                        SoundCategory.BLOCKS,
                        4.0F,
                        (1.0F + (random.nextFloat() - random.nextFloat()) * 0.2F) * 0.7F
                );

                // Add particles
                world.spawnParticle(Particle.GUST_EMITTER_SMALL, location,1 );

                // Create custom explosion
                CustomExplosions.windExplode(
                        (Player) windCharge.getShooter(),
                        location, (float) ChargingWinds.getInstance().getSettings().getPower(),
                        (float) ChargingWinds.getInstance().getSettings().getKnockbackSize(),
                        true);
            }
        }
    }

    @EventHandler
    public void onWindChargeDamage(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) return;
        PlayerHelper playerHelper = PlayerHelper.getPlayer(entity.getUniqueId());
        if (playerHelper == null) return;
        if (playerHelper.hasWindChargeEffect()) {
            event.setCancelled(true);
        }
    }
}