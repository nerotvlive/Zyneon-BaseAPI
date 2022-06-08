package com.zyneonstudios.api.utils;

import com.zyneonstudios.api.utils.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class ZyneonAPI {

    private HashMap<UUID, User> onlineUsers;

    public ZyneonAPI() {
        onlineUsers = new HashMap<>();
    }

    public void connectUser(UUID uuid) {
        this.onlineUsers.put(uuid,new User(uuid));
    }

    public void disconnectUser(User user) {
        onlineUsers.remove(user.getUUID());
        user.disconnect();
    }

    public User getOnlineUser(UUID uuid) {
        if(onlineUsers.containsKey(uuid)) {
            return onlineUsers.get(uuid);
        } else {
            this.connectUser(uuid);
            return this.getOnlineUser(uuid);
        }
    }

    public int getYearDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public String getTime() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        return format.format(now);
    }

    public int getMonth() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return Integer.parseInt(format.format(now).replace("0",""));
    }

    public int getYear() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return Integer.parseInt(format.format(now));
    }

    public void initListenerClass(PluginManager pluginManager, Listener listener, Plugin plugin) {
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§f  -> §7Lade Listenerklasse §e"+listener.getClass().getSimpleName()+"§8...");
        pluginManager.registerEvents(listener,plugin);
    }

    public HashMap<UUID,User> getOnlineUsers() {
        return this.onlineUsers;
    }
}