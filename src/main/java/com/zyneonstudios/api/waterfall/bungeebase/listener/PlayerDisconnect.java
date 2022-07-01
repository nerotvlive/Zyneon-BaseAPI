package com.zyneonstudios.api.waterfall.bungeebase.listener;

import com.zyneonstudios.api.waterfall.bungeebase.api.API;
import com.zyneonstudios.api.waterfall.bungeebase.utils.ZyneonPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnect implements Listener {

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        ProxiedPlayer p = e.getPlayer();
        ZyneonPlayer zP = API.zyneonPlayers.get(p.getUniqueId());
        API.zyneonPlayers.remove(p.getUniqueId());
        zP.destroy();
    }
}
