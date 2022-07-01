package com.zyneonstudios.api.waterfall.server;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.api.utils.sql.MySQL;
import com.zyneonstudios.api.waterfall.ProxiedZyneon;
import com.zyneonstudios.api.waterfall.bungeebase.BungeeBase;
import com.zyneonstudios.api.waterfall.configuration.ProxiedConfig;
import com.zyneonstudios.api.waterfall.utils.ProxiedCountdown;
import com.zyneonstudios.api.waterfall.utils.communication.Communicate;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import java.util.concurrent.ThreadLocalRandom;

public class ProxiedServer {

    private final MySQL mysql;
    private int serverID;
    private final ProxiedConfig proxiedConfig;
    private boolean isRegistered;
    private boolean isStopping;
    private final ProxyServer proxyServer;

    public ProxiedServer() {
        isRegistered = false;
        isStopping = false;
        proxyServer = ProxyServer.getInstance();
        proxiedConfig = new ProxiedConfig("plugins/Zyneon/server.yml");
        if(proxiedConfig.getFile().exists()) {
            if(proxiedConfig.getCFG().contains("Server.ID")) {
                isRegistered = true;
            } else {
                isRegistered = false;
            }
        }
        proxiedConfig.checkEntry("MySQL.enable",false);
        proxiedConfig.checkEntry("MySQL.host","localhost");
        proxiedConfig.checkEntry("MySQL.port","3306");
        proxiedConfig.checkEntry("MySQL.user","root");
        proxiedConfig.checkEntry("MySQL.database","zyneon_server");
        proxiedConfig.checkEntry("MySQL.password","password");
        proxiedConfig.saveConfig();
        proxiedConfig.reloadConfig();
        if(proxiedConfig.getCFG().getBoolean("MySQL.enable")) {
            mysql = new MySQL(proxiedConfig.getCFG().getString("MySQL.host"), proxiedConfig.getCFG().getString("MySQL.port"), proxiedConfig.getCFG().getString("MySQL.database"), proxiedConfig.getCFG().getString("MySQL.user"), proxiedConfig.getCFG().getString("MySQL.password"));
        } else {
            mysql = null;
        }
        if (isRegistered) {
            serverID = proxiedConfig.getCFG().getInt("Server.ID");
        }
    }

    public void generateID() {
        if(!isRegistered) {
            String SID = "13" + "" + ThreadLocalRandom.current().nextInt(1000, 9999);
            int id = Integer.parseInt(SID);
            if (proxiedConfig.getCFG().getBoolean("MySQL.enable")) {
                if (ProxiedZyneon.getAPI().getIDS().contains(id)) {
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

    public ProxiedConfig getConfig() {
        return this.proxiedConfig;
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
        new ProxiedCountdown(27, BungeeBase.getInstance()) {
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