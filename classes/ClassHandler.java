package classes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import main.Raidcraft;

public class ClassHandler {
	public void classifyPlayer(Raidcraft plugin, Player player) {
		System.out.println("This is called");
		List<String> class1 = getClass1Players();
		List<String> class2 = getClass2Players();
		
		String playerUUID = player.getUniqueId().toString();
		
		if(!(class1.contains(playerUUID)) && !(class2.contains(playerUUID))){
			player.sendMessage("You are not a part of a class");
		}
	}

	public List<String> getClass1Players() {
		List<String> class1 = new ArrayList<String>();

		ConfigurationSection class1Players = Raidcraft.config.getConfigurationSection("Class 1");

		if (class1Players != null) {
			for (String player : class1Players.getKeys(false)) {
				class1.add(player);
			}
		}

		return class1;
	}

	public List<String> getClass2Players() {
		List<String> class2 = new ArrayList<String>();

		ConfigurationSection class2Players = Raidcraft.config.getConfigurationSection("Class 2");

		if (class2Players != null) {
			for (String player : class2Players.getKeys(false)) {
				class2.add(player);
			}
		}

		return class2;
	}
}
