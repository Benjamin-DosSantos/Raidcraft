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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import commands.ClaimCommand;
import commands.CommandHandler;
import generation.MineCore;

public class Raidcraft extends JavaPlugin implements Listener {

	public static ChatColor titleColor = ChatColor.BLUE;	// Color for the name of the plugin when displayed in chat
	public static ChatColor sucessColor = ChatColor.GOLD;	// Color for successful commands 
	public static ChatColor failColor = ChatColor.RED;		// Color for failed commands 
	
	public static String pluginTitle = titleColor + "[Raidcraft] ";
	
	CommandHandler commandHandler = new CommandHandler();
	MineCore mineCore = new MineCore();
	ClaimCommand claims = new ClaimCommand();
	
	String previousOwner = "";
	
	public static FileConfiguration config;

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		config = this.getConfig();

		saveConfig();
	}// End of onEnable Method

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch (cmd.getName().toLowerCase()) {
		case "clan":
			commandHandler.routeCommand(this, sender, args);
			saveConfig();
			return true;
		case "mine":
			mineCore.routeMine((Player) sender, args);
			break;
		}// End of command finder
		return false;
	}// End of onCommand Method

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		String currentOwner = claims.getOwnerFaction(this, event.getPlayer(), event.getTo().getChunk());
		if (!event.getFrom().getChunk().equals(event.getTo().getChunk()) && !currentOwner.equalsIgnoreCase(previousOwner)) {
			event.getPlayer().sendMessage(ChatColor.GOLD + "<" + ChatColor.GREEN + currentOwner + ChatColor.GOLD + ">");
			previousOwner = currentOwner;
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
