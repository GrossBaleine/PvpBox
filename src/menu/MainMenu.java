package menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import utils.ItemBuilder;

public class MainMenu {

	public MainMenu(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setHealth(20);
		player.setFoodLevel(20);
		ItemBuilder kits = new ItemBuilder(Material.DIAMOND_SWORD, 1, (short) 0, "§bChoisir un kit §8(Clic-droit)",
				null);
		// ItemBuilder vs = new ItemBuilder(Material.BLAZE_ROD, 1, (short)
		// 0,"§bDéfier un joueur §8(Cliquez sur un joueur)", null);
		// ItemStack skull = new HeadBuilder(player.getName(), "§bStatistiques
		// §8(Clic-droit)", null).toItemStack();
		// ItemBuilder edit = new ItemBuilder(Material.ANVIL, 1, (short) 0,
		// "§bEdition de kit", null);
		ItemBuilder hub = new ItemBuilder(Material.BED, 1, (short) 0, "§bRetour au hub §8(Clic-droit)", null);
		player.getInventory().setItem(0, kits);
		// player.getInventory().setItem(1, vs);
		// player.getInventory().setItem(4, skull);
		// player.getInventory().setItem(6, edit);
		player.getInventory().setItem(8, hub);
	}
}
