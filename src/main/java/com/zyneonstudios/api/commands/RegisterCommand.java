package com.zyneonstudios.api.commands;

import com.zyneonstudios.api.Zyneon;
import com.zyneonstudios.api.server.Server;
import com.zyneonstudios.api.sql.MySQL;
import com.zyneonstudios.api.utils.Strings;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s.hasPermission("zyneon.leading.register")) {
            Server server = Zyneon.getZyneonServer();
            if(server.isRegistered()) {
                s.sendMessage("§cDer Server ist bereits registriert§8!");
                if(s instanceof Player p) {
                    p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
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
                            if(s instanceof Player p) {
                                p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                            }
                        }
                        server.getConfig().getCFG().set("Server.ID", ID);
                        server.getConfig().saveConfig();
                        server.getConfig().reloadConfig();
                        server.setRegistered(true);
                        s.sendMessage(Strings.prefix() + "§7Du hast den Server erfolgreich unter der ID §e" + ID + "§7 registriert§8!");
                    } else {
                        s.sendMessage("§cDer Server ist nicht mit einer Datenbank verbunden§8!");
                        if(s instanceof Player p) {
                            p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                        }
                    }
                } else {
                    s.sendMessage("§4Fehler: §c/register §c[name]");
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