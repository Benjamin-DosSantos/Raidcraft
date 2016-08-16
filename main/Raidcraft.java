package main;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import commands.CommandHandler;
import generation.MineCore;

public class Raidcraft extends JavaPlugin {

	public ChatColor titleColor = ChatColor.BLUE;	// Color for the name of the plugin when displayed in chat
	public ChatColor sucessColor = ChatColor.GOLD;	// Color for successful commands 
	public ChatColor failColor = ChatColor.RED;		// Color for failed commands 
	
	public String pluginTitle = titleColor + "[Raidcraft] ";
	
	CommandHandler commandHandler = new CommandHandler();
	MineCore mineCore = new MineCore();
	
	public static FileConfiguration config;

	public void onEnable() {
		config = this.getConfig();

		saveConfig();
	}// End of onEnable Method

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch (cmd.getName().toLowerCase()) {
		case "clan":
			commandHandler.routeCommand(sender, args);
			saveConfig();
			return true;
		case "mine":
			mineCore.routeMine((Player) sender, args);
			break;
		}// End of command finder
		return false;
	}// End of onCommand Method

	public static void addItem(String path, String info) {
		config.set(path, info);
	}// End of addItem method (String, String)

	public static void addItem(String path, ArrayList<String> info) {
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
