package classes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import main.Raidcraft;

public class ClassHandler {
	public void classifyPlayer(Raidcraft plugin, Player player) {
		String playerUUID = player.getUniqueId().toString();
		
		if(!isPlayerInClass(playerUUID)){
			player.sendMessage(plugin.pluginTitle + plugin.failColor
					+ "You are not a part of a class. To learn more about a class use the command /class 1, /class 2, or /class 3");
		}
		
	}

	public void commandHandler(Raidcraft raidcraft, Player sender, String[] args) {
		switch (args[0]) {
		case "1":
			class1Info(raidcraft, sender, args);
			break;
		case "2":
			class2Info(raidcraft, sender, args);
			break;
		case "3":
			class3Info(raidcraft, sender, args);
			break;
		default:
			sender.sendMessage(raidcraft.pluginTitle + raidcraft.failColor + "There is no class " + args[0]);
			break;
		}
	}

	public List<String> getAllClassMembers(int classNumber){
		List<String> classList = new ArrayList<String>();
		ConfigurationSection classPlayers = Raidcraft.config.getConfigurationSection("Class " + classNumber);

		if (classPlayers != null) {
			for (String player : classPlayers.getKeys(false)) {
				classList.add(player);
			}
		}
		return classList;
	}
	
	public boolean isPlayerInClass(String playerUUID){
		boolean isPartOfClass = false;
		
		for(int i = 1; i <= 3; i++){
			if (getAllClassMembers(i).contains(playerUUID)) {
				for(String player: getAllClassMembers(i)){
					System.out.println(player);
				}
				isPartOfClass = true;
			}
		}
		return isPartOfClass;
	}
	
	private void class1Info(Raidcraft raidcraft, Player sender, String[] args) {
		if (args.length == 2 && args[1].equalsIgnoreCase("join") && !isPlayerInClass(sender.getUniqueId().toString())) {
			List<String> classMembers = getAllClassMembers(1);
			classMembers.add(sender.getUniqueId().toString());
			raidcraft.addItem("Class 1", classMembers);
			raidcraft.saveConfig();
			sender.sendMessage(raidcraft.pluginTitle + raidcraft.sucessColor + "You have been added to class 1");
		}else{
			sender.sendMessage(raidcraft.pluginTitle + raidcraft.failColor + "This is not a class command, try" + ChatColor.BOLD + "/class 1, 2, or 3" + ChatColor.RESET + raidcraft.failColor + "to learn more about each class");
		}
	}

	private void class2Info(Raidcraft raidcraft, Player sender, String[] args) {
		if (args.length == 2 && args[1].equalsIgnoreCase("join") && !isPlayerInClass(sender.getUniqueId().toString())) {
			List<String> classMembers = getAllClassMembers(2);
			classMembers.add(sender.getUniqueId().toString());
			raidcraft.addItem("Class 2", classMembers);
			raidcraft.saveConfig();
			sender.sendMessage(raidcraft.pluginTitle + raidcraft.sucessColor + "You have been added to class 2");
		}else{
			sender.sendMessage(raidcraft.pluginTitle + raidcraft.failColor + "This is not a class command, try" + ChatColor.BOLD + "/class 1, 2, or 3" + ChatColor.RESET + raidcraft.failColor + "to learn more about each class");
		}
	}

	private void class3Info(Raidcraft raidcraft, Player sender, String[] args) {
		if (args.length == 2 && args[1].equalsIgnoreCase("join") && !isPlayerInClass(sender.getUniqueId().toString())) {
			List<String> classMembers = getAllClassMembers(3);
			classMembers.add(sender.getUniqueId().toString());
			raidcraft.addItem("Class 3", classMembers);
			raidcraft.saveConfig();
			sender.sendMessage(raidcraft.pluginTitle + raidcraft.sucessColor + "You have been added to class 3");
		}else{
			sender.sendMessage(raidcraft.pluginTitle + raidcraft.failColor + "This is not a class command, try" + ChatColor.BOLD + "/class 1, 2, or 3" + ChatColor.RESET + raidcraft.failColor + "to learn more about each class");
		}
	}
}
