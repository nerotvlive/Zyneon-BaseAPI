package com.zyneonstudios.api.paper;

import com.zyneonstudios.api.paper.commands.GetIDCommand;
import com.zyneonstudios.api.paper.commands.RegisterCommand;
import com.zyneonstudios.api.paper.listeners.PlayerChatListener;
import com.zyneonstudios.api.paper.listeners.PlayerConnectListener;
import com.zyneonstudios.api.paper.server.Server;
import com.zyneonstudios.api.paper.listeners.PlayerJoinListener;
import com.zyneonstudios.api.paper.listeners.PlayerQuitListener;
import com.zyneonstudios.api.paper.utils.ZyneonAPI;
import com.zyneonstudios.api.paper.utils.user.User;
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
    public void onLoad() {
        instance = this;
        zAPI = new ZyneonAPI();
        server = new Server();
    }

    @Override
    public void onEnable() {
        pm = Bukkit.getPluginManager();
        initListeners();
        getCommand("register").setExecutor(new RegisterCommand());
        getCommand("getid").setExecutor(new GetIDCommand());
        server.generateID();
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
        initListenerClass(new PlayerChatListener());
        initListenerClass(new PlayerConnectListener());
        initListenerClass(new PlayerJoinListener());
        initListenerClass(new PlayerQuitListener());
    }

    private void initListenerClass(Listener listener) {
        getPluginManager().registerEvents(listener,this);
    }
}
