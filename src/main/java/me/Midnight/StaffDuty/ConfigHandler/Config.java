package me.Midnight.StaffDuty.ConfigHandler;

import me.Midnight.StaffDuty.StaffDuty;

public class Config {
    ConfigManager manager;

    public Config(StaffDuty plugin) {
        manager = new ConfigManager(plugin);
        this.initiateConfigValues();
        manager.saveConfig();
        plugin.saveConfig();
    }

    private void initiateConfigValues() {
        for (ConfigEnums e : ConfigEnums.getValues()) {
            manager.addKey(e.getKey(), e.getValue());
        }
    }

    public String getConfigKey(ConfigEnums e) {
        return manager.getValueForEnum(e);
    }
}
