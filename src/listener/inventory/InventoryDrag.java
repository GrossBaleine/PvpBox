package listener.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class InventoryDrag implements Listener {

	@EventHandler
	public void onDrag(InventoryDragEvent event) {
		Inventory inv = event.getInventory();
		switch (inv.getName()) {
		case "§bKits":
		case "§bStatistiques":
			event.setCancelled(true);
			break;
		default:
			//event.setCancelled(true);
			break;
		}
	}
}
