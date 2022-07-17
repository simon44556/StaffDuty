package me.Midnight.StaffDuty.ConfigHandler;

public class ConfigStore {
    private String CONFIG_PATH;
    private String VALUE;

    public ConfigStore(String path, String value) {
        CONFIG_PATH = path;
        VALUE = value;
    }

    public String getPath() {
        return CONFIG_PATH;
    }
    public String getValue() {
        return VALUE;
    }
}
