package menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import kits.Kit;
import launcher.PvpBox;

public class KitPreferencesMenu {

	public KitPreferencesMenu(Player player) {
		Inventory inventory = Bukkit.createInventory(player, 9, "§bEdition de kits");
		for (Kit kit : PvpBox.kits)
			inventory.setItem(kit.getSlot(), kit.getItem());
		player.openInventory(inventory);
	}
}
