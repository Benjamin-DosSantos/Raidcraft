package blockEvents;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import commands.ClaimCommand;
import faction.Faction;
import main.Raidcraft;

public class CoreHandler {

	ClaimCommand claimHandler = new ClaimCommand();
	Faction factionCore = new Faction();
	
	@SuppressWarnings("static-access")
	public void addBeacon(Raidcraft plugin, BlockPlaceEvent event) {
		Chunk currentChunk = event.getPlayer().getLocation().getChunk();
		String currentChunkAsString = currentChunk.toString();
		Player player = event.getPlayer();
		
		if(claimHandler.getOwnerFaction(plugin, currentChunk).equalsIgnoreCase("Wilderness")){
			event.setCancelled(true);
			player.sendMessage(plugin.pluginTitle + plugin.failColor + "You can't place a core block in a chunk your faction dosen't control");
		}else{
			List<String> beacons = getAllFactionBeacons(player);
			if(!beacons.contains(currentChunkAsString)){
				beacons.add(currentChunkAsString);
				plugin.addItem(factionCore.factionLocation + "." + factionCore.getPlayerFaction(player) + "." + factionCore.beaconLocation, beacons);
				player.sendMessage(plugin.pluginTitle + plugin.sucessColor + "You sucessfuly placed a core block");
			}else{
				player.sendMessage(plugin.pluginTitle + plugin.failColor + "This chunk already contains a beacon");
			}
		}
	}// End of addBeacon Method
	
	@SuppressWarnings("static-access")
	public void removeBeacon(Raidcraft plugin, BlockBreakEvent event) {
		Chunk currentChunk = event.getPlayer().getLocation().getChunk();
		String currentChunkAsString = currentChunk.toString();
		Player player = event.getPlayer();
		
		List<String> beacons = getAllFactionBeacons(player);
		if(beacons.contains(currentChunkAsString)){
			beacons.remove(currentChunkAsString);
			plugin.addItem(factionCore.factionLocation + "." + factionCore.getPlayerFaction(player) + "." + factionCore.beaconLocation, beacons);
			player.sendMessage(plugin.pluginTitle + plugin.sucessColor + "You sucessfuly removed a core block");
		}
	}// End of addBeacon Method
	
	public List<String> getAllFactionBeacons(Player player){
		ConfigurationSection beacons = Raidcraft.config.getConfigurationSection(factionCore.factionLocation + "." + factionCore.getPlayerFaction(player) + "." + factionCore.beaconLocation);
		List<String> beaconLocations = new ArrayList<String>();
		if(beacons != null){
			for (String chunk : beacons.getKeys(false)) {
				beaconLocations.add(chunk);
			}
		}
		return beaconLocations;
	}
}// End of class
