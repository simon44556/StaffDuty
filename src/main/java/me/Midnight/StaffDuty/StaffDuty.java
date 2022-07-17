package me.Midnight.StaffDuty;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.Midnight.StaffDuty.ConfigHandler.Config;
import me.Midnight.StaffDuty.PlaceHolderApiManager.PlaceHolder;
import me.Midnight.StaffDuty.PlayerTracker.TrackManager;

public class StaffDuty extends JavaPlugin {
    Config configHandler;
    TrackManager trackManager;

    @Override
    public void onEnable() {
        configHandler = new Config(this);
        trackManager = new TrackManager(configHandler);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceHolder(configHandler, trackManager).register();
        }
    }

    @Override
    public void onDisable() {
        configHandler = null;
        trackManager = null;
    }

    public Config getConfiguration() {
        return configHandler;
    }

    public TrackManager getTrackManager() {
        return trackManager;
    }
}
