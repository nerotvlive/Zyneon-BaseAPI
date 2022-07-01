package com.zyneonstudios.api.waterfall.bungeebase.api;

import com.zyneonstudios.api.waterfall.utils.communication.Communicate;
import com.zyneonstudios.api.waterfall.utils.user.Sound;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerAPI {

    public static void playNewSound(ProxiedPlayer player, Sound sound) {
        Communicate.sendSound(player, sound.toString());
    }
}