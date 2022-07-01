package com.zyneonstudios.api.waterfall.listeners;

import com.zyneonstudios.api.waterfall.Zyneon;
import com.zyneonstudios.api.waterfall.utils.user.ProxiedUser;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PlayerLoginListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(LoginEvent e) {
        PendingConnection p = e.getConnection();
        ProxiedUser u = Zyneon.getAPI().getOnlineUser(p.getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPostLogin(PostLoginEvent e) {
        ProxiedPlayer p = e.getPlayer();
        ProxiedUser u = Zyneon.getAPI().getOnlineUser(p.getUniqueId());
    }
}