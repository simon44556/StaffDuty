package me.Midnight.StaffDuty;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.Midnight.StaffDuty.ConfigHandler.Config;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {
    List<StaffTrack> dutyTracker;
    Config configuration;

    @Override
    public void onEnable(){
        configuration = new Config(this);

        dutyTracker = new ArrayList<StaffTrack>();

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new PlaceHolder(this).register();
        }

        new LuckpermsEvents(this);

        getServer().getPluginManager().registerEvents(new JoinListeneer(this), this);

        this.getCommand("toggleduty").setExecutor(new Commands(this));
    }
    @Override
    public void onDisable(){

    }

    public void addPlayer(Player p){
        if(dutyTracker == null){
            return;
        }
        if(!contains(p)){
            dutyTracker.add(new StaffTrack(p, true, this));
        }
    }

    public Config getConfiguration() {
        return configuration;
    }

    public void updateOrAddPrefixes(Player p){
        boolean updated = false;
        if(dutyTracker == null){
            return;
        }
        for(StaffTrack s: dutyTracker){
            if(s.getPlayer().equals(p)){
                s.updatePrefixes();
                updated = true;
            }
        }
        if(!updated){
            if(!contains(p)){
                dutyTracker.add(new StaffTrack(p, true, this));
            }
        }
    }

    public void updatePrefixes(Player p){
        if(dutyTracker == null){
            return;
        }
        for(StaffTrack s: dutyTracker){
            if(s.getPlayer().equals(p)){
                s.updatePrefixes();
            }
        }
    }

    public boolean getDuty(Player p){
        if(dutyTracker == null){
            return false;
        }
        
        for(StaffTrack s: dutyTracker){
            if(s.getPlayer().equals(p)){
                return s.duty;
            }
        }
        return false;
    }

    public void toggleDuty(Player p){
        boolean added = false;
        if(dutyTracker == null){
            return;
        }
        for(StaffTrack s: dutyTracker){
            if(s.getPlayer().equals(p)){
                s.duty = !s.duty;
                added = true;
                break;
            }
        }
        if(!added){
            addPlayer(p);
        }
    }

    private boolean contains(Player p){
        if(dutyTracker == null){
            return false;
        }
        for(StaffTrack s: dutyTracker){
            if(s.getPlayer().equals(p)){
                return true;
            }
        }
        return false;
    }

    public StaffTrack getStaffTrack(Player p){
        if(dutyTracker == null){
            return null;
        }
        for(StaffTrack s: dutyTracker){
            if(s.getPlayer().equals(p)){
                return s;
            }
        }
        return null;
    }
}
