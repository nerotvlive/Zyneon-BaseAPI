package com.zyneonstudios.api.waterfall.bungeebase.listener;

import com.zyneonstudios.api.waterfall.bungeebase.BungeeBase;
import com.zyneonstudios.api.waterfall.bungeebase.api.API;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PlayerPing implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ProxyPingEvent e) {
        ServerPing ping = e.getResponse();
        ServerPing.Players players = ping.getPlayers();
        int Protocol = e.getConnection().getVersion();
        ServerPing.Protocol vers = ping.getVersion();
        if(Protocol == 4) {
            ping.setDescriptionComponent(API.getBaseComponent("Zyneon ist ein Minecraft Minispiel und Projektenetzwerk, aktiv seit 2016! Unser Hauptaugenmerk liegt auf den Projekten!"));
            ping.setVersion(new ServerPing.Protocol("§c" + API.lowestVersion + "-" + API.highestVersion, 1));
            vers.setName("§4ZyneonCord " + BungeeBase.getVersion());
        } else if(Protocol < API.lowestProtocol) {
            ping.setDescriptionComponent(API.getBaseComponent("§4zyneonstudios.com §8- §cBitte aktualisiere dein Spiel!\n§cWir unterstützen nur Versionen ab der " + API.lowestVersion + "!"));
            ping.setVersion(new ServerPing.Protocol("§c" + API.lowestVersion + "-" + API.highestVersion, 1));
            vers.setName("§4ZyneonCord " + BungeeBase.getVersion());
        } else if(Protocol > API.highestProtocol) {
            ping.setDescriptionComponent(API.getBaseComponent("§4zyneonstudios.com §8- §cDein Spiel ist zu aktuell! Wir\n§cunterstützen momentan Versionen bis zur " + API.highestVersion + "!"));
            ping.setVersion(new ServerPing.Protocol("§c" + API.lowestVersion + "-" + API.highestVersion, 1));
            vers.setName("§4ZyneonCord " + BungeeBase.getVersion());
        } else if(API.maintenance) {
            ping.setDescriptionComponent(API.getBaseComponent("§4zyneonstudios.com §8- §cWir sind in Wartungsarbeiten!\n§cVersuche es später erneut! §4[§c"+API.lowestVersion+"§4-§c"+API.highestVersion+"§4]"));
            ping.setVersion(new ServerPing.Protocol("§cWartungsarbeiten",1));
            vers.setName("§4ZyneonCord " + BungeeBase.getVersion());
        } else {
            /*
            vers.setName("§9ZyneonCord " + BungeeBase.getVersion());
            players.setSample(new ServerPing.PlayerInfo[]{
                    new ServerPing.PlayerInfo("§9ZyneonCord "+BungeeBase.getVersion(), "ZyneonCord "+BungeeBase.getVersion())
            });
            ping.setDescriptionComponent(API.getBaseComponent(API.getMotd(1).replace("%lV%",API.lowestVersion).replace("%hV%",API.highestVersion) + "\n" + API.getMotd(2).replace("%lV%",API.lowestVersion).replace("%hV%",API.highestVersion)));
            */
            vers.setName("§9ZyneonCord " + BungeeBase.getVersion());
            players.setSample(new ServerPing.PlayerInfo[]{
                    new ServerPing.PlayerInfo("§9ZyneonCord "+BungeeBase.getVersion(), "ZyneonCord "+BungeeBase.getVersion())
            });
            //ping.setDescriptionComponent(API.getBaseComponent("               §9§lＺＹＮＥＯＮ ＳＴＵＤＩＯＳ§r\n §9１§8．§9１９ §fＵＰＤＡＴＥ §8｜ §aＣＲＥＡＴＩＶＥ§8－§7ＲＥＬＥＡＳＥ"));
            BaseComponent component = new TextComponent(new ComponentBuilder("                     ZYNEON STUDIOS\n").color(ChatColor.of(java.awt.Color.decode("#3696ff"))).append("§r            §91§8.§919§8-§7Update §8| §aCreative§8-§7Release§r").create());

            ping.setDescriptionComponent(component);
        }
    }

    /*@EventHandler
    public void on(ProxyDefineCommandsEvent e) {
        e.getCommands().clear();
    }*/
}