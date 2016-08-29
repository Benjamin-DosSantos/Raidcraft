package blockEvents;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import main.Raidcraft;

public class StoneBrickHandler implements Listener {
	Raidcraft plugin;
	FileConfiguration config;

	int numberOfTimesNeeded = 1;

	public static HashMap<Location, Integer> semiBrokenBlocks = new HashMap<Location, Integer>();

	public StoneBrickHandler(Raidcraft raidcraft) {
		plugin = raidcraft;
		config = plugin.getConfig();
	}

	@EventHandler
	public void playerBrokeStoneBrick(EntityExplodeEvent event) {
		for (Block block : new ArrayList<Block>(event.blockList())) {
			if (block.getType().equals(Material.SMOOTH_BRICK)) {
				Location blockLocation = block.getLocation();
				if (semiBrokenBlocks.containsKey(blockLocation)) {
					int numberOfTimesBroken = semiBrokenBlocks.get(blockLocation);
					if (numberOfTimesBroken < numberOfTimesNeeded) {
						event.blockList().remove(block);
						semiBrokenBlocks.put(blockLocation, numberOfTimesBroken + 1);
					} else {
						semiBrokenBlocks.put(blockLocation, 0);
					}
				} else if(block.getType().equals(Material.BEACON)){
					// TODO: Remove the core from the chunk data
				}else{
					semiBrokenBlocks.put(blockLocation, 1);
					event.blockList().remove(block);
				}
			}else{
				event.blockList().remove(block);
			}
		}
	}
}
