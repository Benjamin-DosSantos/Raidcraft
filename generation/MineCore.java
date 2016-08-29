package generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import main.Raidcraft;

public class MineCore {

	List<String> masterList = new ArrayList<String>();
	
	String mineLocation = "Mines";
	
	boolean cornerCalledBefore = false;
	
	public void routeMine(Raidcraft plugin, Player player) {
		newMine(plugin, player);
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

	private void newMine(Raidcraft plugin, Player player) {
		int xSize = (4 * 13);
		int ySize = 4;
		int zSize = (4 * 13);
		
		Material[] materials = new Material[4];
		
		materials[0] = Material.DIAMOND_ORE;
		materials[1] = Material.GOLD_ORE;
		materials[2] = Material.IRON_ORE;
		materials[3] = Material.STONE;
		
		player.sendMessage("Command");
		
		Location playerLoc = player.getLocation();
		
		Random ran = new Random();
		
		for(int x = 0; x < xSize; x++){
			for(int y = 0; y < ySize; y++){
				for(int z = 0; z < zSize; z++){
					int diamondRoll = ran.nextInt(50);
					int goldRoll = ran.nextInt(100);
					int ironRoll = ran.nextInt(200);
					int stoneRoll = ran.nextInt(400);
					
					int winner = diamondRoll;
					int number = 0;
					
					if (goldRoll  > winner) { winner = goldRoll ; number = 1; }
					if (ironRoll  > winner) { winner = ironRoll ; number = 2; }
					if (stoneRoll > winner) { winner = stoneRoll; number = 3; }
					
					player.getWorld().getBlockAt(new Location(player.getWorld(), playerLoc.getX() + x, playerLoc.getY() + y, playerLoc.getZ() + z)).setType(materials[number]);
				}
			}
		}
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
