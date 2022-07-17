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
        if (e.getEntry().getTarget().getType() != Action.Target.Type.USER) {
            return;
        }

        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

        for (Player p : onlinePlayers) {
            User user = luckPerms.getUserManager().getUser(p.getUniqueId());

            if (user == null) {
                continue;
            }

            if (p.hasPermission("toggleduty.staff") || p.isOp()) {
                trackManager.updateOrAddPrefixes(Bukkit.getPlayer(user.getUniqueId()));
            } else {
                trackManager.updatePrefixes(Bukkit.getPlayer(user.getUniqueId()));
            }
        }
    }

    public void rankChange(NodeMutateEvent e) {
        if (!e.isUser()) {
            return;
        }

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

        else if (player.isOnline()) {
            trackManager.updatePrefixes(player);
        }
    }
}
