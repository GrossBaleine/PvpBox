package kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import manager.PBPlayer;
import utils.ItemBuilder;
import utils.RefillItems;

public class Archer extends Kit {

	public Archer() {
		super();
		name = "Archer";
		menuItem = new ItemBuilder(Material.IRON_SWORD, 1, (short) 0, "§6Kit: Archer", null);
		UnitPrice = 0;
		FinalPrice = 0;

		setItemDescription();
		setStuff();
		this.slot = 1;
	}

	public void setStuff() {
		armor[3] = new ItemBuilder(Material.CHAINMAIL_HELMET, "Casque de mailles", null);
		armor[2] = new ItemBuilder(Material.GOLD_CHESTPLATE, "Plastron en or", null);
		armor[1] = new ItemBuilder(Material.CHAINMAIL_LEGGINGS, "Jambières de mailles", null);
		armor[0] = new ItemBuilder(Material.GOLD_BOOTS, "Bottes en or", null);
		for (ItemStack item : armor) {
			item.addEnchantment(Enchantment.DURABILITY, 3);
			item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		}
		ItemStack bow = new ItemBuilder(Material.BOW, "Arc", null);
		bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
		bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
		bow.addEnchantment(Enchantment.DURABILITY, 3);

		ItemStack sword = new ItemBuilder(Material.WOOD_SWORD, "Épée en bois", null);
		sword.addEnchantment(Enchantment.KNOCKBACK, 2);
		sword.addEnchantment(Enchantment.DURABILITY, 3);

		ItemStack steak = new ItemBuilder(new ItemStack(Material.COOKED_BEEF, 32, (short) 0), "Steak", new String());
		ItemStack arrows = new ItemBuilder(Material.ARROW, 64, (short) 0, "Flèche", null);

		stuff.add(sword);
		stuff.add(bow);
		stuff.add(steak);
		stuff.add(arrows);
		RefillItems steakRefill = new RefillItems(steak, 1, steak.getAmount());
		RefillItems arrowsRefill = new RefillItems(arrows, 10, arrows.getAmount());
		refillItems.add(steakRefill);
		refillItems.add(arrowsRefill);
	}

	public void setItemDescription() {
		description.add("§a» §3Contenu:");
		description.add("§8- §7x1 Casque de mailles (Protection 1)");
		description.add("§8- §7x1 Plastron en or (Protection 1)");
		description.add("§8- §7x1 Jambières de mailles (Protection 1)");
		description.add("§8- §7x1 Bottes en or (Protection 1)");
		description.add("§8- §7x1 Épée en bois (Recul 2)");
		description.add("§8- §7x1 Arc (Puissance 1, Frappe 1)");
		description.add("§8- §7x32 Steak");
		description.add("§8- §7x64 Flèche");
		// description.add("§8- §7x1 Potion de Rapidité jetable (8min)");
		description.add("§a» §3Cliquez pour sélectionner ce kit.");
		menuItem = new ItemBuilder(Material.BOW, 1, (short) 0, menuItem.getItemMeta().getDisplayName(), description);
	}

	@Override
	public void customAdd(PBPlayer player) {
		player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true, true));
	}

}
