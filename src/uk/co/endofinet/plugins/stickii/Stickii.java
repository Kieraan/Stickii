package uk.co.endofinet.plugins.stickii;

import java.util.HashSet;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.endofinet.plugins.stickii.commands.*;
import uk.co.endofinet.plugins.stickii.listeners.*;

public class Stickii extends JavaPlugin {
	
	public Logger log;
	public PluginDescriptionFile desc;
	
	public HashSet<String> stickiiUsers;
	
	@Override
	public void onEnable() {
		this.log = this.getLogger();
		this.desc = this.getDescription();
		
		this.stickiiUsers = new HashSet<String>();
		this.getCommand("stickii").setExecutor(new StickiiCommand(this));
		this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		
		this.log.info("Successfully loaded " + desc.getName() + " v" + desc.getVersion());
	}
	
	@Override
	public void onDisable() {
		this.log.info("Unloading " + desc.getName() + " v" + desc.getVersion());
	}
	

}
