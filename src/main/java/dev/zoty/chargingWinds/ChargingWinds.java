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
    private Settings settings;

    @Override
    public void onEnable() {
        instance = this;

        // Plugin startup logic
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new WindChargeListener(), this);

        pluginManager.registerEvents(new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                new PlayerHelper(event.getPlayer());
            }
        }, instance);

        this.settings = new Settings();

        getLogger().info(getName() + " successfully enabled!");
    }

    public static ChargingWinds getInstance() {
        return instance;
    }

    public Settings getSettings() {
        return settings;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
