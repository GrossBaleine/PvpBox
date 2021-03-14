package listener.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import kits.Kit;
import launcher.PvpBox;
import manager.PBPlayer;
import menu.KitEditionMenu;
import menu.KitSelectionMenu;

public class InventoryClick implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		PBPlayer pbPlayer = PvpBox.getInstance().get(player);
		String inventory = event.getInventory().getTitle();
		int slot = event.getSlot();
		switch (inventory) {
		case "§bKits":
			event.setCancelled(true);
			verifyKitAndSet(pbPlayer, slot, event.getCurrentItem());
			break;
		case "§bStatistiques":
			event.setCancelled(true);
			break;
		case "§bEdition de kits":
			for (Kit k : PvpBox.kits) {
				int kitSlot = k.getSlot();
				if (slot == kitSlot && k.getItem().getItemMeta().getDisplayName() == event.getCurrentItem()
						.getItemMeta().getDisplayName())
					new KitEditionMenu(player, k);
			}
			event.setCancelled(true);
			break;
		default:
			/*
			 * if (player.getOpenInventory().getType() == InventoryType.ANVIL) {
			 * Inventory inv = player.getOpenInventory().getTopInventory(); for
			 * (Kit k : PvpBox.kits) { if (player.getOpenInventory().getTitle().
			 * equals("�bEdition de kits : " + k.getName()) && inv.getItem(2) !=
			 * null && inv.getItem(0) != null && inv.getItem(1) == null) {
			 * 
			 * player.sendMessage( "Repair cost : " + ((Repairable)
			 * inv.getItem(0).getItemMeta()).getRepairCost()); } } }
			 */
			break;
		}
	}

	private void verifyKitAndSet(PBPlayer pbPlayer, int slot, ItemStack clickedItem) {
		for (Kit k : PvpBox.kits) {
			int kitSlot = k.getSlot();
			ItemStack kitItem = k.getItem();
			if (slot == kitSlot
					&& kitItem.getItemMeta().getDisplayName() == clickedItem.getItemMeta().getDisplayName()) {
				if (pbPlayer.getKitStats(k).getAvailability())
					pbPlayer.setPvpMode(k);
				else if (pbPlayer.getKitStats(k).getUnits() > 0) {
					pbPlayer.getKitStats(k).setUnits(pbPlayer.getKitStats(k).getUnits() - 1);
					pbPlayer.setPvpMode(k);
				} else if (!pbPlayer.getKitStats(k).getAvailability()) {
					//com.elenox.api.ElenoxAPI.getElenoxAPI().getActionBar().sendActionBar(pbPlayer.getPlayer(),
							//"Vous n'avez pas encore débloqué le kit" + k.getName());
				}
			} else if (slot == kitSlot + 9) {
				pbPlayer.buyUnitKit(k);
				new KitSelectionMenu(pbPlayer.getPlayer());
			} else if (slot == kitSlot + 18) {
				pbPlayer.buyFinalKit(k);
				new KitSelectionMenu(pbPlayer.getPlayer());
			}
		}
	}
}
