package kits;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import manager.PBPlayer;
import utils.ItemBuilder;
import utils.RefillItems;

public class Cavalier extends Kit {

	public static int horseCD = 20*10;
	
	public Cavalier() {
		super();
		name = "Cavalier";
		menuItem = new ItemBuilder(Material.SADDLE, 1, (short) 0, "§6Kit: Cavalier", null);
		UnitPrice = 30;
		FinalPrice = 500;
		this.slot = 4;
		setItemDescription();
		setStuff();
	}

	public void setStuff() {
		armor[3] = new ItemBuilder(Material.IRON_HELMET, "Casque en fer", null);
		armor[2] = new ItemBuilder(Material.IRON_CHESTPLATE, "Armure en fer", null);
		armor[1] = new ItemBuilder(Material.IRON_LEGGINGS, "Jambières en fer", null);
		armor[0] = new ItemBuilder(Material.IRON_BOOTS, "Bottes en fer", null);
		for (ItemStack item : armor)
			item.addEnchantment(Enchantment.DURABILITY, 3);

		ItemStack sword = new ItemBuilder(Material.IRON_SWORD, "Épée en fer", null);
		ItemStack mount = new ItemBuilder(Material.SADDLE, "Monture", null);
		ItemStack steak = new ItemBuilder(Material.COOKED_BEEF, 32, (short) 0, "Steak", null);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		sword.addEnchantment(Enchantment.DURABILITY, 3);
		stuff.add(sword);
		stuff.add(steak);
		stuff.add(mount);
		RefillItems steakRefill = new RefillItems(steak, 1, steak.getAmount());
		refillItems.add(steakRefill);
		constantEffects.add(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0, true, true));
	}

	@Override
	public void setItemDescription() {
		description.add("§a» §3Contenu:");
		description.add("§8- §7x1 Casque en fer");
		description.add("§8- §7x1 Plastron en fer");
		description.add("§8- §7x1 Jambières en fer");
		description.add("§8- §7x1 Bottes en fer");
		description.add("§8- §7x1 Épée en fer (Tranchant 1)");
		description.add("§8- §7x32 Steak");
		description.add("§8- §7x1 Monture : Invocation (15sec cooldown)");
		description.add("§8- §7Effet Lenteur1 permanant");
		description.add("§a» §3Cliquez pour sélectionner ce kit.");
		menuItem = new ItemBuilder(Material.SADDLE, 1, (short) 0, menuItem.getItemMeta().getDisplayName(), description);
	}

	@Override
	public void customAdd(PBPlayer player) {
		if (player.getPlayer().isInsideVehicle())
			player.getPlayer().getVehicle().remove();
		createMount(player.getPlayer()).addPassenger(player.getPlayer());
	}

	@SuppressWarnings("deprecation")
	public static Entity createMount(Player player) {
		Horse horse = (Horse) player.getWorld().spawnEntity(player.getLocation(), EntityType.HORSE);
		horse.setAdult();
		horse.setTamed(true);
		horse.setMaxHealth(30);
		horse.setHealth(30);
		horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.225);
		horse.getInventory().setArmor(new ItemStack(Material.DIAMOND_BARDING));
		horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		return horse;
	}

}
