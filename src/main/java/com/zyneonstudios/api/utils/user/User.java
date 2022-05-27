package com.zyneonstudios.api.utils.user;

import com.zyneonstudios.api.Zyneon;
import live.nerotv.lobbysystem.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.UUID;

public class User {

    private Player player;
    private OfflinePlayer offlinePlayer;
    private UUID uuid;
    private boolean isBedrock;

    public User(UUID uuid) {
        if(Bukkit.getPlayer(uuid)!=null) {
            this.player = Bukkit.getPlayer(uuid);
        } else {
            this.player = null;
        }
        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        this.uuid = uuid;
        if (Zyneon.getPluginManager().getPlugin("floodgate") != null) {
            if (offlinePlayer.getName() != null) {
                if (offlinePlayer.getName().contains("*")) {
                    this.isBedrock = true;
                } else {
                    this.isBedrock = org.geysermc.floodgate.api.FloodgateApi.getInstance().isFloodgatePlayer(uuid);
                }
            } else {
                this.isBedrock = org.geysermc.floodgate.api.FloodgateApi.getInstance().isFloodgatePlayer(uuid);
            }
        } else {
            this.isBedrock = false;
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public OfflinePlayer getOfflinePlayer() {
        return this.offlinePlayer;
    }

    public Object getSpecialUser(UserType type) {
        if(type.equals(UserType.projectUser)) {
            if(Zyneon.getPluginManager().isPluginEnabled("ProjectsBase")) {
                return live.nerotv.projectsbase.api.API.getUser(uuid);
            }
        }
        return null;
    }

    public boolean isBedrock() {
        return isBedrock;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void switchServer(String serverName) {
        if(player!=null) {
            try {
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(byteArray);
                out.writeUTF("Connect");
                out.writeUTF(serverName);
                player.sendPluginMessage(Main.get(), "BungeeCord", byteArray.toByteArray());
            } catch (Exception ignore) {
            }
        }
    }

    public void sendTitle(String title) {
        sendTitle(title,null);
    }

    public void sendTitle(String title, String subtitle) {
        sendTitle(title,subtitle,10);
    }

    public void sendTitle(String title, String subtitle, int showTime) {
        if(player!=null) {
            UserTitle.sendTitle(player,1,showTime,1,title,subtitle);
        }
    }

    public void sendRawMessage(String message) {
        if(player!=null) {
            player.sendMessage(message);
        }
    }

    public void sendMessage(String message) {
        sendRawMessage(message);
        if(player!=null) {
            player.playSound(player.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
        }
    }

    public void sendWarnMessage(String message) {
        sendRawMessage("§e"+message);
        if(player!=null) {
            player.playSound(player.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
        }
    }

    public void sendErrorMessage(String message) {
        sendRawMessage("§4"+message);
        if(player!=null) {
            player.playSound(player.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
        }
    }

    public void disconnect() {
        if(player!=null) {
            player.kickPlayer("Verbindung getrennt.");
            player = null;
        }
        offlinePlayer = null;
        Zyneon.getAPI().getOnlineUsers().remove(uuid);
        uuid = null;
        System.gc();
    }

    public void disconnect(String reason) {
        if(player!=null) {
            player.kickPlayer(reason);
            player = null;
        }
        offlinePlayer = null;
        Zyneon.getAPI().getOnlineUsers().remove(uuid);
        uuid = null;
        System.gc();
    }
}