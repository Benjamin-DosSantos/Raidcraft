package commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import faction.Faction;

public class CommandHandler {

	Faction factionCore = new Faction();
	ClaimCommand claimCore = new ClaimCommand();

	public void routeCommand(CommandSender sender, String[] commandArgs) {
		Player player = (Player) sender;
		switch (commandArgs[0].toLowerCase()) {
		case "create":
			factionCore.createFaction(player, commandArgs[1].toLowerCase());
			break;
		case "claim":
			claimCore.claimLandForFaction(player);
			break;
		case "add":
			factionCore.addPlayerToFaction(player, commandArgs[1]);
			break;
		}
	}// End																																																																				// method
}// End of class
