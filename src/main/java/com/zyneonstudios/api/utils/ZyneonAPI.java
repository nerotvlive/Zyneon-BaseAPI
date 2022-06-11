package com.zyneonstudios.api.utils;

import com.zyneonstudios.api.Zyneon;
import com.zyneonstudios.api.utils.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public ArrayList<Integer> getIDS() {
        if(Zyneon.getZyneonServer().getConfig().getCFG().getBoolean("MySQL.enable")) {
            try {
                ArrayList<Integer> list = new ArrayList<>();
                PreparedStatement ps = Zyneon.getZyneonServer().getSQL().getConnection().prepareStatement("SELECT * FROM serverlist ORDER BY ID DESC");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(rs.getInt(1));
                }
                return list;
            } catch (SQLException e) {
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
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

    public int getSeconds() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("ss");
        return Integer.parseInt(format.format(now));
    }

    public int getMinute() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("mm");
        return Integer.parseInt(format.format(now));
    }

    public int getHour() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH");
        return Integer.parseInt(format.format(now));
    }

    public int getDay() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd");
        return Integer.parseInt(format.format(now).replace("0",""));
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