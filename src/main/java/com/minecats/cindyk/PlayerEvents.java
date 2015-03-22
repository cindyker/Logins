package com.minecats.cindyk;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by cindy on 3/21/2015.
 */
public class PlayerEvents implements Listener {

    @EventHandler
    void onPlayerLogin(PlayerJoinEvent event){

        int SecondsToWait = 1;


        if(Logins.config.LocSize()>0) {
            final Player player = event.getPlayer();

            if(!player.hasPlayedBefore()) return;  //don't move new players.

           // int expire = Logins.plugin.getConfig().getInt("on-first-join.modify-damage.disable-pvp.expire-after");
            //Move Player to a login point... in a task.
            Logins.plugin.getServer().getScheduler().runTaskLater(Logins.plugin, new Runnable() {
                public void run() {

                    //Teleport player to SpawnPoint
                    Location loc = Logins.config.getJoinLocation(Logins.config.getNextPointIndex());
                    player.teleport(loc);
                    Logins.plugin.getLogger().info("Teleported Player! " + player.getName() + " to point : " + loc.toString()
                    );
                }
            }, SecondsToWait * 20L);

        }


    }
}
