package com.zyneonstudios.api.server;

import com.zyneonstudios.api.Zyneon;
import com.zyneonstudios.api.utils.Countdown;
import com.zyneonstudios.api.utils.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Server {

    private boolean isStopping;
    private final org.bukkit.Server bukkitServer;

    public Server() {
        isStopping = false;
        bukkitServer = Bukkit.getServer();
    }

    public boolean isStopping() {
        return this.isStopping;
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