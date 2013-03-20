package uk.co.kieraan.stickii.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import uk.co.kieraan.stickii.MasterCommand;
import uk.co.kieraan.stickii.Stickii;

public class StickiiCommand extends MasterCommand {

    Stickii plugin;

    public StickiiCommand(Stickii stickii) {
        super(stickii);
        this.plugin = stickii;
    }

    @Override
    public void exec(CommandSender sender, String label, String[] args, Player player, boolean isPlayer) {
        if (isPlayer) {
            String p = player.getName();
            String user = p.toLowerCase();

            if (!player.hasPermission("stickii.use")) {
                player.sendMessage(ChatColor.RED + "Access Denied.");
                return;
            }

            if (args.length < 1) {
                player.sendMessage(ChatColor.GOLD + "Correct usage: /stickii <type> <block id>");
                player.sendMessage(ChatColor.GOLD + "Use /stickii help if you need help.");
                return;
            }

            if (args[0].equalsIgnoreCase("help")) {
                player.sendMessage(ChatColor.AQUA + "");
                player.sendMessage(ChatColor.GOLD + "Stickii Help:");
                player.sendMessage(ChatColor.AQUA + "");
                player.sendMessage(ChatColor.AQUA + "Correct usage: /stickii <type> <block id>");
                player.sendMessage(ChatColor.AQUA + "Types: delete replace");
                player.sendMessage(ChatColor.AQUA + "");
                player.sendMessage(ChatColor.AQUA + "Block ID " + ChatColor.UNDERLINE + "is not" + ChatColor.RESET + ChatColor.AQUA + " required for the delete type");
                player.sendMessage(ChatColor.AQUA + "Will act as a mask to stop you deleting other blocks.");
                player.sendMessage(ChatColor.AQUA + "");
                player.sendMessage(ChatColor.AQUA + "Block ID " + ChatColor.UNDERLINE + "is" + ChatColor.RESET + ChatColor.AQUA + " required for the replace type.");
                player.sendMessage(ChatColor.AQUA + "Will be the replacement block.");
                player.sendMessage(ChatColor.AQUA + "");
                return;
            }

            if (args[0].equalsIgnoreCase("delete")) {
                this.plugin.toggleStickiiDelete(player, args, p, user);
                giveStick(player, args, p, user);
                return;
            }

            if (args[0].equalsIgnoreCase("replace")) {
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Correct usage: /stickii replace <block id>");
                } else {
                    this.plugin.toggleStickiiReplace(player, args, p, user);
                }
                giveStick(player, args, p, user);
                return;
            }

            // Will only happen if the player tries an invalid command.
            player.sendMessage(ChatColor.GOLD + "Correct usage: /stickii <type> <block id>");
            player.sendMessage(ChatColor.GOLD + "Use /stickii help if you need help.");
        }
    }

    public void giveStick(Player player, String[] args, String p, String user) {
        ItemStack[] hotBarInvent = player.getInventory().getContents();
        ItemStack stickToGive = new ItemStack(Material.STICK);
        List<String> stickLore = new ArrayList<String>();
        stickLore.add("Bonk!");
        ItemMeta stickMeta = stickToGive.getItemMeta();
        stickMeta.setDisplayName("Stickii");
        stickMeta.setLore(stickLore);
        stickToGive.setItemMeta(stickMeta);
        boolean giveStick = true;

        for (int i = 0; i < hotBarInvent.length; i++) {
            if (hotBarInvent[i] != null && hotBarInvent[i].equals(stickToGive)) {
                giveStick = false;
            }
        }

        if (giveStick) {
            player.sendMessage(ChatColor.GOLD + "Here's your special stick!");
            player.getInventory().setItemInHand(stickToGive);
        }
    }

}
