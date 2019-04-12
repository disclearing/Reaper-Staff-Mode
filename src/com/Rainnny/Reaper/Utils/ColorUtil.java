package com.Rainnny.Reaper.Utils;

import org.bukkit.ChatColor;

public class ColorUtil
{
    public static String color(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
