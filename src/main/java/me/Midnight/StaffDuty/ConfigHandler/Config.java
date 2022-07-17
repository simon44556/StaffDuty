package me.Midnight.StaffDuty.ConfigHandler;

import me.Midnight.StaffDuty.Main;

public class Config {
    ConfigManager manager;

    public Config(Main plugin) {
        manager = new ConfigManager(plugin);
        this.initiateConfigValues();
        manager.saveConfig();
    }

    private void initiateConfigValues() {
        for(ConfigEnums e : ConfigEnums.values()){
            manager.addKey(e.getKey(), e.getValue());
        }
    }

    public String getNoPermMessage() {
        return manager.getValueForEnum(ConfigEnums.NO_PERM);
    }

    public String getDutyPlaceholder() {
        return manager.getValueForEnum(ConfigEnums.DUTY_PLACEHOLDER);
    }

    public String getDutyPlaceholderBtlp() {
        return manager.getValueForEnum(ConfigEnums.BTLP_PLACEHOLER);
    }

    public String getTogglemessage() {
        return manager.getValueForEnum(ConfigEnums.TOGGLE);
    }

    public String getEmptyPlaceholder() {
        return manager.getValueForEnum(ConfigEnums.EMPTY);
    }

    public String getPrimaryTrack() {
        return manager.getValueForEnum(ConfigEnums.PRIMARY);
    }

    public String getSecondaryTrack() {
        return manager.getValueForEnum(ConfigEnums.SECONDARY);
    }
}

