package com.zyneonstudios.api.waterfall.server;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.api.utils.sql.MySQL;
import com.zyneonstudios.api.waterfall.Zyneon;
import com.zyneonstudios.api.waterfall.bungeebase.BungeeBase;
import com.zyneonstudios.api.waterfall.configuration.Config;
import com.zyneonstudios.api.waterfall.utils.Countdown;
import com.zyneonstudios.api.waterfall.utils.communication.Communicate;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.concurrent.ThreadLocalRandom;

public class Server {

    private final MySQL mysql;
    private int serverID;
    private final Config config;
    private boolean isRegistered;
    private boolean isStopping;
    private final ProxyServer proxyServer;

    public Server() {
        isRegistered = false;
        isStopping = false;
        proxyServer = ProxyServer.getInstance();
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
        return this.mysql;
    }

    public Config getConfig() {
        return this.config;
    }

    public int getServerID() {
        return this.serverID;
    }

    public boolean isRegistered() {
        return this.isRegistered;
    }

    public ProxyServer getProxyServer() {
        return this.proxyServer;
    }

    public boolean isStopping() {
        return this.isStopping;
    }

    public void setRegistered(boolean bool) {
        this.isRegistered = bool;
    }

    public void stopServer() {
        isStopping = true;
        new Countdown(27, BungeeBase.getInstance()) {
            @Override
            public void count(int current) {
                if (current < 26) {
                    sendMessage("Proxy-Neustart in " + current + " Sekunden.");
                    if (current == 0) {
                        for(ProxiedPlayer all : BungeeBase.getInstance().getProxy().getPlayers()) {
                            all.disconnect("§cNetzwerk-Neustart\n§7Bitte warte etwas§8... Es kann ein paar Minuten dauern§8,§7 bis der Server wieder erreichbar ist§8!");
                        }
                        BungeeBase.getInstance().getProxy().stop();
                    }
                }
            }
        }.start();
    }

    public void stopNetwork() {
        Communicate.sendStop();
        stopServer();
    }

    public void sendRawMessage(String message) {
        System.out.println(message);
    }

    public void sendMessage(String message) {
        sendRawMessage(Strings.prefix()+message.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }

    public void sendWarnMessage(String message) {
        sendRawMessage("§e"+message.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }

    public void sendErrorMessage(String message) {
        sendRawMessage("§c"+message.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }

    public void instantStop(String reason) {
        isStopping = true;
        for(ProxiedPlayer all : proxyServer.getPlayers()) {
            all.disconnect(reason);
        }
        proxyServer.stop(reason);
    }
}