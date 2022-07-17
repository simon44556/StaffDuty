package me.Midnight.StaffDuty.EventManager;

import me.Midnight.StaffDuty.StaffDuty;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.log.LogReceiveEvent;
import net.luckperms.api.event.node.NodeMutateEvent;

public class EventManager {
    private JoinListeneer joinListeneer;
    private LuckpermsEvents luckpermsEvents;

    public EventManager(StaffDuty plugin) {
        joinListeneer = new JoinListeneer(plugin.getTrackManager());
        luckpermsEvents = new LuckpermsEvents(plugin.getTrackManager());

        registerBukkitEvents(plugin);
        subscribeToLuckpermsEvents(plugin);
    }

    private void registerBukkitEvents(StaffDuty plugin) {
        plugin.getServer().getPluginManager().registerEvents(joinListeneer, plugin);
    }

    private void subscribeToLuckpermsEvents(StaffDuty plugin) {
        EventBus eventBus = LuckPermsProvider.get().getEventBus();
        eventBus.subscribe(plugin, NodeMutateEvent.class, luckpermsEvents::rankChange);
        eventBus.subscribe(plugin, LogReceiveEvent.class, luckpermsEvents::syncEvent);
    }
}
