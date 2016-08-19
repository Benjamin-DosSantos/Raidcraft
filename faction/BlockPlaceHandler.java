package faction;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import commands.ClaimCommand;
import main.Raidcraft;

public class BlockPlaceHandler implements Listener{
	ClaimCommand claimCore = new ClaimCommand();
	Faction factionCore = new Faction();
	
	Raidcraft plugin;
	
	public BlockPlaceHandler(Raidcraft raidcraft) {
		plugin = raidcraft;
	}

	@EventHandler
	public void playerBlockPlace(BlockPlaceEvent event){
		Player player = event.getPlayer();
		
		Chunk blockChunk = event.getBlock().getChunk();
		
		String faction = claimCore.getOwnerFaction(plugin, player, blockChunk);
		
		if(!faction.equalsIgnoreCase(factionCore.getPlayerFaction(player)) && !faction.equalsIgnoreCase("Wilderness")){
			player.sendMessage(plugin.pluginTitle + plugin.failColor + "You don't have permission to place a block here");
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void playerBreakPlace(BlockBreakEvent event){
		Player player = event.getPlayer();
		
		Chunk blockChunk = event.getBlock().getChunk();
		
		String faction = claimCore.getOwnerFaction(plugin, player, blockChunk);
		
		if(!faction.equalsIgnoreCase(factionCore.getPlayerFaction(player)) && !faction.equalsIgnoreCase("Wilderness")){
			player.sendMessage(plugin.pluginTitle + plugin.failColor + "You don't have permission to break this block");
			event.setCancelled(true);
		}
	}
}// End of class
