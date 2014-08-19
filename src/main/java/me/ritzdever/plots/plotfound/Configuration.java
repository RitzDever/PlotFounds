/*
 * Copyright 2014 Ritz.
 */

package me.ritzdever.plots.plotfound;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;


public class Configuration {
    
    private final PlotFounds plugin;
    
    public Configuration(PlotFounds main) {
        this.plugin = main;
        
        plugin.saveDefaultConfig();
    }
    
    public FileConfiguration getConfig() {
        plugin.reloadConfig();
        return plugin.getConfig();
    }
    
    public void set(String path, Object value) {
        plugin.getConfig().set(path, value);
        plugin.saveConfig();
    }
    
    public boolean isDebugMode() {
        return plugin.getConfig().getBoolean("Settings.Debug-Mode");
    }
    
    public int getPlotsPerPage() {
        return plugin.getConfig().getInt("Settings.Plots-Per-Page");
    }
    
    public List<String> getListLayout() {
        return plugin.getConfig().getStringList("List-Layout");
    }
    public void save(PlotFound plot) {
        String BASE = "Plots." + plot.getPlayerName() + ".";
        String LocationWorld    = BASE + "Location.World";
        String LocationX        = BASE + "Location.X";
        String LocationY        = BASE + "Location.Y";
        String LocationZ        = BASE + "Location.Z";
        String LocationYaw      = BASE + "Location.Yaw";
        String LocationPitch    = BASE + "Location.Pitch";
        String Email            = BASE + "Email";
        
        set(LocationWorld, plot.getLocation().getWorld().getName());
        set(LocationX, plot.getLocation().getX());
        set(LocationY, plot.getLocation().getY());
        set(LocationZ, plot.getLocation().getZ());
        set(LocationYaw, plot.getLocation().getYaw());
        set(LocationPitch, plot.getLocation().getPitch());
        set(Email, plot.getEmail().getEmail());
    }
    
    public void delete(PlotFound plot) {
        String BASE = "Plots." + plot.getPlayerName() + ".";
        String LocationWorld    = BASE + "Location.World";
        String LocationX        = BASE + "Location.X";
        String LocationY        = BASE + "Location.Y";
        String LocationZ        = BASE + "Location.Z";
        String LocationYaw      = BASE + "Location.Yaw";
        String LocationPitch    = BASE + "Location.Pitch";
        String Email            = BASE + "Email";
        
        set(LocationWorld, null);
        set(LocationX, null);
        set(LocationY, null);
        set(LocationZ, null);
        set(LocationYaw, null);
        set(LocationPitch, null);
        set(Email, null);
        set(BASE, null);
    }
    
    public List<PlotFound> load() {
        List<PlotFound> list = new ArrayList();
        
        for (String playerName : plugin.getConfig().getConfigurationSection("Plots").getKeys(false)) {
            String CONFIG_BASE = "Plots." + playerName + ".";
            String CONFIG_WORLD = CONFIG_BASE + "Location.World";
            String CONFIG_X = CONFIG_BASE + "Location.X";
            String CONFIG_Y = CONFIG_BASE + "Location.Y";
            String CONFIG_Z = CONFIG_BASE + "Location.Z";
            String CONFIG_YAW      = CONFIG_BASE + "Location.Yaw";
            String CONFIG_PITCH    = CONFIG_BASE + "Location.Pitch";
            String CONFIG_EMAIL = CONFIG_BASE + "Email";
            
            World world = Bukkit.getWorld(plugin.getConfig().getString(CONFIG_WORLD));
            double x = plugin.getConfig().getDouble(CONFIG_X);
            double y = plugin.getConfig().getDouble(CONFIG_Y);
            double z = plugin.getConfig().getDouble(CONFIG_Z);
            float yaw = Float.valueOf(plugin.getConfig().getString(CONFIG_YAW));
            float pitch = Float.valueOf(plugin.getConfig().getString(CONFIG_PITCH));
            String email = plugin.getConfig().getString(CONFIG_EMAIL);
            
            list.add(
                    new PlotFound(
                            plugin, 
                            playerName, 
                            new Location(
                                    world, 
                                    x, 
                                    y, 
                                    z, 
                                    yaw, 
                                    pitch), 
                            email)
            );
        }
        
        return list;
    }
}
