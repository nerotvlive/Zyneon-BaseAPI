package com.zyneonstudios.api.waterfall.bungeebase.commands;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.api.waterfall.bungeebase.api.API;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class Maintenance extends Command {

    public Maintenance() {
        super("bungeemaintenance",null,"bmain");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("zyneon.team")) {
            API.toggleMaintenance();
            API.sendMessage(s,"§7Der §eMaintenance-Mode steht nun auf§8: §a"+API.maintenance);
        } else {
            API.sendErrorMessage(s, Strings.noPerms());
        }
    }
}
