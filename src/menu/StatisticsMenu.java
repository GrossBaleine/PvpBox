package menu;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import kits.Kit;
import launcher.PvpBox;
import manager.PBPlayer;
import utils.ItemBuilder;

public class StatisticsMenu {

	private PvpBox pvpBox = PvpBox.getInstance();
	private PBPlayer pbPlayer;
	private Inventory inventory;
	private int slot[] = { 19, 21, 23, 25, 29, 31, 33, 39, 41 };
	private int deco[] = { 0, 1, 7, 8, 10, 11, 12, 13, 14, 15, 16, 36, 37, 43, 44, 46, 47, 51, 52 };
	private ItemStack vitre = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 1);
	public StatisticsMenu(Player player) {
		pbPlayer = pvpBox.get(player);
		inventory = Bukkit.createInventory(null, 54, "§bStatistiques");
		ItemStack kills = new ItemBuilder(new ItemStack(Material.DIAMOND_SWORD), "§bKills", "§6" + pbPlayer.getKills());
		ItemStack rank = new ItemBuilder(new ItemStack(Material.BEACON), "§bRank", "§6Rank");
		ItemStack death = new ItemBuilder(new ItemStack(Material.SKULL_ITEM), "§bMorts", "§6" + pbPlayer.getDeaths());
		putKitStats();
		for(int i : deco)
			inventory.setItem(i,vitre);
		inventory.setItem(3, kills);
		inventory.setItem(4, rank);
		inventory.setItem(5, death);
		player.openInventory(inventory);
	}

	public void putKitStats() {
		int i =0;
		for (Kit kit : PvpBox.kits) {
			ArrayList<String> kitStat = new ArrayList<String>();
			kitStat.add("§3Kills : §6" + pbPlayer.getKitStats(kit).getKills());
			kitStat.add("§3Morts : §6" + pbPlayer.getKitStats(kit).getDeaths());
			kitStat.add("§3Nombre de sélections : §6" + pbPlayer.getKitStats(kit).getSelectedKit());
			ItemBuilder item = ((ItemBuilder) kit.getItem().clone()).setLore(kitStat);
			inventory.setItem(slot[i], item);
			i++;
		}
	}
}
