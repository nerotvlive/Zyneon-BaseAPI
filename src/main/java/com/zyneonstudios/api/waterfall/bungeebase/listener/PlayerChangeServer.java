package com.zyneonstudios.api.waterfall.bungeebase.listener;

import com.zyneonstudios.api.waterfall.Zyneon;
import com.zyneonstudios.api.waterfall.utils.user.ProxiedUser;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerChangeServer implements Listener {

    @EventHandler
    public void onServerChange(ServerSwitchEvent e) {
        ProxiedPlayer p = e.getPlayer();
        ProxiedUser u = Zyneon.getAPI().getOnlineUser(p.getUniqueId());
        u.setInit(u.getInit()+1);
        String serverName = e.getPlayer().getServer().getInfo().getName();
        if(u.getInit()>=2) {
            p.sendMessage("§8»§7 Du bist nun auf §e" + serverName + "§8!");
        } else {
            p.sendMessage("§8» §a"+p.getName());
        }
    }
}