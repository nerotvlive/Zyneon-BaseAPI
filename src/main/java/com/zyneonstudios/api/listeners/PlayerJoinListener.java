package com.zyneonstudios.api.listeners;

import com.zyneonstudios.api.Zyneon;
import com.zyneonstudios.api.utils.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        Zyneon.getAPI().connectUser(uuid);
        User u = Zyneon.getAPI().getOnlineUser(uuid);
    }
}