package me.Midnight.StaffDuty;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    private Main plugin;

    public Commands(Main pl){
        this.plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(sender.hasPermission("toggleduty.staff")){
                plugin.toggleDuty((Player)sender);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.getConfiguration().getTogglemessage() +
                        plugin.getDuty((Player)sender)));
            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfiguration().getNoPermMessage()));
            }
        }
        return true;
    }

}
