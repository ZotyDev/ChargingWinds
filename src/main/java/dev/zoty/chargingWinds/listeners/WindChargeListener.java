package dev.zoty.chargingWinds.listeners;

import dev.zoty.chargingWinds.ChargingWinds;
import dev.zoty.chargingWinds.player.PlayerHelper;
import dev.zoty.chargingWinds.utils.CustomExplosions;
import dev.zoty.chargingWinds.utils.MathUtils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

import java.util.Random;

public final class WindChargeListener implements Listener {

    private final Random random = new Random();

    @EventHandler
    public void onWindChargeLaunch(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();

        if (projectile instanceof WindCharge windCharge) {
            // Change the speed
            float speedFactor = ChargingWinds.getInstance().getSettings().getVelocity();
            windCharge.setAcceleration(windCharge.getAcceleration().multiply(speedFactor));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWindChargeHit(EntityExplodeEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof WindCharge) {
            // Cancels the default explosion since we can't change it in the required ways.
            event.setCancelled(true);

            Location location = event.getLocation();
            World world = location.getWorld();

            if (world != null) {
                float power = ChargingWinds.getInstance().getSettings().getPower();

                // Play the sound
                world.playSound(
                        location,
                        Sound.ENTITY_WIND_CHARGE_WIND_BURST,
                        SoundCategory.BLOCKS,
                        4.0F,
                        (1.0F + (random.nextFloat() - random.nextFloat()) * 0.2F) * 0.7F
                );

                // Add particles
                Particle particle = ChargingWinds.getInstance().getSettings().getParticle();
                int particleAmount = ChargingWinds.getInstance().getSettings().getParticleAmount();
                // If there are more than 1 particle, spread them
                if (particleAmount > 1) {
                    for (int i = 0; i < particleAmount; i++) {
                        world.spawnParticle(particle, location, 1, MathUtils.randomParticleOffset(power), MathUtils.randomParticleOffset(power), MathUtils.randomParticleOffset(power));
                    }
                } else {
                    world.spawnParticle(particle, location,1 );
                }

                // Do custom explosion
                CustomExplosions.windExplode(location, power);
            }
        }
    }

    //
    @EventHandler
    public void onWindChargeDamage(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) return;
        PlayerHelper playerHelper = PlayerHelper.getPlayer(entity.getUniqueId());
        if (playerHelper == null) return;
        if (playerHelper.hasWindChargeEffect()) {
            event.setDamage(0.0D);
        }
    }
}