package me.Midnight.StaffDuty.PlaceHolderApiManager;

import org.bukkit.entity.Player;

import me.Midnight.StaffDuty.ConfigHandler.Config;
import me.Midnight.StaffDuty.PlayerTracker.StaffType;
import me.Midnight.StaffDuty.PlayerTracker.TrackManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceHolderApiConnect extends PlaceholderExpansion {
    Config configHandler;
    TrackManager trackManager;

    /**
     * Since we register the expansion inside our own plugin, we
     * can simply use this method here to get an instance of our
     * plugin.
     *
     * @param plugin
     *               The instance of our plugin.
     */
    public PlaceHolderApiConnect(Config configHandler, TrackManager trackManager) {
        this.configHandler = configHandler;
        this.trackManager = trackManager;
    }

    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister
     * your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist() {
        return true;
    }

    /**
     * Because this is a internal class, this check is not needed
     * and we can simply return {@code true}
     *
     * @return Always true since it's an internal class.
     */
    @Override
    public boolean canRegister() {
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     * <br>
     * For convienience do we return the author from the plugin.yml
     *
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor() {
        return "";
    }

    /**
     * The placeholder identifier should go here.
     * <br>
     * This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>
     * The identifier has to be lowercase and can't contain _ or %
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier() {
        return "staffduty";
    }

    /**
     * This is the version of the expansion.
     * <br>
     * You don't have to use numbers, since it is set as a String.
     *
     * For convienience do we return the version from the plugin.yml
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion() {
        return "1.1";
    }

    /**
     * This is the method called when a placeholder with our identifier
     * is found and needs a value.
     * <br>
     * We specify the value identifier in this method.
     * <br>
     * Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param player
     *                   A {@link org.bukkit.entity.Player Player}.
     * @param identifier
     *                   A String containing the identifier/value.
     *
     * @return possibly-null String of the requested identifier.
     */
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return configHandler.getEmptyPlaceholder();
        }

        try {
            StaffType type = trackManager.getStaffType(player);

            if (type == null) {
                return configHandler.getEmptyPlaceholder();
            }

            String firstPrefix = type.getPrefix(0);
            String secondPrefix = type.getPrefix(1);

            boolean isDuty = trackManager.getDuty(player);

            if (identifier.equals(configHandler.getDutyPlaceholder())) {
                if (isDuty) {
                    return parsePrefix(firstPrefix);
                } else {
                    return parsePrefix(secondPrefix);
                }
            }

            if (identifier.equals(configHandler.getDutyPlaceholderBtlp())) {
                if (isDuty) {
                    return firstPrefix.replace("#", "&#");
                } else {
                    return configHandler.getEmptyPlaceholder();
                }
            }

        } catch (Exception ex) {
            System.out.println("Exception in StaffDuty");
            ex.printStackTrace();
        }

        return "Error in parsing the placeholer";
    }

    private String parsePrefix(String s) {
        String removeHexFormat = "/(?:#)[0-9a-f]{8}|(?:#)[0-9a-f]{6}|(?:#)[0-9a-f]{4}|(?:#)[0-9a-f]{3}/ig";
        return s.replaceAll(removeHexFormat, "");
    }

}
