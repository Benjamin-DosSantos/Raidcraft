package blockEvents;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import commands.ClaimCommand;
import main.Raidcraft;

public class CoreHandler {

	ClaimCommand claimHandler = new ClaimCommand();
	
	public void addBeacon(Raidcraft plugin, BlockPlaceEvent event) {
		Chunk currentChunk = event.getPlayer().getLocation().getChunk();
		String currentChunkAsString = currentChunk.toString();
		Player player = event.getPlayer();
		
		if(claimHandler.getOwnerFaction(plugin, player, currentChunk).equalsIgnoreCase("Wilderness")){
			event.setCancelled(true);
			player.sendMessage("No");
		}else{
			player.sendMessage("You placed a core at " + currentChunkAsString);
		}
	}// End of addBeacon Method
	
	public void removeBeacon(BlockBreakEvent event) {
		Chunk currentChunk = event.getPlayer().getLocation().getChunk();
		String currentChunkAsString = currentChunk.toString();
		Player player = event.getPlayer();
		
		player.sendMessage("You removed a core from " + currentChunkAsString);
	}// End of addBeacon Method
}// End of class
