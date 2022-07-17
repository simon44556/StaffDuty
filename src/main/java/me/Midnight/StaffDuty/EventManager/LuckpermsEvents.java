package me.Midnight.StaffDuty.EventManager;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Midnight.StaffDuty.PlayerTracker.TrackManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.actionlog.Action;
import net.luckperms.api.event.log.LogReceiveEvent;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.model.user.User;

public class LuckpermsEvents {
    /*
     * TODO:
     * - Log manager
     * - Change hardcoded strings
     */
    LuckPerms luckPerms;
    TrackManager trackManager;

    public LuckpermsEvents(TrackManager trackManager) {
        this.trackManager = trackManager;
        luckPerms = LuckPermsProvider.get();
    }

    public void syncEvent(LogReceiveEvent e) {
        if (e.getEntry().getTarget().getType() == Action.Target.Type.USER) {
            Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
            for (Player p : onlinePlayers) {
                System.out.println("user " + p.getName());
                User user = luckPerms.getUserManager().getUser(p.getUniqueId());
                if (user != null && p.hasPermission("toggleduty.staff")) {
                    System.out.println("Sync update / add");
                    trackManager.updateOrAddPrefixes(Bukkit.getPlayer(user.getUniqueId()));
                } else if (user != null) {
                    System.out.println("Sync update");
                    trackManager.updatePrefixes(Bukkit.getPlayer(user.getUniqueId()));
                }
            }
        }
    }

    public void rankChange(NodeMutateEvent e) {
        if (e.isUser()) {
            User user = (User) e.getTarget();
            Player player = Bukkit.getPlayer(user.getUniqueId());

            if (player == null) {
                return;
            }

            if (player.isOnline()
                    && user.getCachedData().getPermissionData().checkPermission("toggleduty.staff").asBoolean()
                    || player.isOp()) {
                trackManager.updateOrAddPrefixes(player);
            }

            else if (user != null && player != null && player.isOnline()) {
                trackManager.updatePrefixes(player);
            }
        }
    }
}
