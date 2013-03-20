package uk.co.kieraan.stickii.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import uk.co.kieraan.stickii.Stickii;

public class PlayerInteractListener implements Listener {

    Stickii plugin;

    public PlayerInteractListener(Stickii stickii) {
        this.plugin = stickii;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String p = player.getName();
        String user = p.toLowerCase();
        ItemStack itemInHand = player.getItemInHand();

        if (itemInHand.getType().equals(Material.STICK)) {
            final Block target = player.getTargetBlock(null, 50);
            boolean mask = false;
            int replaceBlockID = 1;

            if (this.plugin.stickiiDelete.containsKey(user)) {
                if (!player.hasPermission("stickii.use.delete")) {
                    this.plugin.stickiiDelete.remove(user);
                    return;
                }
                if (!this.plugin.stickiiDelete.get(user).equals(-1) && this.plugin.stickiiDelete.get(user) != null) {
                    mask = true;
                }
                if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)) {
                    if (target != null && !target.getType().equals(Material.AIR)) {
                        event.setCancelled(true);
                        if (mask) {
                            if (this.plugin.stickiiDelete.get(user).equals(target.getTypeId())) {
                                target.breakNaturally();
                            }
                        } else {
                            target.breakNaturally();
                        }
                    }
                }

                if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    if (target != null && !target.getType().equals(Material.AIR)) {
                        event.setCancelled(true);
                        if (mask) {
                            if (this.plugin.stickiiDelete.get(user).equals(target.getTypeId())) {
                                target.setTypeId(0);
                            }
                        } else {
                            target.setTypeId(0);
                        }
                    }
                }
                return;
            }

            if (this.plugin.stickiiReplace.containsKey(user)) {
                if (!player.hasPermission("stickii.use.replace")) {
                    this.plugin.stickiiReplace.remove(user);
                    return;
                }
                if (this.plugin.stickiiReplace.get(user) != null) {
                    replaceBlockID = this.plugin.stickiiReplace.get(user);
                }

                Material replaceBlock = Material.getMaterial(replaceBlockID);
                if (!replaceBlock.isBlock()) {
                    player.sendMessage(ChatColor.RED + "The block ID you're attempting to use is not a block!");
                    return;
                }

                if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)) {
                    if (target != null && !target.getType().equals(Material.AIR)) {
                        event.setCancelled(true);
                        target.breakNaturally();
                        target.setTypeId(replaceBlockID);
                    }
                }

                if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    if (target != null && !target.getType().equals(Material.AIR)) {
                        event.setCancelled(true);
                        target.setTypeId(replaceBlockID);
                    }
                }
                return;
            }
        }

    }

}
