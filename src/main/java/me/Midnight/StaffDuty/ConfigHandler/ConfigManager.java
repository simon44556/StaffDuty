package me.Midnight.StaffDuty.ConfigHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import me.Midnight.StaffDuty.StaffDuty;

public class ConfigManager {
    private FileConfiguration config;

    // TODO: Check if this is needed
    private StaffDuty _plugin;

    List<ConfigStore> configKeys;

    public ConfigManager(StaffDuty plugin) {
        _plugin = plugin;
        config = plugin.getConfig();
        configKeys = new ArrayList<ConfigStore>();
    }

    public void addKey(ConfigStore key) {
        configKeys.add(generateConfigKey(key.getPath(), key.getValue(), key.getType()));
    }

    public void addKey(String configPath, String value) {
        configKeys.add(generateConfigKey(configPath, value));
    }

    public void addKey(ConfigEnums e) {
        configKeys.add(generateConfigKey(e.getKey(), e.getValue(), e));
    }

    public String getValueForEnum(ConfigEnums e) {
        for (ConfigStore val : configKeys) {
            if (val.getType() == e) {
                return val.getValue();
            }
        }
        return null;
    }

    public String getValueAtPath(String path) {
        for (ConfigStore val : configKeys) {
            if (val.getPath().equals(path)) {
                return val.getValue();
            }
        }
        return null;
    }

    public ConfigStore generateConfigKey(String path, String value) {
        final String val = configGetVal(path);
        if (val == null) {
            return new ConfigStore(path, value);
        }
        return new ConfigStore(path, val);
    }

    public ConfigStore generateConfigKey(String path, String value, ConfigEnums type) {
        final String val = configGetVal(path);
        if (val == null) {
            return new ConfigStore(path, value, type);
        }
        return new ConfigStore(path, val, type);
    }

    public String configGetVal(String path) {
        if (config.getString(path) != null) {
            return config.getString(path);
        }
        return null;
    }

    public void saveConfig() {
        config.addDefaults(toMap(configKeys));
        config.options().copyDefaults(true);
        _plugin.saveConfig();
    }

    private Map<String, Object> toMap(List<ConfigStore> values) {
        final Map<String, Object> retunValue = new HashMap<>();

        for (ConfigStore obj : values) {
            retunValue.put(obj.getPath(), obj.getValue());
        }
        return retunValue;
    }
}
