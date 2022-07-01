package com.zyneonstudios.api.waterfall;

import com.zyneonstudios.api.waterfall.bungeebase.BungeeBase;
import com.zyneonstudios.api.waterfall.commands.GetProxyIDCommand;
import com.zyneonstudios.api.waterfall.commands.RegisterProxyCommand;
import com.zyneonstudios.api.waterfall.listeners.PlayerDisconnectListener;
import com.zyneonstudios.api.waterfall.listeners.PlayerLoginListener;
import com.zyneonstudios.api.waterfall.server.ProxiedServer;
import com.zyneonstudios.api.waterfall.utils.ProxiedZyneonAPI;
import com.zyneonstudios.api.waterfall.utils.user.ProxiedUser;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.HashMap;
import java.util.UUID;

public class ProxiedZyneon extends Plugin {

    private static ProxiedServer proxiedServer;
    private static ProxiedZyneonAPI zAPI;
    private static PluginManager pm;
    private static ProxiedZyneon instance;

    @Override
    public void onLoad() {
        instance = this;
        zAPI = new ProxiedZyneonAPI();
        proxiedServer = new ProxiedServer();
        pm = ProxyServer.getInstance().getPluginManager();
        BungeeBase.onLoad();
    }

    @Override
    public void onEnable() {
        initListeners();
        BungeeBase.onEnable();
        pm.registerCommand(instance,new GetProxyIDCommand("GetProxyID"));
        pm.registerCommand(instance,new RegisterProxyCommand("RegisterProxy"));
        proxiedServer.generateID();
    }

    @Override
    public void onDisable() {
        BungeeBase.onDisable();
        instance = null;
        proxiedServer = null;
        zAPI = null;
        pm = null;
    }

    public static ProxiedZyneon getInstance() {
        return instance;
    }

    public static ProxiedServer getZyneonServer() {
        return proxiedServer;
    }

    public static HashMap<UUID, ProxiedUser> getOnlineUsers() {
        return getAPI().getOnlineUsers();
    }

    public static ProxiedZyneonAPI getAPI() {
        return zAPI;
    }

    public static PluginManager getPluginManager() {
        return pm;
    }

    private void initListeners() {
        initListenerClass(new PlayerLoginListener());
        initListenerClass(new PlayerDisconnectListener());
    }

    private void initListenerClass(Listener listener) {
        pm.registerListener(instance,listener);
    }
}