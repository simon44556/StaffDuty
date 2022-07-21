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
            trackManager.updateOrAddPrefixes(p);
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

        trackManager.updateOrAddPrefixes(player);
    }
}
