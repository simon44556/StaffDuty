package me.Midnight.StaffDuty.ConfigHandler;

import me.Midnight.StaffDuty.Main;

public class Config {
    ConfigManager manager;

    public Config(Main plugin) {
        manager = new ConfigManager(plugin);

        for(ConfigEnums e : ConfigEnums.values()){
            manager.addKey(e.getKey(), e.getValue());
        }

        manager.saveConfig();
    }

    public String getNoPermMessage() {
        return manager.getValueAtPath(ConfigEnums.NO_PERM.getKey());
    }

    public String getDutyPlaceholder() {
        return manager.getValueAtPath(ConfigEnums.DUTY_PLACEHOLDER.getKey());
    }

    public String getDutyPlaceholderBtlp() {
        return manager.getValueAtPath(ConfigEnums.BTLP_PLACEHOLER.getKey());
    }

    public String getTogglemessage() {
        return manager.getValueAtPath(ConfigEnums.TOGGLE.getKey());
    }

    public String getEmptyPlaceholder() {
        return manager.getValueAtPath(ConfigEnums.EMPTY.getKey());
    }

    public String getPrimaryTrack() {
        return manager.getValueAtPath(ConfigEnums.PRIMARY.getKey());
    }

    public String getSecondaryTrack() {
        return manager.getValueAtPath(ConfigEnums.SECONDARY.getKey());
    }
}

