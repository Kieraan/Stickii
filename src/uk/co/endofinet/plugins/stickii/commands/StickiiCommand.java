package uk.co.endofinet.plugins.stickii.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.endofinet.plugins.stickii.MasterCommand;
import uk.co.endofinet.plugins.stickii.Stickii;

public class StickiiCommand extends MasterCommand {
	
	Stickii plugin;

	public StickiiCommand(Stickii stickii) {
		super(stickii);
		this.plugin = stickii;
	}

	@Override
	public void exec(CommandSender sender, String label, String[] args, Player player, boolean isPlayer) {
		if (isPlayer) {
            String user = player.getName().toLowerCase();
	        if(player.hasPermission("stickii.use") || player.isOp()) {
	        	if (this.plugin.stickiiUsers.contains(user)) {
	                this.plugin.stickiiUsers.remove(user);
	                player.sendMessage(ChatColor.AQUA + this.plugin.getConfig().getString("messages.disable"));
	                this.plugin.log.info(this.plugin.getConfig().getString("messages.disable"));
	            } else {
	            	this.plugin.stickiiUsers.add(user);
	                player.sendMessage(ChatColor.AQUA + this.plugin.getConfig().getString("messages.enable"));
	                this.plugin.log.info(this.plugin.getConfig().getString("messages.enable"));
	                if(this.plugin.getConfig().getBoolean("messages.showrange")) {
	                	player.sendMessage(ChatColor.AQUA + "Current range: " + this.plugin.getConfig().getInt("range") + ".");
	                } 
	            }
	        }  
        }
	}
	

}
