package commands;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import faction.Faction;
import main.Raidcraft;

public class HomeCommand {
	Raidcraft plugin;
	Faction factionCore = new Faction();

	public void home(Player player) {
		if (factionCore.getPlayerFaction(player) != null) {
			String totalPath = factionCore.factionLocation + "." + factionCore.getPlayerFaction(player) + "."
					+ factionCore.homeLocation;

			int xPos = Raidcraft.config.getInt(totalPath + "." + "xPos");
			int yPos = Raidcraft.config.getInt(totalPath + "." + "yPos");
			int zPos = Raidcraft.config.getInt(totalPath + "." + "zPos");

			Location homeLocation = new Location(player.getWorld(), xPos, yPos, zPos);

			player.sendMessage(plugin.titleColor + "[Raidcraft] " + plugin.sucessColor + "Teleporting");

			player.teleport(homeLocation);
		} else {
			player.sendMessage(plugin.titleColor + "[Raidcraft] " + plugin.failColor + "You are not a part of a clan!");
		}
	}// End of home method

	public void setHome(Player player) {
		if (factionCore.getPlayerFaction(player) != null) {
			if (factionCore.getAllModsInFaction(factionCore.getPlayerFaction(player))
					.contains(player.getUniqueId().toString())
					|| factionCore.getFactionLeader(factionCore.getPlayerFaction(player))
							.equalsIgnoreCase(player.getUniqueId().toString())) {
				
				Location newHome = player.getLocation();
				String totalPath = factionCore.factionLocation + "." + factionCore.getPlayerFaction(player) + "."
						+ factionCore.homeLocation;

				Raidcraft.addItem(totalPath + "." + "xPos", newHome.getBlockX());
				Raidcraft.addItem(totalPath + "." + "yPos", newHome.getBlockY());
				Raidcraft.addItem(totalPath + "." + "zPos", newHome.getBlockZ());
				
				player.sendMessage(plugin.titleColor + "[Raidcraft] " + plugin.sucessColor + "A new clan home has been set.");
			}else{
				player.sendMessage(plugin.titleColor + "[Raidcraft] " + plugin.failColor + "You do not have permission to do this!");
			}
		}else{
			player.sendMessage(plugin.titleColor + "[Raidcraft] " + plugin.failColor + "You are not a part of a clan!");
		}
	}// End of setHome method
}// End of class
