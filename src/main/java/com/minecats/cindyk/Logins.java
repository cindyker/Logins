package com.minecats.cindyk;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * Created by cindy on 3/21/2015.
 * All Rights Reserved.
 */
public class Logins extends JavaPlugin {

    CommandListener cl;
    static public ConfigHandler config;
    static public Logins plugin;
    PlayerEvents playerEvents;
    FileConfiguration fc;

    // Vault --------------------------------
    public static Permission permission = null;

    @Override
    public void onEnable() {
        super.onEnable();

        plugin = this;
        playerEvents = new PlayerEvents();
        cl = new CommandListener();

        PluginManager pm = getServer().getPluginManager();
        getCommand("SetLoginPoint").setExecutor(cl);
        getCommand("ListLogins").setExecutor(cl);
        getCommand("RemoveLoginPoint").setExecutor(cl);

        //Load Config
        if(!loadConfig()){this.getPluginLoader().disablePlugin(this);}

        config.LoadLocations();
        if(config.LocSize()==0)
            getLogger().info("No Spawn Points Defined!");

        //Initialize Vault
        getLogger().info(String.format("[%s] - Checking for Vault...", getDescription().getName()));

        // Set up Vault
        if(!setupPermissions()) {
            getLogger().info(String.format("[%s] - Could not find Vault dependency, disabling.", getDescription().getName()));
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        pm.registerEvents(playerEvents,this);

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private boolean setupPermissions()
    {
        Plugin plug = getServer().getPluginManager().getPlugin("Vault");
        if(plug == null)
            return false;

        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }




    boolean loadConfig()
    {
        // Configuration
        try {

            fc= getConfig();
            fc.options().copyDefaults(true);
            saveConfig();

            config = new ConfigHandler(plugin);
        } catch (Exception ex) {
            getLogger().log(Level.SEVERE,
                    "Could not load configuration!", ex);
            return false;
        }

        return true;
    }

}
