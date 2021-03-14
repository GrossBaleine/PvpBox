package menu;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import kits.Kit;
import launcher.PvpBox;
import utils.ItemBuilder;

public class KitSelectionMenu {

	private Inventory i;
	private Player player;
	private ItemBuilder unitPaneUnlock = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5, "§6Achat unitaire",
			null);
	private ItemBuilder unitPaneLock = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14, "§6Achat unitaire",
			null);
	private ItemBuilder unlock = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5, "§6Achat définitif", null);
	private ItemBuilder lock = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14, "§6Achat définitif", null);

	public KitSelectionMenu(Player player) {
		i = Bukkit.createInventory(player, 27, "§bKits");
		this.player = player;
		this.setKitItems();
		player.openInventory(i);
	}

	private void setKitItems() {
		for (Kit kit : PvpBox.kits) {
			int slot = kit.getSlot();
			PvpBox pvpBox = PvpBox.getInstance();
			ArrayList<String> loreFinal = new ArrayList<String>();
			ArrayList<String> loreUnit = new ArrayList<String>();
			loreFinal.add("§7Kit: §3" + kit.getName());
			loreUnit.add("§7Kit: §3" + kit.getName());
			i.setItem(slot, kit.getItem());
			if (pvpBox.get(player).getKitStats(kit).getAvailability()) {
				loreFinal.add("§8► §eDéjà acheté !");
				loreUnit.add("§8► §eDéjà acheté !");
				unlock.setLore(loreFinal);
				unitPaneUnlock.setLore(loreUnit);
				i.setItem(slot + 9, unitPaneUnlock);
				i.setItem(slot + 18, unlock);

			} else {
				loreUnit.add("");
				loreUnit.add("§eAcheter ce kit pour une utilisation");
				loreUnit.add("§eVous disposez de §6" + pvpBox.get(player).getKitStats(kit).getUnits()
						+ " §eutilisations pour ce kit");
				loreUnit.add("");
				loreUnit.add("§7(3 unités max par kit simultanément)");
				loreUnit.add("");
				loreUnit.add("§8► §7Prix: §3"+kit.getUnitPrice()+" points");
				loreUnit.add("§8► §7Clic gauche: §3Acheter");
				if (pvpBox.get(player).getKitStats(kit).getUnits() > 0) {
					unitPaneUnlock.setLore(loreUnit);
					i.setItem(slot + 9, unitPaneUnlock);
				} else {
					unitPaneLock.setLore(loreUnit);
					i.setItem(slot + 9, unitPaneLock);
				}
				loreFinal.add("");
				loreFinal.add("§eAcheter le kit définitivement");
				loreFinal.add("");
				loreFinal.add("§8►: §7Prix: §3"+kit.getFinalPrice()+" points");
				loreFinal.add("§8►: §7Clic gauche: §3Acheter");
				lock.setLore(loreFinal);
				i.setItem(slot + 18, lock);
			}
		}
	}
}