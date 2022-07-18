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
        if (contains(p)) {
            return;
        }

        playerTracker.add(new StaffType(p, true));
        updateOrAddPrefixes(p);
    }

    public void removePlayer(Player p) {
        StaffType staffType = getStaffType(p);

        if (staffType == null) {
            return;
        }

        playerTracker.remove(staffType);
    }

    public void updateOrAddPrefixes(Player p) {
        for (StaffType s : playerTracker) {
            if (!s.getPlayer().equals(p)) {
                continue;
            }
            UpdatePrefix(s);
            return;
        }

        addPlayer(p);
    }

    public void updatePrefixes(Player p) {
        for (StaffType s : playerTracker) {
            if (!s.getPlayer().equals(p)) {
                continue;
            }
            UpdatePrefix(s);
            return;
        }
    }

    private void UpdatePrefix(StaffType s) {
        s.setDisplayPrefixes(prefixManager.getChatPrefixes(s.getPlayer()));
        s.setPrefixPerTrack(prefixManager.getPrefixPerTrack(s.getPlayer()));
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
        for (StaffType s : playerTracker) {
            if (!s.getPlayer().equals(p)) {
                continue;
            }
            s.toggleDuty();
            return;
        }

        addPlayer(p);
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
}
