package manager;

import org.bukkit.inventory.ItemStack;

import kits.Kit;

public enum Titles {
	
	;
	private String name;
	private ItemStack item;
	private String description;
	private Kit kit;
	private int kills;
	private int deaths;
	private int nbSelection;
	private int livingTime;
	private int hitsGiven;
	private int killStreak;

	Titles(String name, String Description, Kit kit, int kills, int deaths, int nbSelection, int livingTime,
			int hitsGiven, int killStreak) {

	}

}
