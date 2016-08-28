package main;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import blockEvents.BlockPlaceHandler;
import blockEvents.StoneBrickHandler;
import commands.ClaimCommand;
import commands.CommandHandler;
import faction.Faction;
import generation.MineCore;

public class Raidcraft extends JavaPlugin implements Listener {

	public static ChatColor titleColor = ChatColor.BLUE;	// Color for the name of the plugin when displayed in chat
	public static ChatColor sucessColor = ChatColor.GOLD;	// Color for successful commands 
	public static ChatColor failColor = ChatColor.RED;		// Color for failed commands 
	
	public static String pluginTitle = titleColor + "[C-Raids] ";
	
	CommandHandler commandHandler = new CommandHandler();
	MineCore mineCore = new MineCore();
	ClaimCommand claims = new ClaimCommand();
	Faction factionCore = new Faction();
	
	public String previousOwner = "";
	
	public static FileConfiguration config;

	public void onEnable() {
		config = this.getConfig();
		
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new BlockPlaceHandler(this), this);
		getServer().getPluginManager().registerEvents(new StoneBrickHandler(this), this);
		
		saveConfig();
	}// End of onEnable Method

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch (cmd.getName().toLowerCase()) {
		case "clan":
			commandHandler.routeCommand(this, sender, args);
			saveConfig();
			return true;
		case "mine":
			mineCore.routeMine(this, (Player) sender, args);
			break;
		}// End of command finder
		return false;
	}// End of onCommand Method

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		String currentOwner = claims.getOwnerFaction(this, event.getTo().getChunk());
		if (!event.getFrom().getChunk().equals(event.getTo().getChunk()) && !currentOwner.equalsIgnoreCase(previousOwner)) {
			event.getPlayer().sendMessage(ChatColor.GOLD + "<" + ChatColor.GREEN + currentOwner + ChatColor.GOLD + ">");
			previousOwner = currentOwner;
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		if(!factionCore.playerHasInvite(this, event.getPlayer()).equals("")){
			event.getPlayer().sendMessage(pluginTitle + "You have an invite to join the clan " + factionCore.playerHasInvite(this, event.getPlayer()));
		}
	}
	
	public static void addItem(String path, String string) {
		config.set(path, string);
	}// End of addItem method (String, String)

	public static void addItem(String path, List<String> info) {
		config.set(path, info);
	}// End of addItem method (String, ArrayList<String>)
	
	public static void addItem(String path, int info) {
		config.set(path, info);
	}// End of addItem method (String, int)

	public static void addItem(String path, World info) {
		config.set(path, info);
	}// End of addItem method (String, World)
	
	public void onDisable() {
		saveConfig();
	}// End of onDisable Method
}// End of class
