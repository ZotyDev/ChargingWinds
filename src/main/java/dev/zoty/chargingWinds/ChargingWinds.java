package dev.zoty.chargingWinds;

import dev.zoty.chargingWinds.listeners.WindChargeListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChargingWinds extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getLogger().info("Hello!!");

        getServer().getPluginManager().registerEvents(new WindChargeListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
