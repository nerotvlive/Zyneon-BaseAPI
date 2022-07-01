package com.zyneonstudios.api.waterfall;

import com.zyneonstudios.api.waterfall.bungeebase.BungeeBase;
import net.md_5.bungee.api.plugin.Plugin;

public class Zyneon extends Plugin {

    private static Zyneon instance;
    public static Zyneon getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        BungeeBase.onLoad();
    }

    @Override
    public void onEnable() {
        instance = this;
        BungeeBase.onEnable();
    }

    @Override
    public void onDisable() {
        instance = null;
        BungeeBase.onDisable();
    }
}