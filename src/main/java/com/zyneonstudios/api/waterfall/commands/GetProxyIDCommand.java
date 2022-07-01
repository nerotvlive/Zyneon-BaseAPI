package com.zyneonstudios.api.waterfall.commands;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.api.waterfall.utils.communication.Communicate;
import com.zyneonstudios.api.waterfall.utils.user.Sound;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class GetProxyIDCommand extends Command {

    public GetProxyIDCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("zyneon.leading.register")) {
            int id = Zyneon.getZyneonServer().getServerID();
            boolean isRegistered = Zyneon.getZyneonServer().isRegistered();
            if(isRegistered) {
                s.sendMessage(Strings.prefix()+"§aDer Server ist registriert§8.");
                s.sendMessage(Strings.prefix()+"§7Die §eServer§8-§eID §7lautet§8: §a"+id);
                if(s instanceof ProxiedPlayer p) {
                    Communicate.sendSound(p, Sound.ENTITY_CHICKEN_EGG.toString());
                }
            } else {
                if(Zyneon.getZyneonServer().getConfig().getCFG().getBoolean("MySQL.enable")) {
                    s.sendMessage(Strings.prefix()+"§eDer Server ist nicht registriert§8!");
                    s.sendMessage(Strings.prefix()+"§7Die &etemporäre Server§8-§eID&7 lautet§8: §a"+id);
                    s.sendMessage(Strings.prefix()+"§7Um die ID fest zu speichern und zu registrieren, mache §f/register [Server-Name]§8!");
                    if(s instanceof ProxiedPlayer p) {
                        Communicate.sendSound(p, Sound.ENTITY_CHICKEN_EGG.toString());
                    }
                } else {
                    s.sendMessage(Strings.prefix()+"§cDer Server kann zurzeit nicht registriert werden§8!");
                    s.sendMessage(Strings.prefix()+"§7Die &etemporäre Server§8-§eID&7 lautet§8: §a"+id);
                    s.sendMessage(Strings.prefix()+"§7Um den Server registrieren zu können§8,§7 verbinde ihn mit einer Datenbank§8!");
                    if(s instanceof ProxiedPlayer p) {
                        Communicate.sendSound(p, Sound.BLOCK_ANVIL_BREAK.toString());
                    }
                }
            }

        } else {
            s.sendMessage(Strings.noPerms());
            if(s instanceof ProxiedPlayer p) {
                Communicate.sendSound(p, Sound.BLOCK_ANVIL_BREAK.toString());
            }
        }
    }
}
