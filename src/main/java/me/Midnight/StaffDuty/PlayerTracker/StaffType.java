package me.Midnight.StaffDuty.PlayerTracker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class StaffType {
    Player player;
    boolean isDuty;

    List<String> prefixes;

    StaffType(Player player) {
        this.player = player;
        this.isDuty = false;

        this.prefixes = new ArrayList<>();
    }

    StaffType(Player player, boolean isDuty) {
        this.player = player;
        this.isDuty = isDuty;

        this.prefixes = new ArrayList<>();
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

    public List<String> getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(List<String> prefixes) {
        this.prefixes = prefixes;
    }
}
