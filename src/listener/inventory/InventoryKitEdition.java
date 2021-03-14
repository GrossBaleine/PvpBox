package listener.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import kits.Kit;
import launcher.PvpBox;
import manager.PBPlayer;

public class InventoryKitEdition implements Listener {

	/*
	 * @EventHandler(priority = EventPriority.HIGHEST) public void
	 * prepareRename(PrepareAnvilEvent event) { AnvilInventory inv =
	 * event.getInventory(); Player player = (Player) event.getViewers().get(0);
	 * player.sendMessage("Title = "+ player.getOpenInventory().getTitle()); for
	 * (Kit k : PvpBox.kits) { if
	 * (player.getOpenInventory().getTitle().equals("�bEdition de kits : " +
	 * k.getName())) { ItemStack item = null; if (inv.getItem(0) != null &&
	 * inv.getItem(1) == null) item = inv.getItem(0); else if (inv.getItem(0) ==
	 * null && inv.getItem(1) != null) item = inv.getItem(1); if (item != null)
	 * { //Repairable renamed = (Repairable) item.getItemMeta();
	 * inv.setRepairCost(9);
	 * 
	 * //item.setItemMeta((ItemMeta) renamed); //event.setResult(item); } } } }
	 */

	@EventHandler
	public void onAnvilClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (player.getOpenInventory().getTopInventory().getType() == InventoryType.ANVIL) {
			for (Kit k : PvpBox.kits) {
				if (player.getOpenInventory().getTitle().equals("§bEdition de kits : " + k.getName())) {
					if (event.getSlot() == 2 && event.getClickedInventory().getType() == InventoryType.ANVIL) {
						PBPlayer pbPlayer = PvpBox.getInstance().get(player);
						pbPlayer.setPoints(pbPlayer.getPoints() - 10);
						player.sendMessage("is cancelled ? = " + event.isCancelled());
					}
				}
			}
		}
	}

}
