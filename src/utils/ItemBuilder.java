package utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder extends ItemStack {

	public ItemBuilder(Material material, int amount, short damage, String name, List<String> lore) {
		super(material,amount,damage);
		ItemMeta meta = getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		setItemMeta(meta);
	}
	
	public ItemBuilder(ItemStack item, String name, List<String>lore){
		super(item);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		setItemMeta(meta);
	}
	
	public ItemBuilder(ItemStack item, String name, String lore){
		super(item);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		ArrayList<String> array = new ArrayList<String>();
		array.add(lore);
		meta.setLore(array);
		setItemMeta(meta);
	}
	
	public ItemBuilder(Material material, String name, List<String>lore){
		super(material);
		ItemMeta meta = getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		setItemMeta(meta);
	}
	
	public ItemBuilder setLore(ArrayList<String> lore){
		ItemMeta meta = getItemMeta();
		meta.setLore(lore);
		setItemMeta(meta);
		return this;
	}
	
}
