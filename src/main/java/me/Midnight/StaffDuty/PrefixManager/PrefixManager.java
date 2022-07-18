package me.Midnight.StaffDuty.PrefixManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import me.Midnight.StaffDuty.ConfigHandler.Config;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedDataManager;
import net.luckperms.api.metastacking.DuplicateRemovalFunction;
import net.luckperms.api.metastacking.MetaStackDefinition;
import net.luckperms.api.metastacking.MetaStackElement;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import net.luckperms.api.track.Track;

public class PrefixManager {
    private final String LUCKPERMS_SORT_ORDER = "highest_on_track_";

    LuckPerms luckPerms;
    Config configHandler;

    public PrefixManager(Config configHandler) {
        this.configHandler = configHandler;
        luckPerms = LuckPermsProvider.get();
    }

    public Map<String, String> getPrefixPerTrack(Player p) {
        Map<String, String> finalMaping = new HashMap<>();

        List<String> allTracks = getAllTracks();

        for (String track : allTracks) {
            finalMaping.put(track, getPrefixForTrack(p, track));
        }

        return finalMaping;
    }

    public String getChatPrefixes(Player p) {
        User user = luckPerms.getUserManager().getUser(p.getUniqueId());

        if (user == null) {
            return "";
        }

        return user.getCachedData().getMetaData().getPrefix();
    }

    private List<String> getAllTracks() {
        Set<Track> loadedTracks = luckPerms.getTrackManager().getLoadedTracks();
        List<String> tracksAsString;

        if (loadedTracks.isEmpty()) {
            return new ArrayList<>();
        }

        tracksAsString = new ArrayList<>();

        for (Track el : loadedTracks) {
            tracksAsString.add(el.getName());
        }

        return tracksAsString;
    }

    private String getPrefixForTrack(Player player, String track) {
        CachedDataManager userdata = luckPerms.getUserManager().getUser(player.getUniqueId()).getCachedData();

        MetaStackElement currentTrackMetaElements = luckPerms.getMetaStackFactory()
                .fromString(LUCKPERMS_SORT_ORDER + track)
                .orElse(null);

        if (currentTrackMetaElements == null) {
            return "";
        }

        return getPrefix(player, currentTrackMetaElements, userdata);
    }

    private String getPrefix(Player user, MetaStackElement metaStackElement, CachedDataManager userdata) {
        QueryOptions queryOptions = luckPerms.getContextManager().getQueryOptions(user);

        MetaStackDefinition stackDefinition = luckPerms.getMetaStackFactory().createDefinition(
                ImmutableList.of(metaStackElement),
                DuplicateRemovalFunction.RETAIN_ALL, "", "", "");

        queryOptions = buildQueryStack(queryOptions, stackDefinition);

        return Strings.nullToEmpty(userdata.getMetaData(queryOptions).getPrefix());
    }

    private QueryOptions buildQueryStack(QueryOptions queryOptions, MetaStackDefinition metaStackElement) {
        return queryOptions.toBuilder()
                .option(MetaStackDefinition.PREFIX_STACK_KEY, metaStackElement)
                .option(MetaStackDefinition.SUFFIX_STACK_KEY, metaStackElement)
                .build();
    }

}
