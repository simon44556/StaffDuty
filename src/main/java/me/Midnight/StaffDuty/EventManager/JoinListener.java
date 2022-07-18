package me.Midnight.StaffDuty.EventManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Midnight.StaffDuty.PlayerTracker.TrackManager;

public class JoinListener implements Listener {
    TrackManager trackManager;

    public JoinListener(TrackManager trackManager) {
        this.trackManager = trackManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        // Track all
        trackManager.addPlayer(e.getPlayer());
    }

}
