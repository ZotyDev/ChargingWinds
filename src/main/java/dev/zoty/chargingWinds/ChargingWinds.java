package dev.zoty.chargingWinds;

import dev.zoty.chargingWinds.listeners.WindChargeListener;
import dev.zoty.chargingWinds.player.PlayerHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChargingWinds extends JavaPlugin {
    private static ChargingWinds instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new WindChargeListener(), this);

        // Helper that will register all players inside PlayerHelper, that way we can more easily manage the wind
        // charged effect to reduce fall damage.
        pluginManager.registerEvents(new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                new PlayerHelper(event.getPlayer());
            }
        }, instance);

        getLogger().info(getName() + " successfully enabled!");
    }

    public static ChargingWinds getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
