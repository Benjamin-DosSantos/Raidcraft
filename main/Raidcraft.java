package main;

import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import commands.CommandHandler;

public class Raidcraft extends JavaPlugin {

	CommandHandler commandHandler = new CommandHandler();

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
		}
		return false;
	}// End of onCommand Method

	public static void addItem(String path, String info) {
		config.set(path, info);
	}

	public static void addItem(String path, ArrayList<String> info) {
		config.set(path, info);
	}
	
	public static void addItem(String path, int info) {
		config.set(path, info);
	}

	public static void addItem(String path, World info) {
		config.set(path, info);
	}
	
	public void onDisable() {
		saveConfig();
	}// End of onDisable Method
}// End of class
