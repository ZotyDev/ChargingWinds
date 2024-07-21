package dev.zoty.chargingWinds.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.WindCharge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;

public final class WindChargeListener implements Listener {
    @EventHandler
    public void onWindChargeLaunch(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();

        if (projectile instanceof WindCharge) {
            float speedFactor = 0.1f;
            Vector newAcceleration = new Vector();
            newAcceleration = ((WindCharge) projectile).getAcceleration();
            newAcceleration.setX(newAcceleration.getX() * speedFactor);
            newAcceleration.setY(newAcceleration.getY() * speedFactor);
            newAcceleration.setZ(newAcceleration.getZ() * speedFactor);

            ((WindCharge) projectile).setAcceleration(newAcceleration);
        }
    }
}