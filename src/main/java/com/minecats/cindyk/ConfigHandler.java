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
    static int NextYardPointIndex;
    List<LoginLocs> locs;
    List<LoginLocs> yard;

    public ConfigHandler(Logins plugin) {
        this.plugin = plugin;
        locs = new ArrayList<LoginLocs>();
        yard = new ArrayList<LoginLocs>();
        NextPointIndex = 0;
        NextYardPointIndex=0;
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

        cs = plugin.getConfig().getConfigurationSection("YardPoints");

        for(String ls: cs.getKeys(false))
        {
            plugin.getLogger().info("Showing KEYS!! " + ls);
            LoginLocs ll = new LoginLocs();
            ll.x = plugin.getConfig().getInt("YardPoints."+ls+".teleport.x");
            ll.y = plugin.getConfig().getInt("YardPoints."+ls+".teleport.y");
            ll.z = plugin.getConfig().getInt("YardPoints."+ls+".teleport.z");
            ll.pitch = plugin.getConfig().getInt("YardPoints."+ls+".teleport.pitch");
            ll.yaw =  plugin.getConfig().getInt("YardPoints."+ls+".teleport.yaw");
            String sw;
            sw = plugin.getConfig().getString("YardPoints."+ls+".teleport.world");
            ll.world = plugin.getServer().getWorld(sw);

            AddYardLocation(ll);
        }




    }

    public void AddLocation(LoginLocs newLoc){

        locs.add(newLoc);
    }

    public void AddYardLocation(LoginLocs newLoc){

        yard.add(newLoc);
    }

    public int getNextPointIndex(){
        if(NextPointIndex+1>locs.size()-1)
            NextPointIndex=0;

        return NextPointIndex++;
    }

    public int getNextYardPointIndex(){
        if(NextYardPointIndex+1>yard.size()-1)
            NextYardPointIndex=0;

        return NextYardPointIndex++;
    }

    public int LocSize(){
        return locs.size();
    }
    public int YardSize(){return yard.size();}

    public Location getJoinLocation(int index) {
        return locs.get(index).getJoinLocation();
    }
    public Location getYardLocation(int index) {
        return yard.get(index).getJoinLocation();
    }

}
