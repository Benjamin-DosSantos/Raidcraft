package faction;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import main.Raidcraft;

public class Faction {
	Raidcraft plugin;

	public String factionLocation = "Factions";
	public String playerLocation = "Players";
	public String leaderLocation = "Leader";
	public String landLocation = "Land";

	ArrayList<String> masterList = new ArrayList<String>();
	
	public void createFaction(Player player, String factionName) {
		String factionPath = factionLocation + "." + factionName;
		if (!isInConfig(player, factionName) && getPlayerFaction(player) == null) {
			player.sendMessage(ChatColor.BLUE + "[Raidcraft] " + ChatColor.GOLD + "Faction created successfully!");
			masterList.add(factionName);
			Raidcraft.addItem(factionPath, masterList);
			Raidcraft.addItem(factionPath + "." + leaderLocation, player.getUniqueId().toString());
		} else {
			player.sendMessage(ChatColor.BLUE + "[Raidcraft] " + ChatColor.RED + "The faction was not created, please try again.");
		}
	}// End of createFaction method
	
	public boolean isInConfig(Player player, String factionName) {
		boolean isInConfig = false;
		if(getAllFactions().contains(factionName)){
			isInConfig = true;
		}// End of if the name is the same as the one the user wants to create
		return isInConfig;
	}// End of isInConfig method

	public ArrayList<String> getAllFactions(){
		ConfigurationSection factions = Raidcraft.config.getConfigurationSection(factionLocation);
		if(factions != null){
			for(String faction: factions.getKeys(false)){
				masterList.add(faction);
			}
		}
		return masterList;
	}
	
	public void removeFaction(Player player, String factionName) {
		
	}// End of removeFaction method

	public String getPlayerFaction(Player player) {
		ArrayList<String> factionList = getAllFactions();
		for(String faction: factionList){
			String factionPath = factionLocation + "." + faction;
			if(player.getUniqueId().toString().equals(Raidcraft.config.getString(factionPath + "." + leaderLocation).toString()) || getAllPlayersInFaction(faction).contains(player.getUniqueId().toString())){
				return faction;
			}
		}
		return null;
	}// End of getPlayerFaction

	public ArrayList<String> getAllPlayersInFaction(String factionName){
		ArrayList<String> masterList = new ArrayList<>();
		ConfigurationSection players = Raidcraft.config.getConfigurationSection(factionLocation + "." + factionName + "." + playerLocation);
		
		if(players != null){
			for(String playerUUID: players.getKeys(false)){
				masterList.add(playerUUID);
			}
		}
		return masterList;
	}
	
	public void addPlayerToFaction(Player player, String factionName) {
		ArrayList<String> playerList = getAllPlayersInFaction(factionName);
		
		if(getPlayerFaction(player) == null){
			playerList.add(player.getUniqueId().toString());
			Raidcraft.config.set(factionLocation + "." + factionName + "." + playerLocation, playerList);
			player.sendMessage("You have been added to a clan");
		}else{
			player.sendMessage("You are already a part of a faction");
		}
	}// End of addPlayerToFaction

	public void removePlayerFromFaction(Player player, String factionName) {

	}// End of removePlayerFromFaction
}// End of class
