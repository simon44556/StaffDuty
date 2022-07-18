package me.Midnight.StaffDuty.EventManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Midnight.StaffDuty.PlayerTracker.TrackManager;

public class LeaveListener implements Listener {
    TrackManager trackManager;

    public LeaveListener(TrackManager trackManager) {
        this.trackManager = trackManager;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        trackManager.removePlayer(e.getPlayer());
    }
}
