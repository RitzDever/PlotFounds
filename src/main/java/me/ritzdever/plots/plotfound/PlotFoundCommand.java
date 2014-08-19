/*
 * Copyright 2014 Ritz.
 */

package me.ritzdever.plots.plotfound;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class PlotFoundCommand implements CommandExecutor {

    private final PlotFounds plugin;
    
    public PlotFoundCommand(PlotFounds plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 0) {
            sendHelp(sender);
            return true;
        }
        
        if (args[0] == null) {
            sendHelp(sender);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "list":
                if (Utils.isAdmin(sender)) {
                    if (args.length >= 2) {
                        try {
                            int page = Integer.parseInt(args[1]);
                            List<PlotFound> list = plugin.getPlotListPage(page);
                            sendPage(sender, list);
                            return true;
                        } catch (NumberFormatException e) {
                            sender.sendMessage(Utils.PREFIX + ChatColor.DARK_RED + "Page # MUST be a number!");
                            return true;
                        }
                    }
                    List<PlotFound> list2 = plugin.getPlotListPage(1);
                    sendPage(sender, list2);
                    return true;
                } else {
                    sender.sendMessage(Utils.PREFIX + ChatColor.DARK_RED + "You do not have permission to do that!");
                    return true;
                }
            default:
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (Utils.isEmailAddress(args[0])) {
                        plugin.plotFound(player, args[0]);
                        sender.sendMessage(Utils.PREFIX + ChatColor.GREEN + "We have been notified that you found your plot. We will email you soon if this is your plot!");
                        return true;
                    } else if (!Utils.isEmailAddress(args[0])) {
                        if (Utils.isAdmin(player)) {
                            PlotFound pf = plugin.getPlotFoundForPlayer(args[0]);
                            if (pf != null) {
                                player.teleport(pf.getLocation());
                                sender.sendMessage(Utils.PREFIX + ChatColor.GREEN + "You have been teleported to the location of " + args[0] + "'s plot!");
                                for (String message : pf.sendAsList()) {
                                    sender.sendMessage(message);
                                }
                                return true;
                            } else {
                                sender.sendMessage(Utils.PREFIX + ChatColor.DARK_RED + "That player has not yet found their plot!");
                                return true;
                            }
                        } else {
                            sender.sendMessage(Utils.PREFIX + ChatColor.DARK_RED + "You do not have permission to do that!");
                            return true;
                        }
                    } else {
                        sender.sendMessage(Utils.PREFIX + ChatColor.DARK_RED + "Something went wrong! Tell an admin to check the console");
                        System.out.println("[PlotFound] [Error] ================================");
                        System.out.println("[PlotFound] [Error] Please send this to Ritz!!!!!!");
                        System.out.println("[PlotFound] [Error] ================================");
                        System.out.println("[PlotFound] [Error] Player: " + player);
                        for (int i = 0; i <= args.length; i++) {
                            System.out.println("[PlotFound] [Error] Arg[" + i + "] == " + args[i]);
                        }
                        return true;
                    }
                } else {
                    sender.sendMessage(Utils.PREFIX + ChatColor.RED + "Only players can do that!");
                    return true;
                }
        }
    }
    
    public void sendHelp(CommandSender sender) {
        sender.sendMessage(Utils.PREFIX + ChatColor.GREEN + "PlotFound Help");
        sender.sendMessage(Utils.PREFIX + ChatColor.RED + "/plotfound <your@email>");
        if (Utils.isAdmin(sender)) {
            sender.sendMessage(Utils.PREFIX + ChatColor.RED + "/plotfound <MemberName>");
            sender.sendMessage(Utils.PREFIX + ChatColor.RED + "/plotfound list [page #]");
        }
    }
    
    public void sendPage(CommandSender sender, List<PlotFound> list) {
        for (PlotFound pf : list) {
            for (String message : pf.sendAsList()) {
                sender.sendMessage(message);
            }
        }
    }
}
