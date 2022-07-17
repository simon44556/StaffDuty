package me.Midnight.StaffDuty.ConfigHandler;

public class ConfigStore {
    private String CONFIG_PATH;
    private String VALUE;
    private ConfigEnums TYPE;

    public ConfigStore(String path, String value) {
        CONFIG_PATH = path;
        VALUE = value;

        TYPE = findType(path);
    }

    public ConfigStore(String path, String value, ConfigEnums type) {
        CONFIG_PATH = path;
        VALUE = value;

        TYPE = type;
    }

    private ConfigEnums findType(String path){
        for(ConfigEnums e : ConfigEnums.values()){
            if(e.getKey().equals(path)){
                return e;
            }
        }
        return ConfigEnums.DEFAULT;
    }

    public String getPath() {
        return CONFIG_PATH;
    }
    public String getValue() {
        return VALUE;
    }
    public ConfigEnums getType() {
        return TYPE;
    }
}
