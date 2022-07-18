package me.Midnight.StaffDuty;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.Midnight.StaffDuty.CommandManager.CommandManager;
import me.Midnight.StaffDuty.ConfigHandler.Config;
import me.Midnight.StaffDuty.EventManager.EventManager;
import me.Midnight.StaffDuty.PlaceHolderApiManager.PlaceHolderApiConnect;
import me.Midnight.StaffDuty.PlayerTracker.TrackManager;

public class StaffDuty extends JavaPlugin {
    Config configHandler;
    TrackManager trackManager;
    CommandManager commandManager;
    PlaceHolderApiConnect placeHolderApiConnect;
    EventManager eventManager;

    /*
     * TODO:
     * - Add permission to config
     * - Add alis to config
     * - Add multiple tracks
     * - Make placeholders dynamic
     * - Improve regex filter
     */

    @Override
    public void onEnable() {
        configHandler = new Config(this);
        trackManager = new TrackManager(configHandler);
        commandManager = new CommandManager(this);
        eventManager = new EventManager(this);

        initPlaceHolerApi();
    }

    @Override
    public void onDisable() {
        configHandler = null;
        trackManager = null;
        commandManager = null;
        placeHolderApiConnect = null;
    }

    public Config getConfiguration() {
        return configHandler;
    }

    public TrackManager getTrackManager() {
        return trackManager;
    }

    private void initPlaceHolerApi() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return;
        }

        placeHolderApiConnect = new PlaceHolderApiConnect(configHandler, trackManager);

        if (!placeHolderApiConnect.canRegister()) {
            return;
        }

        placeHolderApiConnect.register();
    }
}
