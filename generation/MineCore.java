package generation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import main.Raidcraft;

public class MineCore {

	List<String> masterList = new ArrayList<String>();
	
	String mineLocation = "Mines";
	
	boolean cornerCalledBefore = false;
	
	public void routeMine(Raidcraft plugin, Player player, String[] commandArgs) {
		switch (commandArgs[0].toLowerCase()) {
		case "newMine":
			newMine(plugin, player, commandArgs[1]);
			break;
		case "list":
			listMines(plugin, player);
			break;
		case "corner":
			corner(plugin, player, commandArgs[1]);
		}
	}

	@SuppressWarnings("static-access")
	private void corner(Raidcraft plugin, Player player, String mineName) {
		String locationPath = mineLocation + "." + mineName;
		
		if(!cornerCalledBefore){
			plugin.addItem(locationPath + "." + "Corner_1" + "." + "XPos", player.getLocation().getBlockX());
			plugin.addItem(locationPath + "." + "Corner_1" + "." + "YPos", player.getLocation().getBlockY());
			plugin.addItem(locationPath + "." + "Corner_1" + "." + "ZPos", player.getLocation().getBlockZ());
		
			player.sendMessage(plugin.pluginTitle + plugin.sucessColor + "Your current location will be used for the mines first corner");
		}else{
			plugin.addItem(locationPath + "." + "Corner_2" + "." + "XPos", player.getLocation().getBlockX());
			plugin.addItem(locationPath + "." + "Corner_2" + "." + "YPos", player.getLocation().getBlockY());
			plugin.addItem(locationPath + "." + "Corner_2" + "." + "ZPos", player.getLocation().getBlockZ());
			
			player.sendMessage(plugin.pluginTitle + plugin.sucessColor + "Your current location will be used for the mines second corner");
			cornerCalledBefore = false;
		}
	}

	@SuppressWarnings("static-access")
	private void newMine(Raidcraft plugin, Player player, String mineName) {
		plugin.addItem(mineLocation + "." + mineName, " ");
		player.sendMessage(plugin.pluginTitle + plugin.sucessColor + "Mine created sucessfuly");
	}
	
	public List<String> getAllMines(String mineLocation){
		ConfigurationSection factions = Raidcraft.config.getConfigurationSection(mineLocation);
		if (factions != null) {
			for (String faction : factions.getKeys(false)) {
				masterList.add(faction);
			}
		}
		return masterList;
	}
	
	public int getNumberOfMines(){
		return getAllMines(mineLocation).size();
	}
	
	@SuppressWarnings("static-access")
	public void listMines(Raidcraft plugin, Player player){
		int numberOfMines = getNumberOfMines();
		
		player.sendMessage(ChatColor.RED + "==========" + plugin.sucessColor + "[There are " + numberOfMines + " Clans]" + ChatColor.RED + "==========");
		
		List<String> mines = getAllMines(mineLocation);
		
		for(int i = 0; i < mines.size() / 2; i++){		// For some reason the list is twice as long as it actually is(?)
			player.sendMessage(plugin.sucessColor + mines.get(i) + "\n");
		}
		
		player.sendMessage(ChatColor.RED + "==========" + plugin.sucessColor + "[There are " + numberOfMines + " Clans]" + ChatColor.RED + "==========");
	}
}// End of class
