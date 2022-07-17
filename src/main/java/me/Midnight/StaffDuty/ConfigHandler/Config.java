package me.Midnight.StaffDuty.ConfigHandler;

import me.Midnight.StaffDuty.StaffDuty;

public class Config {
    ConfigManager manager;

    public Config(StaffDuty plugin) {
        manager = new ConfigManager(plugin);
        this.initiateConfigValues();
        manager.saveConfig();
    }

    private void initiateConfigValues() {
        for (ConfigEnums e : ConfigEnums.getValues()) {
            manager.addKey(e.getKey(), e.getValue());
        }
    }

    public Object getConfigKey(ConfigEnums e) {
        return manager.getValueForEnum(e);
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

    public String getBtlpRegex() {
        return manager.getValueForEnum(ConfigEnums.BTLP_PREFIX_REGEX);
    }

    public String getChatRegex() {
        return manager.getValueForEnum(ConfigEnums.CHAT_PREFIX_REGEX);
    }
}
