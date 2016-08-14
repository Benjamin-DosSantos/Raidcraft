package generation;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import main.Raidcraft;

public class MineCore {
	int mineXPos_1;
	int mineYPos_1;
	int mineZPos_1;

	int mineXPos_2;
	int mineYPos_2;
	int mineZPos_2;

	public void routeMine(Player sender, String[] commandArgs) {
		switch (commandArgs[0].toLowerCase()) {
		case "newmine":
			newMine(sender);
			break;
		}
	}

	public void newMine(Player player) {
		setCorner(player, 1);
		setCorner(player, 2);
		sendToConfig();
		generateMine(player);
	}// End of newMine method

	private void generateMine(Player player) {
		player.sendMessage("This is where we spawn the mine :)");
	}

	private void sendToConfig() {
		String minePath = "Mine.Location";
		
		Raidcraft.addItem(minePath + "." + "XPos_1", mineXPos_1);
		Raidcraft.addItem(minePath + "." + "YPos_1", mineYPos_1);
		Raidcraft.addItem(minePath + "." + "ZPos_1", mineZPos_1);
		
		Raidcraft.addItem(minePath + "." + "XPos_2", mineXPos_2);
		Raidcraft.addItem(minePath + "." + "YPos_2", mineYPos_2);
		Raidcraft.addItem(minePath + "." + "ZPos_2", mineZPos_2);
	}

	private void setCorner(Player player, int cornerNumber) {
		player.sendMessage("Move to the corner and press shift");
		boolean hasCrouched = false;

		while (!hasCrouched) {
			if (cornerNumber == 1) {
				if (player.isSneaking()) {
					Location playerLocation = player.getLocation();
					mineXPos_1 = playerLocation.getBlockX();
					mineYPos_1 = playerLocation.getBlockY();
					mineZPos_1 = playerLocation.getBlockZ();

					hasCrouched = true;
				}
			} else {
				if (player.isSneaking()) {
					Location playerLocation = player.getLocation();
					mineXPos_2 = playerLocation.getBlockX();
					mineYPos_2 = playerLocation.getBlockY();
					mineZPos_2 = playerLocation.getBlockZ();

					hasCrouched = true;
				}
			}
		}
	}
}// End of class
