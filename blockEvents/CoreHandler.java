package blockEvents;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import commands.ClaimCommand;
import faction.Faction;
import main.Raidcraft;

public class CoreHandler {

	ClaimCommand claimHandler = new ClaimCommand();
	Faction factionCore = new Faction();
	
	public void addBeacon(Raidcraft plugin, BlockPlaceEvent event) {
		Chunk currentChunk = event.getPlayer().getLocation().getChunk();
		String currentChunkAsString = currentChunk.toString();
		Player player = event.getPlayer();
		
		if(claimHandler.getOwnerFaction(plugin, player, currentChunk).equalsIgnoreCase("Wilderness")){
			event.setCancelled(true);
			player.sendMessage(plugin.pluginTitle + plugin.failColor + "You can't place a core block in a chunk your faction dosen't control");
		}else{
			player.sendMessage(plugin.pluginTitle + plugin.sucessColor + "You sucessfuly placed a core block");
			String coreLocation = factionCore.factionLocation + "." + factionCore.getPlayerFaction(player) + "." + factionCore.landLocation + "." + currentChunkAsString;
			plugin.addItem(coreLocation, "Yes");
			player.sendMessage("Yes");
		}
	}// End of addBeacon Method
	
	public void removeBeacon(Raidcraft plugin, BlockBreakEvent event) {
		Chunk currentChunk = event.getPlayer().getLocation().getChunk();
		String currentChunkAsString = currentChunk.toString();
		Player player = event.getPlayer();
		
		player.sendMessage(plugin.pluginTitle + plugin.sucessColor + "You removed a core from this chunk");
		String coreLocation = factionCore.factionLocation + "." + factionCore.getPlayerFaction(player) + "." + factionCore.landLocation + "." + currentChunkAsString;
		plugin.addItem(coreLocation, "No");
		player.sendMessage("No");
	}// End of addBeacon Method
}// End of class
