package kits;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import manager.PBPlayer;
import manager.Pet;
import utils.ItemBuilder;
import utils.RefillItems;

public class Chasseur extends Kit {

	private final static int MAXPETS = 1;

	public Chasseur() {
		super();
		name = "Chasseur";
		menuItem = new ItemBuilder(Material.LEASH, 1, (short) 0, "§6Kit: Chasseur", null);
		UnitPrice = 30;
		FinalPrice = 500;

		this.slot = 7;
		setStuff();
		setItemDescription();
	}

	@SuppressWarnings("deprecation")
	public void setStuff() {
		armor[3] = new ItemBuilder(Material.CHAINMAIL_HELMET, "Casque de mailles", null);
		armor[2] = new ItemBuilder(Material.CHAINMAIL_CHESTPLATE, "Cotte de mailles", null);
		armor[1] = new ItemBuilder(Material.CHAINMAIL_LEGGINGS, "Jambières de mailles", null);
		armor[0] = new ItemBuilder(Material.CHAINMAIL_BOOTS, "Bottes de mailles", null);
		for (ItemStack item : armor) {
			item.addEnchantment(Enchantment.DURABILITY, 3);
			item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		}

		ItemStack sword = new ItemBuilder(Material.WOOD_SWORD, "Épée en bois", null);
		sword.addEnchantment(Enchantment.KNOCKBACK, 2);
		sword.addEnchantment(Enchantment.DURABILITY, 3);

		ItemStack bow = new ItemBuilder(Material.BOW, "Arc", null);
		bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
		bow.addEnchantment(Enchantment.DURABILITY, 3);

		ItemStack steak = new ItemBuilder(new ItemStack(Material.COOKED_BEEF, 32, (short) 0), "Steak", new String());
		ItemStack arrows = new ItemBuilder(Material.ARROW, 64, (short) 0, "Flèche", null);

		ItemStack poisonArrows = new ItemBuilder(Material.TIPPED_ARROW, 8, (short) 0, "Flèche empoisonnée", null);
		PotionMeta meta = (PotionMeta) poisonArrows.getItemMeta();
		meta.setColor(Color.GREEN);
		meta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 8 * 20, 0), true);
		poisonArrows.setItemMeta(meta);

		ItemStack slowArrows = new ItemBuilder(Material.TIPPED_ARROW, 6, (short) 0, "Flèche glaciale", null);
		PotionMeta meta2 = (PotionMeta) slowArrows.getItemMeta();
		meta2.setColor(Color.NAVY);
		meta2.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 6 * 20, 2), true);
		slowArrows.setItemMeta(meta2);

		ItemStack wolf = new ItemStack(Material.MONSTER_EGG, 1, EntityType.WOLF.getTypeId());
		RefillItems arrowsRefill = new RefillItems(arrows,10,arrows.getAmount());
		RefillItems poisonArrowsRefill = new RefillItems(poisonArrows, 1, poisonArrows.getAmount());
		RefillItems slowArrowsRefill = new RefillItems(slowArrows, 1, slowArrows.getAmount());
		RefillItems steakRefill = new RefillItems(steak, 1, steak.getAmount());
		refillItems.add(steakRefill);
		refillItems.add(arrowsRefill);
		refillItems.add(poisonArrowsRefill);
		refillItems.add(slowArrowsRefill);
		stuff.add(sword);
		stuff.add(bow);
		stuff.add(steak);
		stuff.add(arrows);
		stuff.add(poisonArrows);
		stuff.add(slowArrows);
		stuff.add(wolf);
	}

	@SuppressWarnings("deprecation")
	public static void spawnWolf(PBPlayer player) {
		if (player.getPets().size() >= MAXPETS) {
			player.getPets().get(0).getCreature().remove();
			player.getPets().remove(0);
		}
		Pet pet = new Pet(player, EntityType.WOLF, "Loup de " + player.getPlayer().getName(),
				player.getPlayer().getLocation(), true);
		pet.getCreature().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
		//pet.getCreature().addPotionEffect(
		//		new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0, false, false));
		pet.getCreature().setMaxHealth(20);
		pet.getCreature().setHealth(pet.getCreature().getMaxHealth());
		player.getPets().add(pet);
	}

	@Override
	public void setItemDescription() {
		description.add("§a» §3Contenu:");
		description.add("§8- §7x1 Casque de mailles");
		description.add("§8- §7x1 Cotte de mailles");
		description.add("§8- §7x1 Jambières de mailles");
		description.add("§8- §7x1 Bottes de mailles");
		description.add("§8- §7x1 Épée en bois (Recul 2)");
		description.add("§8- §7x1 Arc");
		description.add("§8- §7x64 Flèches");
		description.add("§8- §7x6 Flèches empoisonnées (Poison 2 : 8 sec)");
		description.add("§8- §7x8 Flèches glaciales (Lenteur 3 : 6 sec)");
		description.add("§8- §7x32 Steak");
		description.add("§8- §7x1 Invocation : Loup (x1 Max / Vitesse 2)");
		description.add("§a» §3Cliquez pour sélectionner ce kit.");
		menuItem = new ItemBuilder(Material.LEASH, 1, (short) 0, menuItem.getItemMeta().getDisplayName(), description);
	}

	@Override
	public void customAdd(PBPlayer player) {
		/*
		 * for (int i = 0; i < 3; i++) { Pet pet = new Pet(player,
		 * EntityType.WOLF, "Loup de " + player.getPlayer().getName(),
		 * player.getPlayer().getLocation(), true); player.getPets().add(pet); }
		 */
	}

}
