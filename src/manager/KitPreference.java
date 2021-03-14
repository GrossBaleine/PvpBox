package manager;

import java.util.HashMap;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import kits.Kit;

public class KitPreference {

	public PBPlayer player;
	public Kit kit;
	public HashMap<Integer, ItemStack> itemPositions = new HashMap<Integer, ItemStack>();

	public KitPreference(PBPlayer player, Kit kit) {
		this.player = player;
		this.kit = kit;
		int pos = 0;
		for (ItemStack item : kit.getStuff()) {
			this.itemPositions.put(pos, item);
			pos++;
		}
	}

	public void saveKitPreferences(Inventory inventory) {
		HashMap<Integer, ItemStack> newItemPos = new HashMap<Integer, ItemStack>();
		for(int i = 0; i<inventory.getSize();i++){
			newItemPos.put(i, inventory.getItem(i));
		}
		itemPositions = newItemPos;
		player.getPlayer().sendMessage("sauvegarde");
	}

	public ItemStack renameItem(ItemStack item, String name) {
		item.getItemMeta().setDisplayName(name);
		return item;
	}

	public Kit getKit() {
		return this.kit;
	}
}
