package me.Midnight.StaffDuty;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListeneer implements Listener {
    private Main plugin;

    public JoinListeneer(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(e.getPlayer().hasPermission("toggleduty.staff") || e.getPlayer().isOp()){
            plugin.addPlayer(e.getPlayer());
        }
    }

}
