package com.zyneonstudios.api.waterfall.utils.user;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.api.waterfall.Zyneon;
import com.zyneonstudios.api.waterfall.bungeebase.api.BanAPI;
import com.zyneonstudios.api.waterfall.utils.communication.Communicate;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import java.util.UUID;

public class ProxiedUser {

    private ProxiedPlayer player;
    private int init;
    private UUID uuid;
    private boolean isBedrock;

    public ProxiedUser(UUID uuid) {
        if(ProxyServer.getInstance().getPlayer(uuid)!=null) {
            this.player = ProxyServer.getInstance().getPlayer(uuid);
        } else {
            this.player = null;
        }
        this.init = 0;
        this.uuid = uuid;
        this.isBedrock = false;
        if(Zyneon.getPluginManager().getPlugin("floodgate")!=null) {
            if(player!=null) {
                if(player.getName().contains("*")) {
                    this.isBedrock = true;
                } else {
                    this.isBedrock = org.geysermc.floodgate.api.FloodgateApi.getInstance().isFloodgatePlayer(uuid);
                }
            } else {
                this.isBedrock = org.geysermc.floodgate.api.FloodgateApi.getInstance().isFloodgatePlayer(uuid);
            }
        }
    }

    public ProxiedPlayer getPlayer() {
        return this.player;
    }

    public int getInit() {
        return this.init;
    }

    public boolean isBedrock() {
        return this.isBedrock;
    }

    public boolean isBanned() {
        if(player!=null) {
            String a = player.getAddress().getHostString();
            if(BanAPI.isBanned(a)) {
                return true;
            }
        }
        return BanAPI.isBanned(uuid);
    }

    public void switchServer(String serverName) {
        if(this.player!=null) {
            this.player.connect(ProxyServer.getInstance().getServerInfo(serverName));
        }
    }

    public void sendRawMessage(String message) {
        if(this.player!=null) {
            this.player.sendMessage(message);
        }
    }

    public void sendMessage(String message) {
        sendRawMessage(Strings.prefix()+message);
        if(this.player!=null) {
            Communicate.sendSound(this.player,Sound.ENTITY_CHICKEN_EGG.toString());
        }
    }

    public void sendWarnMessage(String message) {
        sendRawMessage("§e"+message);
        if(this.player!=null) {
            Communicate.sendSound(this.player,Sound.BLOCK_NOTE_BLOCK_PLING.toString());
        }
    }

    public void sendErrorMessage(String message) {
        sendRawMessage("§c"+message);
        if(this.player!=null) {
            Communicate.sendSound(this.player,Sound.BLOCK_ANVIL_BREAK.toString());
        }
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setInit(int init) {
        this.init = init;
    }

    public void disconnect() {
        if(this.player!=null) {
            this.player.disconnect("Verbindung getrennt.");
            this.player = null;
        }
        this.isBedrock = false;
        this.init = -1;
        this.uuid = null;
        System.gc();
    }

    public void disconnect(String reason) {
        if(this.player!=null) {
            this.player.disconnect(reason);
            this.player = null;
        }
        this.disconnect();
    }
}
