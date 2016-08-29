package faction;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import main.Raidcraft;

public class Faction {

	public String factionLocation = "Factions";
	public String leaderLocation = "Leader";
	public String playerLocation = "Players";
	public String modLocation = "Mods";
	public String landLocation = "Land";
	public String homeLocation = "Home";
	public String inviteLocation = "Invites";
	public String beaconLocation = "Beacons";
	public String allowedClaimsLocation = "TotalClaims";
	public String currentClaimsLocation = "CurrentClaims";
	
	List<String> masterList = new ArrayList<String>();

	public void createFaction(Player player, String factionName) {
		String factionPath = factionLocation + "." + factionName;
		if (!isInConfig(player, factionName) && getPlayerFaction(player) == null) {
			player.sendMessage(Raidcraft.pluginTitle + Raidcraft.sucessColor + "Faction created successfully!");
			masterList.add(factionName);
			Raidcraft.addItem(factionPath, masterList);
			Raidcraft.addItem(factionPath + "." + leaderLocation, player.getUniqueId().toString());
			Raidcraft.addItem(factionPath + "." + allowedClaimsLocation, 1);
			Raidcraft.addItem(factionPath + "." + currentClaimsLocation, 0);
		} else {
			player.sendMessage(
					Raidcraft.pluginTitle + Raidcraft.failColor + "The faction was not created, please try again.");
		}
	}// End of createFaction method
	
	public void disbandFaction(Player player) {
		String playerFaction = getPlayerFaction(player);
		String factionPath = factionLocation + "." + playerFaction + "." + leaderLocation;
		String factionLeader = Raidcraft.config.getString(factionPath); 
		String playerUUID = player.getUniqueId().toString();
		List<String> factions = getAllFactions();
		if (factions.contains(playerFaction) && factionLeader.equals(playerUUID)) {
			factions.remove(playerFaction);
			Raidcraft.addItem(factionLocation, factions);
			player.sendMessage(Raidcraft.pluginTitle + Raidcraft.sucessColor + "Faction removed successfully!");
		} else {
			player.sendMessage(
					Raidcraft.pluginTitle + Raidcraft.failColor + "The faction was not removed, please try again.");
		}
	}// End of createFaction method

	public boolean isInConfig(Player player, String factionName) {
		boolean isInConfig = false;
		if (getAllFactions().contains(factionName)) {
			isInConfig = true;
		} // End of if the name is the same as the one the user wants to create
		return isInConfig;
	}// End of isInConfig method

	public List<String> getAllFactions() {
		ConfigurationSection factions = Raidcraft.config.getConfigurationSection(factionLocation);
		if (factions != null) {
			for (String faction : factions.getKeys(false)) {
				masterList.add(faction);
			}
		}
		return masterList;
	}
	
	public int getNumberOfFactions(){
		return getAllFactions().size();
	}

	public String getFactionLeader(String factionName) {
		return Raidcraft.config.getString(factionLocation + "." + factionName + "." + leaderLocation);
	}

	public String getPlayerFaction(Player player) {
		List<String> factionList = getAllFactions();
		if(player.isOnline()){
			for (String faction : factionList) {
				String factionPath = factionLocation + "." + faction;
				if (player.getUniqueId().toString()
						.equals(Raidcraft.config.getString(factionPath + "." + leaderLocation).toString())
						|| getAllPlayersInFaction(faction).contains(player.getUniqueId().toString())) {
					return faction;
				}
			}
		}
		return null;
	}// End of getPlayerFaction

	public ArrayList<String> getAllPlayersInFaction(String factionName) {
		ArrayList<String> masterList = new ArrayList<>();
		ConfigurationSection players = Raidcraft.config
				.getConfigurationSection(factionLocation + "." + factionName + "." + playerLocation);

		if (players != null) {
			for (String playerUUID : players.getKeys(false)) {
				masterList.add(playerUUID);
			}
		}
		return masterList;
	}

	public ArrayList<String> getAllModsInFaction(String factionName) {
		ArrayList<String> masterList = new ArrayList<>();
		ConfigurationSection players = Raidcraft.config
				.getConfigurationSection(factionLocation + "." + factionName + "." + modLocation);

		if (players != null) {
			for (String playerUUID : players.getKeys(false)) {
				masterList.add(playerUUID);
			}
		}
		return masterList;
	}

