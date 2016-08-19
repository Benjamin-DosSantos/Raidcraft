package faction;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import main.Raidcraft;

public class StoneBrickHandler implements Listener{
	Raidcraft plugin;
	FileConfiguration config;
	
	int numberOfTimesNeeded = 1;
	
	public static HashMap<Location, Integer> semiBrokenBlocks = new HashMap<Location, Integer>();
	
	public StoneBrickHandler(Raidcraft raidcraft) {
		plugin = raidcraft;
		config = plugin.getConfig();
	}

	
	@EventHandler
	public void playerBrokeStoneBrick(BlockBreakEvent event){
		if(event.getBlock().getType().equals(Material.SMOOTH_BRICK)){
			Player player = event.getPlayer();
			Location blockLocation = event.getBlock().getLocation();
			
			if(semiBrokenBlocks.containsKey(blockLocation)){
				int numberOfTimesBroken = semiBrokenBlocks.get(blockLocation);
				if(numberOfTimesBroken < numberOfTimesNeeded){
					event.setCancelled(true);
					semiBrokenBlocks.put(blockLocation, numberOfTimesBroken + 1);
					player.sendMessage(plugin.pluginTitle + plugin.failColor + "Break this block again to remove it");
				}else{
					semiBrokenBlocks.put(blockLocation, 0);
				}
			}else{
				semiBrokenBlocks.put(blockLocation, 1);
				event.setCancelled(true);
			}
		}
	}
	
	
}
