package dev.zoty.chargingWinds.player;

import dev.zoty.chargingWinds.ChargingWinds;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerHelper {
    private static Map<UUID, PlayerHelper> players = new HashMap<>();

    private final Player player;
    private Location windChargedLocation = null;
    private boolean hasHit = false;
    private BukkitTask task = null;

    public PlayerHelper(Player player) {
        this.player = player;
        players.put(player.getUniqueId(), this);
    }

    public Player getPlayer() {
        return player;
    }

    public static PlayerHelper getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public static PlayerHelper getPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public Location getWindChargedLocation() {
        return windChargedLocation;
    }

    public void setWindChargedLocation(Location windChargeLocation) {
        this.windChargedLocation = windChargeLocation;
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
        task = new BukkitRunnable() {
            public void run() {
                setWindChargedLocation();
            }
        }.runTaskLater(ChargingWinds.getInstance(), 40L);
    }

    private void setWindChargedLocation() {
        this.windChargedLocation = null;
    }

    public boolean hasWindChargeEffect() {
        return this.windChargedLocation != null && this.windChargedLocation.getY() - 1.5D <= player.getLocation().getY();
    }
}
