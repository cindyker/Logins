package com.minecats.cindyk;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cindy on 3/21/2015.
 */
public class ConfigHandler {

    Logins plugin;
    String LoginPoints  = "LoginPoints";
    static int NextPointIndex;
    List<LoginLocs> locs;

    public ConfigHandler(Logins plugin) {
        this.plugin = plugin;
       locs = new ArrayList<LoginLocs>();
        NextPointIndex = 0;
    }


    public void LoadLocations(){

        ConfigurationSection cs = plugin.getConfig().getConfigurationSection("LoginPoints");

        for(String ls: cs.getKeys(false))
        {
            plugin.getLogger().info("Showing KEYS!! " + ls);
            LoginLocs ll = new LoginLocs();
            ll.x = plugin.getConfig().getInt("LoginPoints."+ls+".teleport.x");
            ll.y = plugin.getConfig().getInt("LoginPoints."+ls+".teleport.y");
            ll.z = plugin.getConfig().getInt("LoginPoints."+ls+".teleport.z");
            ll.pitch = plugin.getConfig().getInt("LoginPoints."+ls+".teleport.pitch");
            ll.yaw =  plugin.getConfig().getInt("LoginPoints."+ls+".teleport.yaw");
            String sw;
            sw = plugin.getConfig().getString("LoginPoints."+ls+".teleport.world");
            ll.world = plugin.getServer().getWorld(sw);

            AddLocation(ll);
        }


    }

    public void AddLocation(LoginLocs newLoc){

        locs.add(newLoc);
    }

    public int getNextPointIndex(){
        if(NextPointIndex+1>locs.size()-1)
            NextPointIndex=0;

        return NextPointIndex++;
    }

    public int LocSize(){
        return locs.size();
    }

    public Location getJoinLocation(int index) {
        return locs.get(index).getJoinLocation();
    }


}
