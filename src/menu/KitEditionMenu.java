package menu;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import kits.Kit;
import launcher.PvpBox;
import manager.KitPreference;
import manager.PBPlayer;

public class KitEditionMenu {

	private PvpBox pvpBox = PvpBox.getInstance();
	private PBPlayer pbPlayer;
	private KitPreference pref;
	private Inventory inventory;
	private Player watcher;

	public KitEditionMenu(Player player, Kit kit) {
		inventory = Bukkit.createInventory(player, InventoryType.ANVIL, "§bEdition de kits : " + kit.getName());
		pbPlayer = pvpBox.get(player);
		pbPlayer.setKit(kit);
		pref = pbPlayer.getKitPref(kit);
		player.getInventory().clear();
		for (Entry<Integer, ItemStack> slot : pref.itemPositions.entrySet()) {
			Repairable repair = (Repairable) slot.getValue().getItemMeta();
			repair.setRepairCost(9);
			slot.getValue().setItemMeta((ItemMeta) repair);
			player.getInventory().setItem(slot.getKey(), slot.getValue());
		}
		player.openInventory(inventory);

	}
	
	public KitEditionMenu(Player player,Player watcher, Kit kit) {
		inventory = Bukkit.createInventory(watcher, InventoryType.ANVIL, "§bEdition de kits ("+player.getName()+") : " + kit.getName());
		this.watcher = watcher;
		pbPlayer = pvpBox.get(player);
		pbPlayer.setKit(kit);
		pref = pbPlayer.getKitPref(kit);
		watcher.getInventory().clear();
		for (Entry<Integer, ItemStack> slot : pref.itemPositions.entrySet()) {
			Repairable repair = (Repairable) slot.getValue().getItemMeta();
			repair.setRepairCost(0);
			slot.getValue().setItemMeta((ItemMeta) repair);
			watcher.getInventory().setItem(slot.getKey(), slot.getValue());
		}
		watcher.openInventory(inventory);

	}

	/*public void test(Player player,ArrayList<String> items) {
		AnvilGUIInterface gui = AnvilApi.createNewGUI(player, new AnvilClickEventHandler() {
			@Override
			public void onAnvilClick(final AnvilClickEvent event) {
				if (event.getSlot() == AnvilSlot.OUTPUT) {
					event.setWillClose(true);
					event.setWillDestroy(true);
					ItemStack item = items.get(event.getPlayerName());
					ItemMeta itemMeta = item.getItemMeta();
					itemMeta.setDisplayName(event.getName().replace("&", "§"));
					item.setItemMeta(itemMeta);
					Bukkit.getPlayer(event.getPlayerName()).setItemInHand(item);
					items.remove(event.getPlayerName());
					return;
				} else {
					event.setWillClose(true);
					event.setWillDestroy(true);
					Bukkit.getPlayer(event.getPlayerName()).setItemInHand(items.get(event.getPlayerName()));
					items.remove(event.getPlayerName());
					return;
				}
			}
		});
		gui.setSlot(AnvilSlot.INPUT_LEFT, player.getItemInHand());
		items.put(player.getName(), player.getItemInHand());
		player.closeInventory();
		player.setItemInHand(null);
		gui.open();
	}*/
}
