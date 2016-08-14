package commands;

import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import faction.Faction;
import main.Raidcraft;

public class ClaimCommand {

	Raidcraft plugin;

	Faction factionCore = new Faction();

	public void claimLandForFaction(Player player) {
		String faction = factionCore.getPlayerFaction(player);
		String totalPath = factionCore.factionLocation + "." + faction + "." + factionCore.landLocation;

		ArrayList<String> chunkList = getFactionClaims(faction, totalPath);

		String chunkInfo = player.getLocation().getChunk().toString();

		if (!chunkList.contains(chunkInfo)) {
			player.sendMessage(totalPath);
			chunkList.add(chunkInfo);
			Raidcraft.addItem(totalPath, chunkList);
			player.sendMessage(plugin.titleColor + "[RaidCraft] " + plugin.sucessColor + " You are trying to claim a chunk");
		} else {
			player.sendMessage(plugin.titleColor + "[RaidCraft] " + plugin.failColor + "This chunk is already claimed");
		}
	}

	private ArrayList<String> getFactionClaims(String faction, String totalPath) {
		ArrayList<String> masterList = new ArrayList<>();

		ConfigurationSection factionClaims = Raidcraft.config.getConfigurationSection(totalPath);

		if (factionClaims != null) {
			for (String chunk : factionClaims.getKeys(false)) {
				masterList.add(chunk);
			}
		}
		return masterList;
	}

	public boolean isChuckClaimed() {
		return false;
	}
}// End of class
