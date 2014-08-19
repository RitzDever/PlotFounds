/*
 * Copyright 2014 Ritz.
 */

package me.ritzdever.plots.plotfound;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Utils {
        
    public final static String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "PlotFound" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET;
    static final String ADMIN_PERMISSION = "plotfounds.admin";
    
    public static boolean isEmailAddress(String email) {
        return email.contains("@") && (email.split("@").length >= 2);
    }
    
    public static boolean isAdmin(Player player) {
        if (player.isOp()) return true;
        return player.hasPermission(ADMIN_PERMISSION);
    }
    
    public static boolean isAdmin(CommandSender sender) {
        if (sender.isOp()) return true;
        return sender.hasPermission(ADMIN_PERMISSION);
    }
}
