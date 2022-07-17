package me.Midnight.StaffDuty.PrefixManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

public class PrefixManager {
    private final String LUCKPERMS_SORT_ORDER = "highest_on_track_";

    LuckPerms luckPerms;
    Config configHandler;

    public PrefixManager(Config configHandler) {
        this.configHandler = configHandler;
        luckPerms = LuckPermsProvider.get();
    }

    public String getPrefixesLuck(User user, QueryOptions queryOptions) {
        String prefix = "";

        CachedDataManager userdata = user.getCachedData();

        MetaStackElement primary = luckPerms.getMetaStackFactory()
                .fromString(LUCKPERMS_SORT_ORDER + configHandler.getPrimaryTrack()).orElse(null);
        MetaStackElement secondary = luckPerms.getMetaStackFactory()
                .fromString(LUCKPERMS_SORT_ORDER + configHandler.getSecondaryTrack()).orElse(null);

        if (primary != null) {
            prefix = getPrefix(queryOptions, primary, userdata);
        }
        if (secondary != null && prefix.equals("")) {
            prefix = getPrefix(queryOptions, primary, userdata);
        }

        return prefix;
    }

    private String getPrefix(QueryOptions queryOptions, MetaStackElement m, CachedDataManager userdata) {
        MetaStackDefinition stackDefinition = luckPerms.getMetaStackFactory().createDefinition(ImmutableList.of(m),
                DuplicateRemovalFunction.RETAIN_ALL, "", "", "");
        queryOptions = buildQueryStack(queryOptions, stackDefinition);
        return Strings.nullToEmpty(userdata.getMetaData(queryOptions).getPrefix());
    }

    private QueryOptions buildQueryStack(QueryOptions q, MetaStackDefinition s) {
        return q.toBuilder()
                .option(MetaStackDefinition.PREFIX_STACK_KEY, s)
                .option(MetaStackDefinition.SUFFIX_STACK_KEY, s)
                .build();
    }

    public List<String> updatePrefixes(Player p) {

        QueryOptions queryOptions = luckPerms.getContextManager().getQueryOptions(p);

        User user = luckPerms.getUserManager().getUser(p.getUniqueId());

        if (user == null)
            return new ArrayList<>();

        Collection<String> tmp = user.getCachedData().getMetaData().getPrefixes().values();

        if (tmp.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> prefixes = new ArrayList<>();
        prefixes.add((String) tmp.toArray()[0]);

        prefixes.add(getPrefixesLuck(user, queryOptions));
        return prefixes;
    }
}
