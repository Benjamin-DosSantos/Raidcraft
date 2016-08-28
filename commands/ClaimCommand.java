package commands;

import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import faction.Faction;
import main.Raidcraft;

public class ClaimCommand {

	Faction factionCore = new Faction();

	public void claimLandForFaction(Raidcraft plugin, Player player) {
		String faction = factionCore.getPlayerFaction(player);
		String totalPath = factionCore.factionLocation + "." + faction + "." + factionCore.landLocation;

		Chunk chunk = player.getLocation().getChunk();
		String chunkInfo = chunk.toString();
		
		String oldFaction = getOwnerFaction(plugin, chunk);
		
		if (!hasCore(chunk)) {
			removeLandFromFaction(plugin, oldFaction, chunk);
			List<String> chunkList = getFactionClaims(plugin, totalPath);
			chunkList.add(chunkInfo);
			for(String chunkitem: chunkList){
				System.out.println(chunkitem);
			}
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
		
		List<String> chunkList = getFactionClaims(plugin, totalPath);
		String chunkInfo = player.getLocation().getChunk().toString();
		
		if (chunkList.contains(chunkInfo)) {
			chunkList.remove(chunkInfo);
			Raidcraft.addItem(totalPath, chunkList);
			player.sendMessage(Raidcraft.pluginTitle + Raidcraft.sucessColor + " This chunk has been removed");
		} else {
			player.sendMessage(Raidcraft.pluginTitle + Raidcraft.failColor + " You do not own this chunk");
		}
		
	}

	public void removeLandFromFaction(Raidcraft plugin, String faction, Chunk chunk){
		String totalPath = factionCore.factionLocation + "." + faction + "." + factionCore.landLocation;
		
		List<String> chunkList = getFactionClaims(plugin, totalPath);
		String chunkInfo = "CraftChunk{x=" + chunk.getX() + "z=" + chunk.getZ() + "}";
		
		for(String chunkitem: chunkList){
			System.out.println(chunkitem);
		}
		
		if (chunkList.contains(chunkInfo)) {
			chunkList.remove(chunkInfo);
			
			System.out.println("=========================================");
			
			for(String chunkitem: chunkList){
				System.out.println(chunkitem);
			}
			
			Raidcraft.addItem(totalPath, chunkList);
			System.out.println("Removed " + chunkInfo);
		}
	}
	
	private List<String> getFactionClaims(Raidcraft plugin, String totalPath) {
		List<String> masterList = plugin.getConfig().getStringList(totalPath);
		
		return masterList;
	}

	public String getOwnerFaction(Raidcraft plugin, Chunk chunk){
		for(String faction: factionCore.getAllFactions()){
			String totalPath = factionCore.factionLocation + "." + faction + "." + factionCore.landLocation;
			List<String> chunkList = getFactionClaims(plugin, totalPath);
			if(chunkList.contains(chunk.toString())){
				return faction;
			}
		}
		return "Wilderness";
	}
	
	public boolean hasCore(Chunk chunk){
		
		
		
		return false;
	}// End of hasCore method
}// End of class
