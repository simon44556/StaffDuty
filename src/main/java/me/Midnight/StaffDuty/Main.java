package me.Midnight.StaffDuty;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.Midnight.StaffDuty.ConfigHandler.Config;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {
    Config configuration;

    @Override
    public void onEnable(){
        configuration = new Config(this);

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


    public Config getConfiguration() {
        return configuration;
    }

}
