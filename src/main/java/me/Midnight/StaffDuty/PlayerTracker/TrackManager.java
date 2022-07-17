package me.Midnight.StaffDuty.PlayerTracker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.Midnight.StaffDuty.ConfigHandler.Config;
import me.Midnight.StaffDuty.PrefixManager.PrefixManager;

public class TrackManager {
    List<StaffType> playerTracker;
    PrefixManager prefixManager;

    public TrackManager(Config configHandler) {
        initPlayerTrack();
        prefixManager = new PrefixManager(configHandler);
    }

    private void initPlayerTrack() {
        playerTracker = new ArrayList<>();
    }

    public void addPlayer(Player p) {
        if (!contains(p)) {
            playerTracker.add(new StaffType(p, true));
            updateOrAddPrefixes(p);
        }
    }

    public void updateOrAddPrefixes(Player p) {
        boolean updated = false;

        for (StaffType s : playerTracker) {
            if (s.getPlayer().equals(p)) {
                UpdatePrefix(s);
                updated = true;
            }
        }
        if (!updated) {
            if (!contains(p)) {
                playerTracker.add(new StaffType(p, true));
            }
        }
    }

    public void updatePrefixes(Player p) {
        for (StaffType s : playerTracker) {
            if (s.getPlayer().equals(p)) {
                UpdatePrefix(s);
            }
        }
    }

    public boolean getDuty(Player p) {
        for (StaffType s : playerTracker) {
            if (s.getPlayer().equals(p)) {
                return s.getDuty();
            }
        }
        return false;
    }

    public void toggleDuty(Player p) {
        boolean added = false;

        for (StaffType s : playerTracker) {
            if (s.getPlayer().equals(p)) {
                s.toggleDuty();
                added = true;
                break;
            }
        }
        if (!added) {
            addPlayer(p);
        }
    }

    private boolean contains(Player p) {
        for (StaffType s : playerTracker) {
            if (s.getPlayer().equals(p)) {
                return true;
            }
        }
        return false;
    }

    public StaffType getStaffType(Player p) {
        for (StaffType s : playerTracker) {
            if (s.getPlayer().equals(p)) {
                return s;
            }
        }
        return null;
    }

    private void UpdatePrefix(StaffType s) {
        List<String> prefixes = prefixManager.updatePrefixes(s.getPlayer());
        s.setPrefixes(prefixes);
    }
}
