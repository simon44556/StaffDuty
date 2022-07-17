package me.Midnight.StaffDuty.CommandManager;

import me.Midnight.StaffDuty.StaffDuty;

public class CommandManager {
    StaffDutyCommand staffDuty;

    public CommandManager(StaffDuty plugin) {
        staffDuty = new StaffDutyCommand(plugin.getTrackManager(), plugin.getConfiguration());

        RegisterCommands(plugin);
    }

    public void RegisterCommands(StaffDuty plugin) {
        plugin.getCommand("toggleduty").setExecutor(staffDuty);
    }
}