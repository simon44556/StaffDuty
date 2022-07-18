package me.Midnight.StaffDuty.PlayerTracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.bukkit.entity.Player;

public class StaffType {
    Player player;
    boolean isDuty;

    Collection<String> onDutyPrefix;
    Map<String, String> prefixPerTrack;

    StaffType(Player player) {
        this.player = player;
        this.isDuty = true;

        this.onDutyPrefix = new ArrayList<>();
    }

    StaffType(Player player, boolean isDuty) {
        this.player = player;
        this.isDuty = isDuty;

        this.onDutyPrefix = new ArrayList<>();
    }

    public Player getPlayer() {
        return player;
    }

    public boolean getDuty() {
        return isDuty;
    }

    public void toggleDuty() {
        isDuty = !isDuty;
    }

    public void setDuty(boolean isDuty) {
        this.isDuty = isDuty;
    }

    public Collection<String> getPrefixes() {
        return onDutyPrefix;
    }

    public String getPrefix(int idx) {
        String[] prefixes = this.onDutyPrefix.toArray(new String[0]);

        if (idx < 0 || prefixes == null) {
            return "";
        }

        for (String prefix : prefixes) {
            if (--idx < 0) {
                return prefix;
            }
        }

        return "";
    }

    public void setDisplayPrefixes(Collection<String> prefixes) {
        this.onDutyPrefix = prefixes;
    }

    public void setPrefixPerTrack(Map<String, String> prefixPerTrack) {
        this.prefixPerTrack = prefixPerTrack;
    }

    public String getPrefixForTrack(String track) {
        return this.prefixPerTrack.get(track);
    }

    public String getDutyPrefix() {
        return String.join("", onDutyPrefix);
    }
}
