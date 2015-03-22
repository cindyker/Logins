package com.minecats.cindyk;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

/**
 * Created by cindy on 3/21/2015.
 */
public class CommandListener implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player player;
        if(commandSender instanceof  Player) {
            player = (Player)commandSender;

            if (command.getName().equalsIgnoreCase("SetLoginPoint")) {
                if (Logins.permission.playerHas((Player) commandSender, "Logins.setspawn")) {
                    int idx = Logins.config.LocSize();
                    LoginLocs ll = new LoginLocs();

                    ll.x = player.getLocation().getBlockX();
                    ll.y = player.getLocation().getBlockY();
                    ll.z = player.getLocation().getBlockZ();
                    ll.pitch = player.getLocation().getPitch();
                    ll.yaw = player.getLocation().getYaw();
                    ll.world = player.getLocation().getWorld();

                    Logins.plugin.getConfig().set("LoginPoints."+idx+".teleport.x", player.getLocation().getBlockX());
                    Logins.plugin.getConfig().set("LoginPoints."+idx+".teleport.y", player.getLocation().getBlockY());
                    Logins.plugin.getConfig().set("LoginPoints."+idx+".teleport.z", player.getLocation().getBlockZ());
                    Logins.plugin.getConfig().set("LoginPoints."+idx+".teleport.pitch", player.getLocation().getPitch());
                    Logins.plugin.getConfig().set("LoginPoints."+idx+".teleport.yaw", player.getLocation().getYaw());
                    Logins.plugin.getConfig().set("LoginPoints."+idx+".teleport.world", player.getLocation().getWorld().getName());

                    Logins.plugin.saveConfig();
                    Logins.plugin.reloadConfig();

                    Logins.config.AddLocation(ll);

                    player.sendMessage("Saved Login Point: "+ idx);
                }
            }

            if (command.getName().equalsIgnoreCase("RemoveLoginPoint")) {
                if (Logins.permission.playerHas((Player) commandSender, "Logins.removespawn")) {
                    if (args.length != 1) {
                        player.sendMessage("Wrong Arguments: /RemoveLoginPoint [number]");
                        return true;
                    }
                    if(Integer.parseInt(args[0]) > Logins.config.LocSize()){
                        player.sendMessage("You need to pick the index of a spawn that exists. /ListLogins");
                        return true;
                    }


                    if(Logins.plugin.getConfig().get("LoginPoints."+args[0])!=null){

                        Logins.config.locs.remove(Integer.parseInt(args[0]));
                        saveListToConfig();

                        player.sendMessage("You have remove SpawnPoint: " + args[0]);
                    }

                }

            }

            if (command.getName().equalsIgnoreCase("ListLogins")) {
                if (Logins.permission.playerHas((Player) commandSender, "Logins.list")) {
                   int idx = Logins.config.LocSize();
                    Logins.plugin.getLogger().info("ListLogins: Count " + idx);
                  ///  List<Integer> LocIndex = Logins.plugin.getConfig().getIntegerList("LoginPoints");

                    for(int x=0;x<idx;x++){
                        LoginLocs ll = new LoginLocs();
                        ll.x = Logins.plugin.getConfig().getInt("LoginPoints."+x+".teleport.x");
                        ll.y = Logins.plugin.getConfig().getInt("LoginPoints."+x+".teleport.y");
                        ll.z = Logins.plugin.getConfig().getInt("LoginPoints."+x+".teleport.z");
                        ll.pitch = Logins.plugin.getConfig().getInt("LoginPoints."+x+".teleport.pitch");
                        ll.yaw =  Logins.plugin.getConfig().getInt("Loginoints."+x+".teleport.yaw");
                        String sw;
                        sw = Logins.plugin.getConfig().getString("LoginPoints."+x+".teleport.world");

                        player.sendMessage(x+" : " + ll.x + ", " + ll.y + ", " + ll.z + ", " + ll.pitch + ", " + ll.yaw + " in world: " + sw);
                    }
                }
                else{
                    player.sendMessage("You don't have permission for this command.");
                }
            }


        }
        return true;
    }


    void saveListToConfig(){
        LoginLocs ll;

        //Clear the List...
        Logins.plugin.getConfig().set("LoginPoints",null);

        for(int idx=0;idx<Logins.config.LocSize();idx++) {

            ll = Logins.config.locs.get(idx);

            Logins.plugin.getConfig().set("LoginPoints." + idx + ".teleport.x",ll.x);
            Logins.plugin.getConfig().set("LoginPoints." + idx + ".teleport.y", ll.y);
            Logins.plugin.getConfig().set("LoginPoints." + idx + ".teleport.z", ll.z);
            Logins.plugin.getConfig().set("LoginPoints." + idx + ".teleport.pitch", ll.pitch);
            Logins.plugin.getConfig().set("LoginPoints." + idx + ".teleport.yaw", ll.yaw);
            Logins.plugin.getConfig().set("LoginPoints." + idx + ".teleport.world", ll.world.getName());

        }

        Logins.plugin.saveConfig();
        Logins.plugin.reloadConfig();
    }

}
