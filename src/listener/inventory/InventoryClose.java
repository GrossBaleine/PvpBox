package listener.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import kits.Kit;
import launcher.PvpBox;
import manager.PBPlayer;
import menu.MainMenu;

public class InventoryClose implements Listener {

	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		Inventory inv = event.getInventory();
		Player player = (Player) event.getPlayer();
		PBPlayer pbPlayer = PvpBox.getInstance().get(player);
		for (Kit kit : PvpBox.kits) {
			if (inv.getType() == InventoryType.ANVIL && pbPlayer.getKit() == kit) {
				player.sendMessage("Player inv : " + nbItemPlayer(player)
						+ " / kit stuff : " + kit.getStuff().size());
				if (inv.getItem(0) == null && inv.getItem(1) == null && inv.getItem(2) == null
						&& nbItemPlayer(player) == kit.getStuff().size())
					pbPlayer.getKitPref(kit).saveKitPreferences(player.getInventory());
				else
					player.sendMessage("La sauvegarde ne s'est pas effectuée");
				pbPlayer.setKit(null);
				new MainMenu(player);
			}
		}
	}

	private int nbItemPlayer(Player player) {
		int size = 0;
		for (ItemStack i : player.getInventory().getStorageContents()) {
			if (i != null)
				size++;
		}
		return size;
	}
}
