package uk.co.kieraan.stickii;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import org.mcstats.MetricsLite;

import uk.co.kieraan.stickii.commands.*;
import uk.co.kieraan.stickii.listeners.*;

public class Stickii extends JavaPlugin {

    public HashMap<String, Integer> stickiiDelete;
    public HashMap<String, Integer> stickiiReplace;

    @Override
    public void onEnable() {
        final File check = new File(this.getDataFolder(), "config.yml");
        if (!check.exists()) {
            this.saveDefaultConfig();
            this.reloadConfig();
        }

        try {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException e) {
            // Stats no worky :(
        }

        this.stickiiDelete = new HashMap<String, Integer>();
        this.stickiiReplace = new HashMap<String, Integer>();
        this.getCommand("stickii").setExecutor(new StickiiCommand(this));
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        this.getLogger().info("Loaded " + this.getDescription().getName() + " v" + this.getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Disabled " + this.getDescription().getName() + " v" + this.getDescription().getVersion());
    }

    public void toggleStickiiDelete(Player player, String[] args, String p, String user) {
        int putID = 0;
        if (player.hasPermission("stickii.delete")) {
            if (stickiiDelete.containsKey(user)) {

                if (args.length < 2 || args[0].equalsIgnoreCase("replace")) {
                    stickiiDelete.remove(user);
                    player.sendMessage(ChatColor.AQUA + "Stickii Deleter Disabled");

                    if (this.getConfig().getString("consoleLog").equalsIgnoreCase("ALL") || this.getConfig().getString("consoleLog").equalsIgnoreCase("TOGGLE")) {
                        this.getLogger().info(p + " just disabled their Stickii Deleter");
                    }
                } else if (args[0].equalsIgnoreCase("delete")) {
                    try {
                        putID = Integer.parseInt(args[1]);
                    } catch(NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Unknown ID");
                        return;
                    }

                    stickiiDelete.remove(user);
                    stickiiDelete.put(user, putID);
                    player.sendMessage(ChatColor.AQUA + "Stickii Deleter mask changed to: " + putID);

                    if (this.getConfig().getString("consoleLog").equalsIgnoreCase("ALL") || this.getConfig().getString("consoleLog").equalsIgnoreCase("TOGGLE")) {
                        this.getLogger().info(p + " just changed their Stickii Deleter mask to ID: " + putID);
                    }
                }
            } else {
                if (stickiiReplace.containsKey(user)) {
                    toggleStickiiReplace(player, args, p, user);
                }
                if (args.length < 2) {
                    stickiiDelete.put(user, -1);
                } else {
                    try {
                        putID = Integer.parseInt(args[1]);
                    } catch(NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Unknown ID");
                        return;
                    }
                    stickiiDelete.put(user, putID);
                }

                player.sendMessage(ChatColor.AQUA + "Stickii Deleter Enabled");

                if (this.getConfig().getString("consoleLog").equalsIgnoreCase("ALL") || this.getConfig().getString("consoleLog").equalsIgnoreCase("TOGGLE")) {
                    this.getLogger().info(p + " just enabled their Stickii Deleter");
                }
            }
        }
    }

    public void toggleStickiiReplace(Player player, String[] args, String p, String user) {
        int putID = 0;
        if (player.hasPermission("stickii.replace")) {
            if (stickiiReplace.containsKey(user)) {
                if (args.length < 2 || args[0].equalsIgnoreCase("delete")) {
                    stickiiReplace.remove(user);
                    player.sendMessage(ChatColor.AQUA + "Stickii Replacer Disabled");

                    if (this.getConfig().getString("consoleLog").equalsIgnoreCase("ALL") || this.getConfig().getString("consoleLog").equalsIgnoreCase("TOGGLE")) {
                        this.getLogger().info(p + " just disabled their Stickii Replacer");
                    }
                } else if (args[0].equalsIgnoreCase("replace")) {
                    stickiiReplace.remove(user);
                    
                    try {
                        putID = Integer.parseInt(args[1]);
                    } catch(NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Unknown ID");
                        return;
                    }

                    Material replaceBlock = Material.getMaterial(putID);
                    if (!replaceBlock.isBlock()) {
                        player.sendMessage(ChatColor.RED + "That's not a placeable block, please try another ID.");
                        return;
                    }
                    stickiiReplace.put(user, putID);

                    player.sendMessage(ChatColor.AQUA + "Stickii Replacer block ID changed to: " + putID);

                    if (this.getConfig().getString("consoleLog").equalsIgnoreCase("ALL") || this.getConfig().getString("consoleLog").equalsIgnoreCase("TOGGLE")) {
                        this.getLogger().info(p + " just changed their Stickii Replacer block ID to: " + putID);
                    }
                }
            } else {
                if (stickiiDelete.containsKey(user)) {
                    toggleStickiiDelete(player, args, p, user);
                }

                try {
                    putID = Integer.parseInt(args[1]);
                } catch(NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Unknown ID");
                    return;
                }

                if (putID > 396) {
                    player.sendMessage(ChatColor.RED + "Unknown ID");
                    return;
                }

                Material replaceBlock = Material.getMaterial(putID);
                if (!replaceBlock.isBlock()) {
                    player.sendMessage(ChatColor.RED + "That's not a placeable block, please try another ID.");
                    return;
                }
                stickiiReplace.put(user, putID);
                player.sendMessage(ChatColor.AQUA + "Stickii Replacer Enabled");

                if (this.getConfig().getString("consoleLog").equalsIgnoreCase("ALL") || this.getConfig().getString("consoleLog").equalsIgnoreCase("TOGGLE")) {
                    this.getLogger().info(p + " just enabled their Stickii Replacer");
                }
            }
        }
    }

}
