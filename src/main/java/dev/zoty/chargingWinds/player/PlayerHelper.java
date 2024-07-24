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
    private static final Map<UUID, PlayerHelper> players = new HashMap<>();

    private final Player player;
    private Location windChargedLocation = null;
    private BukkitTask task = null;

    public PlayerHelper(Player player) {
        this.player = player;
        players.put(player.getUniqueId(), this);
    }

    public static PlayerHelper getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public static PlayerHelper getPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    // Will set the wind charged location, after 40 ticks the effect wears off.
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

    // Check if player should be damaged or not.
    public boolean hasWindChargedEffect() {
        // Wind charged effect will only be applied if the fall distance is less than 1.5 blocks down the origin.
        return this.windChargedLocation != null && this.windChargedLocation.getY() - 1.5D <= player.getLocation().getY();
    }
}
