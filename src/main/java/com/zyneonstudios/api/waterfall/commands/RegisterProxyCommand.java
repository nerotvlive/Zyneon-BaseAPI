package com.zyneonstudios.api.waterfall.commands;

import com.zyneonstudios.api.waterfall.Zyneon;
import com.zyneonstudios.api.waterfall.server.Server;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.api.utils.sql.MySQL;
import com.zyneonstudios.api.waterfall.utils.communication.Communicate;
import com.zyneonstudios.api.waterfall.utils.user.Sound;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterProxyCommand extends Command {

    public RegisterProxyCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("zyneon.leading.register")) {
            Server server = Zyneon.getZyneonServer();
            if(server.isRegistered()) {
                s.sendMessage("§cDer Server ist bereits registriert§8!");
                if(s instanceof ProxiedPlayer p) {
                    Communicate.sendSound(p, Sound.BLOCK_ANVIL_BREAK.toString());
                }
            } else {
                if(args.length == 1) {
                    String name = args[0];
                    int ID = server.getServerID();
                    if(server.getConfig().getCFG().getBoolean("MySQL.enable")) {
                        MySQL sql = server.getSQL();
                        try {
                            sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS serverlist (ID INT,NAME VARCHAR(32))").executeUpdate();
                            PreparedStatement ps = sql.getConnection().prepareStatement("INSERT INTO serverlist (ID,NAME) VALUES (?,?)");
                            ps.setInt(1,ID);
                            ps.setString(2,name);
                            ps.executeUpdate();
                        } catch (SQLException e) {
                            s.sendMessage("§cDer Server ist nicht mit einer Datenbank verbunden§8!");
                            if(s instanceof ProxiedPlayer p) {
                                Communicate.sendSound(p, Sound.BLOCK_ANVIL_BREAK.toString());
                            }
                        }
                        server.getConfig().getCFG().set("Server.ID", ID);
                        server.getConfig().saveConfig();
                        server.getConfig().reloadConfig();
                        server.setRegistered(true);
                        s.sendMessage(Strings.prefix() + "§7Du hast den Server erfolgreich unter der ID §e" + ID + "§7 registriert§8!");
                        if(s instanceof ProxiedPlayer p) {
                            Communicate.sendSound(p, Sound.ENTITY_CHICKEN_EGG.toString());
                        }
                    } else {
                        s.sendMessage("§cDer Server ist nicht mit einer Datenbank verbunden§8!");
                        if(s instanceof ProxiedPlayer p) {
                            Communicate.sendSound(p, Sound.BLOCK_ANVIL_BREAK.toString());
                        }
                    }
                } else {
                    s.sendMessage("§4Fehler: §c/register §c[name]");
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
