package me.Midnight.StaffDuty;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.actionlog.Action;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.log.LogReceiveEvent;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class LuckpermsEvents {
    private Main plugin;
    LuckPerms luckPerms;

    public LuckpermsEvents(Main plugin){
        this.plugin = plugin;

        luckPerms = LuckPermsProvider.get();

        EventBus eventBus = luckPerms.getEventBus();

        // 3. Subscribe to an event using a method reference
        eventBus.subscribe(this.plugin, NodeMutateEvent.class, this::rankChange);
        eventBus.subscribe(this.plugin, LogReceiveEvent.class, this::syncEvent);
    }

    private void syncEvent(LogReceiveEvent e){
        if(e.getEntry().getTarget().getType() == Action.Target.Type.USER){
            Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
            for(Player p : onlinePlayers){
                System.out.println("user " + p.getName());
                User user = luckPerms.getUserManager().getUser(p.getUniqueId());
                if(user != null && p.hasPermission("toggleduty.staff")){
                    System.out.println("Sync update / add");
                    plugin.updateOrAddPrefixes(Bukkit.getPlayer(user.getUniqueId()));
                }else if(user != null){
                    System.out.println("Sync update");
                    plugin.updatePrefixes(Bukkit.getPlayer(user.getUniqueId()));
                }
            }
        }
    }

    private void rankChange(NodeMutateEvent e){
        if(e.isUser()){
            User user = (User)e.getTarget();
            Player player = Bukkit.getPlayer(user.getUniqueId());

            if(player == null )
                return;

            if(player.isOnline() && user.getCachedData().getPermissionData().checkPermission("toggleduty.staff").asBoolean() || player.isOp()){
                plugin.updateOrAddPrefixes(player);
            }
            else if(user != null && player != null && player.isOnline()){
                plugin.updatePrefixes(player);
            }
        }
    }
}
