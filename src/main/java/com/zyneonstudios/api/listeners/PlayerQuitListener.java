package com.zyneonstudios.api.listeners;

import com.zyneonstudios.api.Zyneon;
import com.zyneonstudios.api.utils.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.UUID;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        User u = Zyneon.getAPI().getOnlineUser(uuid);
        Zyneon.getAPI().disconnectUser(u);
    }
}