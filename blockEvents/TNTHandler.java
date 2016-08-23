package blockEvents;

import org.bukkit.Material;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.BlockPlaceEvent;

public class TNTHandler {
	public void createTNT(BlockPlaceEvent event){
		event.getBlock().setType(Material.AIR);
		TNTPrimed tnt = (TNTPrimed) event.getPlayer().getWorld().spawn(event.getBlock().getLocation(), TNTPrimed.class);
	}// End of createTNT method
}// End of class
