package me.Midnight.StaffDuty.ConfigHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import me.Midnight.StaffDuty.Main;

public class ConfigManager {
    private FileConfiguration config;
    private Main _plugin;

    List<ConfigStore> configKeys;

    public ConfigManager(Main plugin) {
        _plugin = plugin;
        config = plugin.getConfig();
        configKeys = new ArrayList<ConfigStore>();
    }

    public void addKey(ConfigStore key) {
        configKeys.add(key);
    }

    public void addKey(String configPath, String value) {
        configKeys.add(new ConfigStore(configPath, value));
    }

    public String getValueAtPath(String path){
        for(ConfigStore val : configKeys) {
            if(val.getPath().equals(path)) {
                return val.getValue();
            }
        }
        return null;
    }

    public ConfigStore generateConfigKey(String path, String value){
        String val = configGetVal(path);
        if(val == null){
            return new ConfigStore(path, value);
        }
        return new ConfigStore(path, val);
    }

    public String configGetVal(String path) {
        if(config.getString(path) != null) {
            return config.getString(path);
        }
        return null;
    }

    public void saveConfig(){
        config.addDefaults(toMap(configKeys));
        config.options().copyDefaults(true);
        _plugin.saveConfig();
    }

    private Map<String, Object> toMap(List<ConfigStore> values){
        Map<String, Object> retunValue = new HashMap<String, Object>();

        for(ConfigStore obj : values){
            retunValue.put(obj.getPath(), obj.getValue());
        }
        return retunValue;
    }
}
