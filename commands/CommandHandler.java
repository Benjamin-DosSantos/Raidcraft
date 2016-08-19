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
	public void routeCommand(Raidcraft plugin, CommandSender sender, String[] commandArgs) {
		Player player = (Player) sender;

		switch (commandArgs[0].toLowerCase()) {
		case "create":
			factionCore.createFaction(player, commandArgs[1]); // This is where the To lower case was if that becomes an issue
			break;
		case "disband":
			factionCore.disbandFaction(player); // This is where the To lower case was if that becomes an issue
			break;
		case "claim":
			claimCore.claimLandForFaction(plugin, player);
			break;
		case "unclaim":
			claimCore.removeLandFromFaction(plugin, player);
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
