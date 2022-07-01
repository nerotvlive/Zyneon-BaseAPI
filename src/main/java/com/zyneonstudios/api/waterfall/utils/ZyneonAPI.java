package com.zyneonstudios.api.waterfall.utils;

import com.zyneonstudios.api.waterfall.Zyneon;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.api.waterfall.utils.user.ProxiedUser;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ZyneonAPI {

    private HashMap<UUID, ProxiedUser> onlineUsers;
    public ZyneonAPI() {
        onlineUsers = new HashMap<>();
    }

    public HashMap<UUID, ProxiedUser> getOnlineUsers() {
        return this.onlineUsers;
    }

    public void connectUser(UUID uuid) {
        this.onlineUsers.put(uuid,new ProxiedUser(uuid));
    }

    public void disconnectUser(ProxiedUser user) {
        onlineUsers.remove(user.getUUID());
        user.disconnect();
    }

    public ProxiedUser getOnlineUser(UUID uuid) {
        if(onlineUsers.containsKey(uuid)) {
            return onlineUsers.get(uuid);
        } else {
            this.connectUser(uuid);
            return this.getOnlineUser(uuid);
        }
    }

    public ArrayList<Integer> getIDS() {
        if (Zyneon.getZyneonServer().getConfig().getCFG().getBoolean("MySQL.enable")) {
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

    public static void initListenerClass(PluginManager pluginManager, Listener listener, Plugin plugin) {
        System.out.println(Strings.prefix()+"§f  -> §7Lade Listenerklasse §e"+listener.getClass().getSimpleName()+"§8...");
        pluginManager.registerListener(plugin,listener);
    }
}