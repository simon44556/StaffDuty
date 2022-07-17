package me.Midnight.StaffDuty.ConfigHandler;

enum ConfigEnums {
    DEFAULT("", ""),
    TOGGLE("ToggleMessage", "&aYour duty was toggled to "),
    DUTY_PLACEHOLDER("DutyPlaceholder", "player_rank_prefix"),
    BTLP_PLACEHOLER("DutyPlaceholderBtlp", "player_rank_prefix_btlp"),
    NO_PERM("Noperm", "&4No permission to do this"),
    EMPTY("EmptyPlaceholder", "&7"),
    PRIMARY("PrimaryTrack", "player"),
    SECONDARY("SecondaryTrack", "");

    private final String value;
    private final String key;

    ConfigEnums(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return value;
    }

    public static ConfigEnums[] getValues() {
        ConfigEnums[] returnValues = new ConfigEnums[ConfigEnums.values().length - 1];

        int idx = 0;

        for (ConfigEnums e : ConfigEnums.values()) {
            if (e == ConfigEnums.DEFAULT) {
                continue;
            }
            returnValues[idx++] = e;
        }

        return returnValues;
    }
}