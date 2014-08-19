/*
 * Copyright 2014 Ritz.
 */
package me.ritzdever.plots.plotfound;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class PlotFounds extends JavaPlugin {
    
    private Configuration config;
    
    public List<PlotFound> plotsFound;
    
    @Override
    public void onEnable() {
        this.config = new Configuration(this);
        
        plotsFound = config.load();
        
        getCommand("plotfound").setExecutor(new PlotFoundCommand(this));
    }
    
    @Override
    public void onDisable() {
        for (PlotFound pf : getPlotsFound()) {
            pf.save();
        }
    }
    
    public Configuration getConfiguration() {
        return config;
    }
    
    public List<PlotFound> getPlotsFound() {
        if (plotsFound == null) {
            this.plotsFound = config.load();
        }
        
        return plotsFound;
    }
    
    public PlotFound getPlotFoundForPlayer(String player) {
        for (PlotFound pf : getPlotsFound()) {
            if (pf.getPlayerName().equalsIgnoreCase(player)) {
                return pf;
            }
        }
        return null;
    }
    
    public List<PlotFound> getPlotListPage(int page) {
        int amount = page * getConfiguration().getPlotsPerPage();
        List<PlotFound> list = new ArrayList();
        
        while (amount >= 0) {
            if (amount == getConfiguration().getPlotsPerPage()) {
                break;
            }
            if (getConfiguration().isDebugMode()) {
                System.out.print("[PlotFound] [Debug] Gathering plot indexes for page '" + page + "'. Checking index '" + amount + "'");
            }
            try {
                PlotFound pf = getPlotsFound().get(amount);
                if (pf != null) {
                    list.add(pf);
                }
            } catch (Exception e) {}
            amount--;
            

        }
        
        return list;
    }
    
    public void plotFound(Player player, String email) {
        if (getPlotFoundForPlayer(player.getName()) != null) {
            PlotFound edit = getPlotFoundForPlayer(player.getName());
            edit.setLocation(player.getLocation());
            edit.setEmail(email);
        } else {
            plotsFound.add(new PlotFound(this, player, email));
        }
    }
}
