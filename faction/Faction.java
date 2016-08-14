package faction;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import main.Raidcraft;

public class Faction {
	Raidcraft plugin;

	public String factionLocation = "Factions";
	public String leaderLocation = "Leader";
	public String playerLocation = "Players";
	public String modLocation = "Mods";
	public String landLocation = "Land";
	public String homeLocation = "Home";
	public String inviteLocation = "Invites";

	ArrayList<String> masterList = new ArrayList<String>();

	public void createFaction(Player player, String factionName) {
		String factionPath = factionLocation + "." + factionName;
		if (!isInConfig(player, factionName) && getPlayerFaction(player) == null) {
			player.sendMessage(plugin.titleColor + "[Raidcraft] " + plugin.sucessColor + "Faction created successfully!");
			masterList.add(factionName);
			Raidcraft.addItem(factionPath, masterList);
			Raidcraft.addItem(factionPath + "." + leaderLocation, player.getUniqueId().toString());
		} else {
			player.sendMessage(
					plugin.titleColor + "[Raidcraft] " + plugin.failColor + "The faction was not created, please try again.");
		}
	}// End of createFaction method

	public boolean isInConfig(Player player, String factionName) {
		boolean isInConfig = false;
		if (getAllFactions().contains(factionName)) {
			isInConfig = true;
		} // End of if the name is the same as the one the user wants to create
		return isInConfig;
	}// End of isInConfig method

	public ArrayList<String> getAllFactions() {
		ConfigurationSection factions = Raidcraft.config.getConfigurationSection(factionLocation);
		if (factions != null) {
			for (String faction : factions.getKeys(false)) {
				masterList.add(faction);
			}
		}
		return masterList;
	}

	public void removeFaction(Player player, String factionName) {

	}// End of removeFaction method

	public String getFactionLeader(String factionName) {
		return Raidcraft.config.getString(factionLocation + "." + factionName + "." + leaderLocation);
	}

	public String getPlayerFaction(Player player) {
		ArrayList<String> factionList = getAllFactions();
		for (String faction : factionList) {
			String factionPath = factionLocation + "." + faction;
			if (player.getUniqueId().toString()
					.equals(Raidcraft.config.getString(factionPath + "." + leaderLocation).toString())
					|| getAllPlayersInFaction(faction).contains(player.getUniqueId().toString())) {
				return faction;
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
			player.sendMessage("You have been added to a clan");
		} else {
			player.sendMessage("You are already a part of a faction");
		}
	}// End of addPlayerToFaction

	public void invitePlayerToFaction(Player player, Player newPlayer) {
		String senderFaction = getPlayerFaction(player);

		if (getPlayerFaction(newPlayer) == null) {
			if (newPlayer.isOnline()) {
				player.sendMessage(plugin.titleColor + "[Raidcraft] " + plugin.sucessColor + newPlayer.getDisplayName()
						+ " has been sent a clan invite.");

				Raidcraft.addItem(inviteLocation + "." + newPlayer.getUniqueId().toString(), senderFaction);

				newPlayer.sendMessage(plugin.titleColor + "[Raidcraft] " + plugin.sucessColor + player.getDisplayName()
						+ " has invited you to join " + senderFaction + ". To join use the command " + ChatColor.BOLD
						+ "/clan join " + senderFaction);
			} else {
				player.sendMessage(plugin.titleColor + "[Raidcraft] " + plugin.sucessColor + newPlayer.getDisplayName()
						+ " is not currently online. They will get the request the next time they join.");
			}
		} else {
			player.sendMessage(plugin.titleColor + "[Raidcraft] " + plugin.failColor + newPlayer.getDisplayName()
					+ " is already part of a faction");
		}
	}// End of addPlayerToFaction

	public void removePlayerFromFaction(Player player, String factionName) {

	}// End of removePlayerFromFaction
}// End of class
