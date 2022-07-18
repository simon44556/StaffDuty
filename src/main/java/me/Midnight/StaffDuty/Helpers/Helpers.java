package me.Midnight.StaffDuty.Helpers;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public final class Helpers {

    private Helpers() {
    }

    public static void SendPlayerMessage(Player p, String m) {
        if (p == null || m == null) {
            return;
        }
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', m));
    }

    public static void SendPlayerMessage(CommandSender p, String m) {
        if (p == null || m == null) {
            return;
        }
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', m));
    }

    public static void SendConsoleMessage(String m) {
        if (m == null) {
            return;
        }
        Bukkit.getLogger().log(Level.INFO, m);
    }
}
