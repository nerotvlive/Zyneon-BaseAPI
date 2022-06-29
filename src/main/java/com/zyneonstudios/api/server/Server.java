package com.zyneonstudios.api.server;

import com.zyneonstudios.api.Zyneon;
import com.zyneonstudios.api.configuration.Config;
import com.zyneonstudios.api.sql.MySQL;
import com.zyneonstudios.api.utils.Countdown;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.api.utils.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class Server {

    private final MySQL mysql;
    private int serverID;
    private final Config config;
    private boolean isRegistered;
    private boolean isStopping;
    private final org.bukkit.Server bukkitServer;

    public Server() {
        isRegistered = false;
        isStopping = false;
        bukkitServer = Bukkit.getServer();
        config = new Config("plugins/Zyneon/server.yml");
        if(config.getFile().exists()) {
            if(config.getCFG().contains("Server.ID")) {
                isRegistered = true;
            } else {
                isRegistered = false;
            }
        }
        config.checkEntry("MySQL.enable",false);
        config.checkEntry("MySQL.host","localhost");
        config.checkEntry("MySQL.port","3306");
        config.checkEntry("MySQL.user","root");
        config.checkEntry("MySQL.database","zyneon_server");
        config.checkEntry("MySQL.password","password");
        config.saveConfig();
        config.reloadConfig();
        if(config.getCFG().getBoolean("MySQL.enable")) {
            mysql = new MySQL(config.getCFG().getString("MySQL.host"),config.getCFG().getString("MySQL.port"),config.getCFG().getString("MySQL.database"),config.getCFG().getString("MySQL.user"),config.getCFG().getString("MySQL.password"));
        } else {
            mysql = null;
        }
        if (isRegistered) {
            serverID = config.getCFG().getInt("Server.ID");
        }
    }

    public void generateID() {
        if(!isRegistered) {
            String SID = "13" + "" + ThreadLocalRandom.current().nextInt(1000, 9999);
            int id = Integer.parseInt(SID);
            if (config.getCFG().getBoolean("MySQL.enable")) {
                if (Zyneon.getAPI().getIDS().contains(id)) {
                    generateID();
                } else {
                    serverID = id;
                }
            } else {
                serverID = id;
            }
        }
    }

    public MySQL getSQL() {
        return mysql;
    }

    public Config getConfig() {
        return config;
    }

    public int getServerID() {
        return serverID;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public org.bukkit.Server getBukkitServer() {
        return bukkitServer;
    }

    public boolean isStopping() {
        return this.isStopping;
    }

    public void setRegistered(boolean bool) {
        this.isRegistered = bool;
    }

    public boolean stopServer() {
        if(!isStopping) {
            isStopping = true;
            new Countdown(26, Zyneon.getInstance()) {
                @Override
                public void count(int current) {
                    if(current==25) {
                        for(User online : Zyneon.getOnlineUsers().values()) {
                            online.sendTitle("Serverneustart§8...","§8...§7in §e"+current+"§7 Sekunden§8!");
                            online.sendWarnMessage("Der Server startet in "+current+" Sekunden neu§8!");
                        }
                    } else if(current==20) {
                        for(User online : Zyneon.getOnlineUsers().values()) {
                            online.sendTitle("Serverneustart§8...","§8...§7in §e"+current+"§7 Sekunden§8!");
                            online.sendWarnMessage("Der Server startet in "+current+" Sekunden neu§8!");
                        }
                    } else if(current==15) {
                        for(User online : Zyneon.getOnlineUsers().values()) {
                            online.sendTitle("Serverneustart§8...","§8...§7in §e"+current+"§7 Sekunden§8!");
                            online.sendWarnMessage("Der Server startet in "+current+" Sekunden neu§8!");
                        }
                    } else if(current<11) {
                        if(current == 1) {
                            for(User online : Zyneon.getOnlineUsers().values()) {
                                online.sendTitle("Serverneustart§8...","§8...§7in §e"+current+"§7 Sekunden§8!");
                                online.sendWarnMessage("Der Server startet in "+current+" Sekunden neu§8!");
                            }
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.kickPlayer("Der Server startet neu...");
                            }
                        } else if(current == 0) {
                            for(User online : Zyneon.getOnlineUsers().values()) {
                                online.sendTitle("Serverneustart§8...","§8...§7in §e"+current+"§7 Sekunden§8!");
                                online.sendWarnMessage("Der Server startet in "+current+" Sekunden neu§8!");
                            }
                            instantStop("Der Server startet neu...");
                        } else if(current>0) {
                            for(User online : Zyneon.getOnlineUsers().values()) {
                                online.sendTitle("Serverneustart§8...","§8...§7in §e"+current+"§7 Sekunden§8!");
                                online.sendWarnMessage("Der Server startet in "+current+" Sekunden neu§8!");
                            }
                        }
                    }
                }
            }.start();
            return true;
        } else {
            return false;
        }
    }

    public void sendMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+message.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }

    public void sendErrorMessage(String message) {
        Bukkit.getConsoleSender().sendMessage("§c"+message.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }

    public void instantStop(String reason) {
        isStopping = true;
        for(Player all : Bukkit.getOnlinePlayers()) {
            all.kickPlayer(reason);
        }
        bukkitServer.shutdown();
    }

    public void setSlots(int slots) {
        bukkitServer.setMaxPlayers(slots);
    }
}