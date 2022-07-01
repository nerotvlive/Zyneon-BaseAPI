package com.zyneonstudios.api.waterfall.listeners;

import com.zyneonstudios.api.waterfall.Zyneon;
import com.zyneonstudios.api.waterfall.utils.user.ProxiedUser;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnectListener implements Listener {

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent e) {
        ProxiedPlayer p = e.getPlayer();
        ProxiedUser u = Zyneon.getAPI().getOnlineUser(p.getUniqueId());
        u.disconnect();
    }
}