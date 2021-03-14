package utils;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import kits.Kit;
import launcher.PvpBox;
import manager.PBPlayer;

public class RefillItems {

	private final ItemStack item;
	private final int amount;
	private final int maxItem;
	private final static float timerRefill = 20 * 40f;

	public RefillItems(ItemStack item, int amount, int maxItem) {
		this.item = item.clone();
		this.amount = amount;
		this.maxItem = maxItem;
	}

	private static void refill(PBPlayer player) {
		Kit kit = player.getKit();
		if (kit != null) {
			PlayerInventory inv = player.getPlayer().getInventory();
			for (RefillItems item : kit.getRefillItems()) {
				refillItem(item, inv, kit, player);
			}
		}
	}

	private static void refillItem(RefillItems item, PlayerInventory inv, Kit kit, PBPlayer player) {
		int count = countItem(inv, item);
		if(count == 0){
			refillIfAbsent(inv, item);
			return;
		}
		if (inv.contains(item.item.getType())) {
			HashMap<Integer, ? extends ItemStack> items = inv.all(item.item.getType());
			if (count <= item.getMaxItem() - item.getAmount()) {
				for (Entry<Integer, ? extends ItemStack> i : items.entrySet()) {
					ItemStack invItem = i.getValue();
					if (refillIfPresent(inv, item, invItem, i.getKey()))
						return;
				}
				ItemStack offhand = inv.getItemInOffHand();
				if(refillIfPresentInOffHand(item, offhand, inv))
					return;
				refillIfAbsent(inv, item);
				return;
			}
		}
	}

	private static int countItem(PlayerInventory inv, RefillItems item) {
		int count = 0;
		HashMap<Integer, ? extends ItemStack> items = inv.all(item.item.getType());
		for (Entry<Integer, ? extends ItemStack> i : items.entrySet()) {
			ItemStack invItem = i.getValue();
			if (fuckYouInternal(invItem, item.item))
				count += invItem.getAmount();
		}
		ItemStack offhand = inv.getItemInOffHand();
		if (offhand.getType() != Material.AIR && fuckYouInternal(offhand, item.item))
			count += offhand.getAmount();
		return count;
	}

	private static void refillIfAbsent(PlayerInventory inv, RefillItems item) {
		PvpBox.getInstance().broadcast("Refill absent : " + item.item.getItemMeta().getDisplayName());
		ItemStack refill = item.item;
		refill.setAmount(item.getAmount());
		inv.addItem(refill);
	}

	private static boolean refillIfPresent(PlayerInventory inv, RefillItems item, ItemStack invItem, int slot) {
		if (fuckYouInternal(invItem, item.item)) {
			if (item.getMaxItem() > invItem.getAmount() - item.getAmount())
				invItem.setAmount(invItem.getAmount() + item.getAmount());
			else
				invItem.setAmount(item.getMaxItem());
			inv.setItem(slot, invItem);
			return true;
		}
		return false;
	}

	private static boolean refillIfPresentInOffHand(RefillItems item, ItemStack offhand, PlayerInventory inv) {
		if (offhand.getType() != Material.AIR && fuckYouInternal(offhand, item.item)) {
			if (item.getMaxItem() > offhand.getAmount() - item.getAmount())
				offhand.setAmount(offhand.getAmount() + item.getAmount());
			else
				offhand.setAmount(item.getMaxItem());
			inv.setItemInOffHand(offhand);
			PvpBox.getInstance().broadcast("Refill offHand : " + item.item.getItemMeta().getDisplayName() + "/ "
					+ offhand.getItemMeta().getDisplayName());
			return true;
		}
		return false;
	}

	private int getAmount() {
		return amount;
	}

	private int getMaxItem() {
		return maxItem;
	}

	public static void refillTask() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) PvpBox.getInstance(), new Runnable() {
			public void run() {
				for (PBPlayer player : PvpBox.getInstance().getPvpPlayers()) {
					if (player.getPlayer().getExp() >= 1f - 1 / timerRefill) {
						player.getPlayer().setExp(0f);
						refill(player);
					} else
						player.getPlayer().setExp(player.getPlayer().getExp() + 1 / timerRefill);
				}
			}
		}, 0, 1);
	}

	private static boolean fuckYouInternal(ItemStack is1, ItemStack is2) {
		if (!(is1.hasItemMeta() && is1.hasItemMeta()))
			return false;
		String is1String = is1.getItemMeta().toString();
		String is2String = is2.getItemMeta().toString();
		if (is1.getType() != is2.getType())
			return false;
		if (is1.getType() != Material.AIR && is1String.contains(", internal=")) {
			String[] split = is1String.split("internal=");
			is1String = split[0] + split[1].split(", ", 2)[1];
		}
		if (is2.getType() != Material.AIR && is2String.contains(", internal=")) {
			String[] split = is2String.split("internal=");
			is2String = split[0] + split[1].split(", ", 2)[1];
		}
		return is1String.equals(is2String);
	}

}
