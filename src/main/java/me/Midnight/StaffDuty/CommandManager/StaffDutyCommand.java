package me.Midnight.StaffDuty.CommandManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Midnight.StaffDuty.ConfigHandler.Config;
import me.Midnight.StaffDuty.PlayerTracker.TrackManager;

public class StaffDutyCommand implements CommandExecutor {
    private TrackManager trackManager;
    private Config configHandler;

    public StaffDutyCommand(TrackManager trackManager, Config configHandler) {
        this.trackManager = trackManager;
        this.configHandler = configHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("toggleduty.staff")) {
                trackManager.toggleDuty((Player) sender);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        configHandler.getTogglemessage() + trackManager.getDuty((Player) sender)));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', configHandler.getNoPermMessage()));
            }
        }
        return true;
    }

}
