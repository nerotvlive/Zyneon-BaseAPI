package com.zyneonstudios.api.paper.commands;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.utils.Strings;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GetIDCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s.hasPermission("zyneon.leading.register")) {
            int id = Zyneon.getZyneonServer().getServerID();
            boolean isRegistered = Zyneon.getZyneonServer().isRegistered();
            if(isRegistered) {
                s.sendMessage(Strings.prefix()+"§aDer Server ist registriert§8.");
                s.sendMessage(Strings.prefix()+"§7Die §eServer§8-§eID §7lautet§8: §a"+id);
                if(s instanceof Player p) {
                    p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
                }
            } else {
                if(Zyneon.getZyneonServer().getConfig().getCFG().getBoolean("MySQL.enable")) {
                    s.sendMessage(Strings.prefix()+"§eDer Server ist nicht registriert§8!");
                    s.sendMessage(Strings.prefix()+"§7Die &etemporäre Server§8-§eID&7 lautet§8: §a"+id);
                    s.sendMessage(Strings.prefix()+"§7Um die ID fest zu speichern und zu registrieren, mache §f/register [Server-Name]§8!");
                    if(s instanceof Player p) {
                        p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
                    }
                } else {
                    s.sendMessage(Strings.prefix()+"§cDer Server kann zurzeit nicht registriert werden§8!");
                    s.sendMessage(Strings.prefix()+"§7Die &etemporäre Server§8-§eID&7 lautet§8: §a"+id);
                    s.sendMessage(Strings.prefix()+"§7Um den Server registrieren zu können§8,§7 verbinde ihn mit einer Datenbank§8!");
                    if(s instanceof Player p) {
                        p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                    }
                }
            }

        } else {
            s.sendMessage(Strings.noPerms());
            if(s instanceof Player p) {
                p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
            }
        }
        return false;
    }
}