package com.zyneonstudios.api.waterfall.bungeebase.api;

import com.zyneonstudios.api.waterfall.bungeebase.utils.Communicate;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerAPI {

    public static void playNewSound(ProxiedPlayer player,NewSound newSound) {
        Communicate.sendSound(player,newSound.toString());
    }
}