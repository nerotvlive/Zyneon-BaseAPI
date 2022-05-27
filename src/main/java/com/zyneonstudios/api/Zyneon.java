package com.zyneonstudios.api;

import com.zyneonstudios.api.server.Server;
import com.zyneonstudios.api.listeners.PlayerJoinListener;
import com.zyneonstudios.api.listeners.PlayerQuitListener;
import com.zyneonstudios.api.utils.ZyneonAPI;
import com.zyneonstudios.api.utils.user.User;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.UUID;

public final class Zyneon extends JavaPlugin {

    private static Server server;
    private static ZyneonAPI zAPI;
    private static PluginManager pm;
    private static Zyneon instance;

    @Override
    public void onEnable() {
        instance = this;
        server = new Server();
        zAPI = new ZyneonAPI();
        pm = Bukkit.getPluginManager();
        initListeners();
    }

    @Override
    public void onDisable() {
        instance = null;
        server = null;
        zAPI = null;
        pm = null;
    }

    public static Zyneon getInstance() {
        return instance;
    }

    public static Server getZyneonServer() {
        return server;
    }

    public static HashMap<UUID, User> getOnlineUsers() {
        return getAPI().getOnlineUsers();
    }

    public static ZyneonAPI getAPI() {
        return zAPI;
    }

    public static PluginManager getPluginManager() {
        return pm;
    }

    private void initListeners() {
        initListenerClass(new PlayerJoinListener());
        initListenerClass(new PlayerQuitListener());
    }

    private void initListenerClass(Listener listener) {
        getPluginManager().registerEvents(listener,this);
    }
}
