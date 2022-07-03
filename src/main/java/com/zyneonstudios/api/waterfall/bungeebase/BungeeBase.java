package com.zyneonstudios.api.waterfall.bungeebase;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.api.waterfall.ProxiedZyneon;
import com.zyneonstudios.api.waterfall.bungeebase.api.API;
import com.zyneonstudios.api.waterfall.bungeebase.api.ConfigAPI;
import com.zyneonstudios.api.waterfall.bungeebase.commands.*;
import com.zyneonstudios.api.waterfall.bungeebase.listener.*;

public class BungeeBase {

    public static ProxiedZyneon instance;
    private static String version = "1.24.1-1.19-SNAPSHOT";

    public static ProxiedZyneon getInstance() { return instance; }
    public static String getVersion() { if(version.equalsIgnoreCase("v")) {return "0";} else {return version;} }

    @Deprecated
    public static void onLoad() {
        instance = ProxiedZyneon.getInstance();
        Strings.setPrefixWord("Zyneon");
        API.sendInit();
        API.sendMessage("§0");
        API.sendMessage("§1");
        API.sendMessage("Das Plugin §aBungeeBase ("+getVersion()+")§7 von §cnerotvlive§7 wird geladen...");
        API.sendMessage("Das Plugin §aBungeeBase ("+getVersion()+")§7 von §cnerotvlive§7 wurde geladen!");
        API.sendMessage("§2");
        API.sendMessage("§3");
        API.sendInit();
    }

    @Deprecated
    public static void onEnable() {
        instance = ProxiedZyneon.getInstance();
        ConfigAPI.reloadConfig();
        API.checkForRestart();
        API.sendInit();
        API.sendMessage("§0");
        API.sendMessage("§1");
        API.sendMessage("Das Plugin §aBungeeBase ("+getVersion()+")§7 von §cnerotvlive§7 wird aktiviert...");
        API.initConfig();
        initPlugin();
        API.sendMessage("Das Plugin §aBungeeBase ("+getVersion()+")§7 von §cnerotvlive§7 wurde aktiviert!");
        API.sendMessage("§2");
        API.sendMessage("§3");
        API.sendInit();
    }

    private static void initPlugin() {
        ConfigAPI.reloadConfig();
        API.sendMessage("§0");
        API.sendMessage("§0  §7Spigot-Channel wird registriert...");
        instance.getProxy().registerChannel("base:bungee");
        API.sendMessage("§0  §7Spigot-Channel wurde registriert!");
        API.sendMessage("§0");
        initCommands();
    }

    private static void initCommands() {
        API.sendMessage("§0  §7Die Commands werden geladen§8...");
        API.registerCommand(instance,new Lobby(),"Hub");
        API.registerCommand(instance,new Maintenance(),"bungeemaintenance");
        API.registerCommand(instance,new BSRL(),"bsrl");
        API.registerCommand(instance,new Ban(),"Ban");
        API.registerCommand(instance,new IPBan(),"IPBan");
        API.registerCommand(instance,new Banlist(),"Banlist");
        API.registerCommand(instance,new Unban(),"Unban");
        API.registerCommand(instance,new Kick(),"Kick");
        API.registerCommand(instance,new Disconnect(),"Disconnect");
        API.registerCommand(instance,new ServerMOTD(),"ServerMOTD");
        API.registerCommand(instance,new com.zyneonstudios.api.waterfall.bungeebase.commands.BungeeBase(),"BungeeBase");
        API.registerCommand(instance,new Get(),"Get");
        API.registerCommand(instance,new Teamchat(),"Teamchat");
        API.sendMessage("§0  §7Die Commands wurden geladen§8!");
        API.sendMessage("§0");
        initListener();
    }

    private static void initListener() {
        API.sendMessage("§0  §7Die Listener werden geladen§8...");
        API.registerEvent(instance,new PlayerChangeServer(),"PlayerChangeServerEvent");
        API.registerEvent(instance,new PlayerDisconnect(),"PlayerDisconnectEvent");
        API.registerEvent(instance,new PlayerJoin(),"PlayerJoinEvent");
        API.registerEvent(instance,new PlayerLogin(),"PlayerLoginEvent");
        API.registerEvent(instance,new PlayerPing(),"PlayerPingEvent");
        API.sendMessage("§0  §7Die Listener wurden geladen§8!");
        API.sendMessage("§0");
        ConfigAPI.CFG.set("API.Plugin.Version",getVersion());
        ConfigAPI.saveConfig();
    }

    @Deprecated
    public static void onDisable() {
        API.sendInit();
        API.sendMessage("§0");
        API.sendMessage("§1");
        API.sendMessage("Das Plugin §aBungeeBase ("+getVersion()+")§7 von §cnerotvlive§7 wird deaktiviert...");
        instance.getProxy().unregisterChannel("base:bungee");
        API.sendMessage("Das Plugin §aBungeeBase ("+getVersion()+")§7 von §cnerotvlive§7 wurde deaktiviert!");
        API.sendMessage("§2");
        API.sendMessage("§3");
        API.sendInit();
        version = null;
        instance = null;
        API.highestVersion = null;
        API.lowestVersion = null;
        API.lowestProtocol = 0;
        API.maintenance = false;
    }
}