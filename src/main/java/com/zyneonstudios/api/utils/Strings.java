package com.zyneonstudios.api.utils;

public class Strings {

    private static String prefixWord = "Zyneon";

    public static String prefix(String prefixWord) {
        return "§9"+prefixWord+"§8 » §7";
    }

    public static String prefix() {
        return "§9"+prefixWord+" §8» §7";
    }

    public static String playerNotFound(String playerName) {
        return "§cDer/die Spieler/in §4"+playerName+"§c wurde nicht gefunden§8!";
    }

    public static String playerNotFound() {
        return "§cDer/die Spieler/in wurde nicht gefunden§8!";
    }

    public static String noPerms() {
        return "§cDas darfst du nicht§8!";
    }

    public static String notEnoughPoints() {
        return "§cDazu hast du nicht genug Punkte§8!";
    }

    public static String notEnoughMoney() {
        return "§cDazu hast du nicht genug Geld§8!";
    }

    public static String needPlayer() {
        return "§cDazu §4musst§c du ein Spieler sein§8!";
    }

    public static void setPrefixWord(String word) {
        prefixWord = word;
    }
}