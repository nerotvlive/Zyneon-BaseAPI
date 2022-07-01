package com.zyneonstudios.api.waterfall.bungeebase.commands;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.api.waterfall.bungeebase.api.API;
import com.zyneonstudios.api.waterfall.bungeebase.utils.Communicate;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BSRL extends Command {

    public BSRL() {
        super("bsrl",null,"bungeestop");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("zyneon.leading")) {
            API.sendMessage(s,"§7Du hast das Stopprotokoll gestartet§8!");
            Communicate.sendStop();
        } else {
            API.sendErrorMessage(s, Strings.noPerms());
        }
    }
}