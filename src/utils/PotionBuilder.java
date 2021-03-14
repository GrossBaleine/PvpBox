package utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class PotionBuilder extends ItemStack {

	public PotionBuilder(Material kindOfPotion, int quantity, PotionType type, boolean extend, boolean upgraded,
			String displayName) {
		super(kindOfPotion, quantity);
		PotionMeta meta = (PotionMeta) this.getItemMeta();
		meta.setDisplayName(displayName);
		meta.setBasePotionData(new PotionData(type, extend, upgraded));
		this.setItemMeta(meta);
	}

	public PotionBuilder(Material kindOfPotion, int quantity, PotionEffectType customType, int duration, int amplifier,
			String displayName, Color color) {
		super(kindOfPotion, quantity);
		PotionMeta meta = (PotionMeta) this.getItemMeta();
		meta.setColor(color);
		meta.setDisplayName(displayName);
		meta.addCustomEffect(new PotionEffect(customType, duration, amplifier), true);
		this.setItemMeta(meta);
	}

}
