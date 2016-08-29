package blockEvents;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import commands.ClaimCommand;
import faction.Faction;
import main.Raidcraft;

public class BlockPlaceHandler implements Listener{
	ClaimCommand claimCore = new ClaimCommand();
	Faction factionCore = new Faction();
	TNTHandler tntHandler = new TNTHandler();
	CoreHandler coreHandler = new CoreHandler();
	
	Raidcraft plugin;
	
	public BlockPlaceHandler(Raidcraft raidcraft) {
		plugin = raidcraft;
	}

	@EventHandler
	public void playerBlockPlace(BlockPlaceEvent event){
		Player player = event.getPlayer();
		
		Chunk blockChunk = event.getBlock().getChunk();
		
		String faction = claimCore.getOwnerFaction(plugin, blockChunk);
		
		if(!faction.equalsIgnoreCase(factionCore.getPlayerFaction(player)) && !faction.equalsIgnoreCase("Wilderness") && !event.getBlock().getType().equals(Material.TNT)){
			player.sendMessage(plugin.pluginTitle + plugin.failColor + "You don't have permission to place a block here");
			event.setCancelled(true);
		}else{
			Material blockType = event.getBlock().getType();
			
			switch (blockType){
			case TNT:
				tntHandler.createTNT(event);
				break;
			case BEACON:
				coreHandler.addBeacon(plugin, event);
				break;
			default:
				break;
			} // End of if the block is TNT
		}
	}
	
	@EventHandler
	public void playerBreak(BlockBreakEvent event){
		Player player = event.getPlayer();
		
		Chunk blockChunk = event.getBlock().getChunk();
		
		String faction = claimCore.getOwnerFaction(plugin, blockChunk);
		
		if(!faction.equalsIgnoreCase(factionCore.getPlayerFaction(player)) && !faction.equalsIgnoreCase("Wilderness")){
			player.sendMessage(plugin.pluginTitle + plugin.failColor + "You don't have permission to break this block");
			event.setCancelled(true);
		}else{
			Material blockType = event.getBlock().getType();
			
			switch (blockType){
			case BEACON:
				coreHandler.removeBeacon(plugin, event);
				break;
			default:
				break;
			} 
		}
	}
}// End of class
