package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import faction.Faction;
import main.Raidcraft;

public class CommandHandler {
	Faction factionCore = new Faction();
	ClaimCommand claimCore = new ClaimCommand();
	HomeCommand homeCore = new HomeCommand();

	@SuppressWarnings("deprecation")
	public void routeCommand(CommandSender sender, String[] commandArgs) {
		Player player = (Player) sender;
		
		switch (commandArgs[0].toLowerCase()) {
		case "create":
			factionCore.createFaction(player, commandArgs[1].toLowerCase());
			break;
		case "claim":
			claimCore.claimLandForFaction(player);
			break;
		case "invite":
			factionCore.invitePlayerToFaction(player, Bukkit.getPlayer(commandArgs[1]));
			break;
		case "home":
			homeCore.home(player);
			break;
		case "sethome":
			homeCore.setHome(player);
			break;
		}// End of switch case
	}// End of routeCommand method
}// End of class
