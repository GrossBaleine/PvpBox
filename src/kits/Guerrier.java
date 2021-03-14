package kits;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import manager.PBPlayer;
import utils.ItemBuilder;
import utils.PotionBuilder;
import utils.RefillItems;

public class Guerrier extends Kit {

	public Guerrier() {
		super();
		name = "Guerrier";
		menuItem = new ItemBuilder(Material.IRON_SWORD, 1, (short) 0, "§6Kit: Guerrier", null);
		UnitPrice = 0 ;
		FinalPrice = 0 ;
		setItemDescription();
		setStuff();
		this.slot = 0;
	}

	public void setStuff() {
		armor[3] = new ItemBuilder(Material.IRON_HELMET, "Casque en fer", null);
		armor[2] = new ItemBuilder(Material.IRON_CHESTPLATE, "Plastron en fer", null);
		armor[1] = new ItemBuilder(Material.IRON_LEGGINGS, "Jambières en fer fer", null);
		armor[0] = new ItemBuilder(Material.IRON_BOOTS, "Bottes en fer", null);
		for (ItemStack item : armor) {
			item.addEnchantment(Enchantment.DURABILITY, 3);
			item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		}
		ItemStack sword = new ItemBuilder(Material.IRON_SWORD, "Épée en fer", null);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		for (ItemStack item : stuff)
			item.addEnchantment(Enchantment.DURABILITY, 3);
		ItemStack steak = new ItemBuilder(new ItemStack(Material.COOKED_BEEF, 32, (short) 0), "Steak", new String());
		ItemStack healPotions = new PotionBuilder(Material.SPLASH_POTION, 6, PotionType.INSTANT_HEAL, false, true,
				"Potion de Soins instantanés jetable II");
		ItemStack levitationPotions = new PotionBuilder(Material.SPLASH_POTION, 6, PotionEffectType.LEVITATION, 6*20, 1,
				"Potion de Lévitation jetable II",Color.WHITE);
		RefillItems healPotionRefill = new RefillItems(healPotions, 2, healPotions.getAmount());
		RefillItems levitationPotionsRefill = new RefillItems(levitationPotions, 2, levitationPotions.getAmount());
		RefillItems steakRefill = new RefillItems(steak, 1, steak.getAmount());
		refillItems.add(healPotionRefill);
		refillItems.add(levitationPotionsRefill);
		refillItems.add(steakRefill);
		stuff.add(sword);
		stuff.add(steak);
		stuff.add(healPotions);
		stuff.add(levitationPotions);
	}

	@Override
	public void setItemDescription() {
		description.add("§a» §3Contenu:");
		description.add("§8- §7x1 Casque en fer (Protection 1)");
		description.add("§8- §7x1 Plastron en fer (Protection 1)");
		description.add("§8- §7x1 Jambières en fer (Protection 1)");
		description.add("§8- §7x1 Bottes en fer (Protection 1)");
		description.add("§8- §7x1 Epée en fer (Tranchant 2)");
		description.add("§8- §7x32 Steak");
		description.add("§8- §7x6 Potions de soin instantané jetable II");
		description.add("§a» §3Cliquez pour sélectionner ce kit.");
		menuItem = new ItemBuilder(Material.IRON_SWORD, 1, (short) 0, menuItem.getItemMeta().getDisplayName(), description);

	}

	@Override
	public void customAdd(PBPlayer player) {
		//Nothing
	}

}
