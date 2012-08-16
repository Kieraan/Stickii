package uk.co.endofinet.plugins.stickii.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import uk.co.endofinet.plugins.stickii.Stickii;

public class PlayerInteractListener implements Listener {
	
	Stickii plugin;
	
	public PlayerInteractListener(Stickii stickii) {
		this.plugin = stickii;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		final String playername = player.getName().toLowerCase();
        final ItemStack itemInHand = player.getItemInHand();

        if ((itemInHand.getTypeId() == this.plugin.getConfig().getInt("item"))) {
        	final Block target = player.getTargetBlock(null, this.plugin.getConfig().getInt("range"));
        	if(this.plugin.stickiiUsers.contains(playername)) {
        		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)) {
		        	if (target != null) {
		               	event.setCancelled(true);
						target.breakNaturally();
		            }
        		}
        			
		        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
		            if (target != null) {
		                event.setCancelled(true);
		              	target.setTypeId(0);
		            }
		        }
        	}
        }

	}

}