	public void addPlayerToFaction(Player player, String factionName) {
		ArrayList<String> playerList = getAllPlayersInFaction(factionName);

		if (getPlayerFaction(player) == null) {
			playerList.add(player.getUniqueId().toString());
			Raidcraft.config.set(factionLocation + "." + factionName + "." + playerLocation, playerList);
			addTotalClaims(factionName, 1);
			player.sendMessage("You have been added to a clan");
		} else {
			player.sendMessage("You are already a part of a faction");
		}
	}// End of addPlayerToFaction

	public void invitePlayerToFaction(Player player, Player newPlayer) {
		String senderFaction = getPlayerFaction(player);

		if (getPlayerFaction(newPlayer) == null) {
			if (newPlayer.isOnline()) {
				player.sendMessage(Raidcraft.pluginTitle + Raidcraft.sucessColor + newPlayer.getDisplayName()
						+ " has been sent a clan invite.");

				Raidcraft.addItem(inviteLocation + "." + newPlayer.getUniqueId().toString(), senderFaction);

				newPlayer.sendMessage(Raidcraft.pluginTitle + Raidcraft.sucessColor + player.getDisplayName()
						+ " has invited you to join " + senderFaction + ". To join use the command " + ChatColor.BOLD
						+ "/clan join " + senderFaction);
			} else {
				player.sendMessage(Raidcraft.pluginTitle + Raidcraft.sucessColor + newPlayer.getDisplayName()
						+ " is not currently online. They will get the request the next time they join.");
			}
		} else {
			player.sendMessage(Raidcraft.pluginTitle + Raidcraft.failColor + newPlayer.getDisplayName()
					+ " is already part of a faction");
		}
	}// End of addPlayerToFaction

	public String playerHasInvite(Raidcraft plugin, Player player){
		return plugin.getConfig().getString(inviteLocation + "." + player.getUniqueId().toString());
	}
	
	public void listClans(Raidcraft plugin, Player player){
		int numberOfClans = getNumberOfFactions();
		
		player.sendMessage(ChatColor.RED + "==========" + plugin.sucessColor + "[There are " + numberOfClans + " Clans]" + ChatColor.RED + "==========");
		
		List<String> factions = getAllFactions();
		
		for(int i = 0; i < factions.size() / 2; i++){		// For some reason the list is twice as long as it actually is(?)
			player.sendMessage(plugin.sucessColor + factions.get(i) + "\n");
		}
		
		player.sendMessage(ChatColor.RED + "==========" + plugin.sucessColor + "[There are " + numberOfClans + " Clans]" + ChatColor.RED + "==========");
	}
	
	public void removePlayerFromFaction(Player player, String factionName) {

	}// End of removePlayerFromFaction
	
	public void addTotalClaims(String factionName, int numberToAdd){
		int allowedClaims = Raidcraft.config.getInt(factionLocation + "." + factionName + "." + allowedClaimsLocation);
		Raidcraft.config.set(factionLocation + "." + factionName + "." + allowedClaimsLocation, (allowedClaims += numberToAdd));
	}
	
	public void removeTotalClaims(String factionName, int numberToRemove){
		int allowedClaims = Raidcraft.config.getInt(factionLocation + "." + factionName + "." + allowedClaimsLocation);
		Raidcraft.config.set(factionLocation + "." + factionName + "." + allowedClaimsLocation, (allowedClaims -= numberToRemove));
	}
	
	public void addCurrentClaims(String factionName, int numberToAdd){
		int currentClaims = Raidcraft.config.getInt(factionLocation + "." + factionName + "." + currentClaimsLocation);
		Raidcraft.config.set(factionLocation + "." + factionName + "." + currentClaimsLocation, (currentClaims += numberToAdd));
	}
	
	public void removeCurrentClaims(String factionName, int numberToRemove){
		int currentClaims = Raidcraft.config.getInt(factionLocation + "." + factionName + "." + currentClaimsLocation);
		Raidcraft.config.set(factionLocation + "." + factionName + "." + currentClaimsLocation, (currentClaims -= numberToRemove));
	}
	
	public boolean hasEnoughChunks(String factionName){
		int currentChunks = Raidcraft.config.getInt(factionLocation + "." + factionName + "." + currentClaimsLocation);
		int maxChunks = Raidcraft.config.getInt(factionLocation + "." + factionName + "." + allowedClaimsLocation); 
		
		return (currentChunks < maxChunks);
	}
}// End of class
