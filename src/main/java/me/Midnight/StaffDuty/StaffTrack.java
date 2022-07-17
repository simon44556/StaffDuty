package me.Midnight.StaffDuty;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedDataManager;
import net.luckperms.api.metastacking.DuplicateRemovalFunction;
import net.luckperms.api.metastacking.MetaStackDefinition;
import net.luckperms.api.metastacking.MetaStackElement;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.entity.Player;

import java.util.*;

public class StaffTrack {
    Player player;
    boolean duty;
    Main plugin;

    LuckPerms luckPerms;

    List<String> prefixes;

    public StaffTrack(Main plugin){
        this.plugin = plugin;
    }

    public StaffTrack(Player player, boolean duty,Main plugin){
        this.plugin = plugin;

        this.player = player;
        this.duty = duty;

        this.prefixes = new ArrayList<String>();

        luckPerms = LuckPermsProvider.get();

        updatePrefixes();
    }

    public String getPrefixesLuck(User user, QueryOptions queryOptions){
        String prefix = "";

        CachedDataManager userdata = user.getCachedData();

        MetaStackElement primary = luckPerms.getMetaStackFactory().fromString("highest_on_track_" + plugin.getConfiguration().getPrimaryTrack()).orElse(null);
        MetaStackElement secondary = luckPerms.getMetaStackFactory().fromString("highest_on_track_" + plugin.getConfiguration().getSecondaryTrack()).orElse(null);

        if (primary != null) {
            prefix = getPrefix(queryOptions, primary, userdata);
        }
        if(secondary != null && prefix.equals("")){
            prefix = getPrefix(queryOptions, primary, userdata);
        }

        return prefix;
    }

    private String getPrefix(QueryOptions queryOptions, MetaStackElement m, CachedDataManager userdata) {
        MetaStackDefinition stackDefinition = luckPerms.getMetaStackFactory().createDefinition(ImmutableList.of(m), DuplicateRemovalFunction.RETAIN_ALL, "", "", "");
        queryOptions = buildQueryStack(queryOptions, stackDefinition);
        return Strings.nullToEmpty(userdata.getMetaData(queryOptions).getPrefix());
    }

    private QueryOptions buildQueryStack(QueryOptions q, MetaStackDefinition s) {
        return q.toBuilder()
        .option(MetaStackDefinition.PREFIX_STACK_KEY, s)
        .option(MetaStackDefinition.SUFFIX_STACK_KEY, s)
        .build();
    }

    public List<String> getPrefixes() {
        return prefixes;
    }

    public void updatePrefixes() {

        QueryOptions queryOptions = luckPerms.getContextManager().getQueryOptions(player);

        User user = luckPerms.getUserManager().getUser(player.getUniqueId());

        if(user == null)
            return;

        Collection<String> tmp = user.getCachedData().getMetaData().getPrefixes().values();

        if(tmp.isEmpty()){
            return;
        }

        this.prefixes = new ArrayList<String>();
        prefixes.add((String) tmp.toArray()[0]);

        prefixes.add(getPrefixesLuck(user, queryOptions));
    }

    public void setDuty(boolean duty) {
        this.duty = duty;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isDuty() {
        return duty;
    }
}
