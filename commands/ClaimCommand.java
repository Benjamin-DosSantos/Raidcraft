package commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import faction.Faction;
import main.Raidcraft;

public class ClaimCommand {

	Faction factionCore = new Faction();

	public void claimLandForFaction(Raidcraft plugin, Player player) {
		String faction = factionCore.getPlayerFaction(player);
		String totalPath = factionCore.factionLocation + "." + faction + "." + factionCore.landLocation;

		List<String> chunkList = getFactionClaims(plugin, player, totalPath);

		Chunk chunk = player.getLocation().getChunk();
		String chunkInfo = chunk.toString();
		
		if (!chunkList.contains(chunkInfo) && getOwnerFaction(plugin, player, chunk).equals("Wilderness")) {
			chunkList.add(chunkInfo);
			Raidcraft.addItem(totalPath, chunkList);
			player.sendMessage(Raidcraft.pluginTitle + Raidcraft.sucessColor + " You claimed this chunk for your clan");
			plugin.previousOwner = faction;
		} else {
			player.sendMessage(Raidcraft.pluginTitle + Raidcraft.failColor + "This chunk is already claimed");
		}
	}
	
	public void removeLandFromFaction(Raidcraft plugin, Player player){
		String faction = factionCore.getPlayerFaction(player);
		String totalPath = factionCore.factionLocation + "." + faction + "." + factionCore.landLocation;
		
		List<String> chunkList = getFactionClaims(plugin, player, totalPath);
		String chunkInfo = player.getLocation().getChunk().toString();
		
		if (chunkList.contains(chunkInfo)) {
			chunkList.remove(chunkInfo);
			Raidcraft.addItem(totalPath, chunkList);
			player.sendMessage(Raidcraft.pluginTitle + Raidcraft.sucessColor + " This chunk has been removed");
		} else {
			player.sendMessage(Raidcraft.pluginTitle + Raidcraft.failColor + " You do not own this chunk");
		}
		
	}

	private List<String> getFactionClaims(Raidcraft plugin, Player player, String totalPath) {
		List<String> masterList = plugin.getConfig().getStringList(totalPath);
		
		return masterList;
	}

	public String getOwnerFaction(Raidcraft plugin, Player player, Chunk chunk){
		for(String faction: factionCore.getAllFactions()){
			String totalPath = factionCore.factionLocation + "." + faction + "." + factionCore.landLocation;
			List<String> chunkList = getFactionClaims(plugin, player, totalPath);
			if(chunkList.contains(chunk.toString())){
				return faction;
			}
		}
		return "Wilderness";
	}
}// End of class
