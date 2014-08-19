/*
 * Copyright 2014 Ritz.
 */

package me.ritzdever.plots.plotfound;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlotFound {
    
    private final PlotFounds plugin;
    private final String playerName;
    private Email email;
    private Location l;
    
    public PlotFound(PlotFounds plugin, Player player, String email) {
        this(plugin, player.getName(), player.getLocation(), email);
    }
    
    public PlotFound(PlotFounds plugin, String playerName, Location loc, String email) {
        this.plugin = plugin;
        this.playerName = playerName;
        this.l = loc;
        this.email = new Email(email);
        
        if (plugin.getConfiguration().isDebugMode()) {
            System.out.println("[PlotFounds] [Debug] ========================");
            System.out.println("[PlotFounds] [Debug] Player: " + playerName);
            System.out.println("[PlotFounds] [Debug] Location: " + loc.toString());
            System.out.println("[PlotFounds] [Debug] Email: " + email);
            System.out.println("[PlotFounds] [Debug] Email2: " + this.email.toString());
        }
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public Location getLocation() {
        return l;
    }
    
    public Email getEmail() {
        return email;
    }
    
    public void setEmail(Email email) {
        this.email = email;
    }
    
    public void setEmail(String email) {
        this.email = new Email(email);
    }
    
    public void setLocation(Location loc) {
        this.l = loc;
    }
    
    public void save() {
        plugin.getConfiguration().save(this);
    }
    
    public void delete() {
        plugin.getConfiguration().delete(this);
    }
    
    public List<String> sendAsList() {
        List<String> list = new ArrayList();
        for (String string : plugin.getConfiguration().getListLayout()) {
            list.add(Utils.PREFIX + ChatColor.translateAlternateColorCodes('&', string)
                    .replace("%player%", playerName)
                    .replace("%email%", email.getEmail())
                    .replace("%loc%", toLocationString())
            );
        }
        
        return list;
    }
    
    protected String toLocationString() {
        return l.getWorld().getName() + " " + l.getX() + " " + l.getY() + " " + l.getZ();
    }
    @Override
    public String toString() {
        return "PlotFound{" +
                    "playerName=" + playerName + "," +
                    "location=" + l + "," +
                    "email=" + email +
                "}";
    }
    
    public class Email {
        
        private final String username, domain;
        
        public Email(String username, String domain) {
            this.username = username;
            this.domain = domain;
        }
        
        public Email(String email) {
            this.username = email.split("@")[0];
            this.domain = email.split("@")[1];
        }
        
        public String getUsername() { 
            return username; 
        }
        
        public String getDomain() { 
            return domain; 
        }
        
        public String getEmail() {
            return username + "@" + domain;
        }
        
        @Override
        public String toString() {
            return "Email{" +
                        "username=" + username + "," +
                        "domain=" + domain +
                    "}";
        }
    }
}
