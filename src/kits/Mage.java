package kits;

import java.util.HashMap;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import manager.PBPlayer;
import utils.ItemBuilder;
import utils.PotionBuilder;
import utils.RefillItems;

public class Mage extends Kit {

	public static HashMap<Entity, ItemStack> FireballAndLaunchers = new HashMap<Entity, ItemStack>();
	public static int fireballDamages = 4;

	public Mage() {
		super();
		name = "Mage";
		menuItem = new ItemBuilder(Material.FIREBALL, 1, (short) 0, "§6Kit: Mage", null);
		UnitPrice = 30;
		FinalPrice = 500;
		this.slot = 5;
		setStuff();
		setItemDescription();
	}

	public void setStuff() {
		armor[3] = new ItemBuilder(Material.LEATHER_HELMET, "Chapeau en cuir", null);
		armor[2] = new ItemBuilder(Material.LEATHER_CHESTPLATE, "Tunique en cuir", null);
		armor[1] = new ItemBuilder(Material.LEATHER_LEGGINGS, "Pantalon en cuir", null);
		armor[0] = new ItemBuilder(Material.LEATHER_BOOTS, "Bottes en cuir", null);
		for (ItemStack item : armor) {
			item.addEnchantment(Enchantment.DURABILITY, 3);
			item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		}
		ItemStack fireball = new ItemBuilder(Material.FIREBALL, "Boule de feu", null);
		ItemStack stick = new ItemBuilder(Material.STICK, "Répulseur", null);
		stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
		ItemStack steak = new ItemBuilder(Material.COOKED_BEEF, 32, (short) 0, "Steak", null);
		ItemStack levitationPotions = new PotionBuilder(Material.SPLASH_POTION, 4, PotionEffectType.LEVITATION, 6 * 20,
				1, "Potion de Lévitation jetable II", Color.WHITE);
		ItemStack healPotions = new PotionBuilder(Material.SPLASH_POTION, 6, PotionType.INSTANT_HEAL, false, true,
				"Potion de Soins instantané jetable II");
		ItemStack damagePotions = new PotionBuilder(Material.SPLASH_POTION, 4, PotionType.INSTANT_DAMAGE, false, false,
				"Potion de Dégats instantanés jetable");
		ItemStack poisonPotions = new PotionBuilder(Material.SPLASH_POTION, 2, PotionEffectType.POISON, 10 * 20, 0,
				"Potion de Poison jetable", Color.GREEN);
		RefillItems steakRefill = new RefillItems(steak, 1, steak.getAmount());
		RefillItems poisonPotionRefill = new RefillItems(poisonPotions, 1, poisonPotions.getAmount());
		RefillItems healPotionRefill = new RefillItems(healPotions, 1, healPotions.getAmount());
		RefillItems levitationPotionsRefill = new RefillItems(levitationPotions, 1, levitationPotions.getAmount());
		RefillItems damagePotionsRefill = new RefillItems(damagePotions, 1, damagePotions.getAmount());
		refillItems.add(steakRefill);
		refillItems.add(poisonPotionRefill);
		refillItems.add(healPotionRefill);
		refillItems.add(levitationPotionsRefill);
		refillItems.add(damagePotionsRefill);
		stuff.add(fireball);
		stuff.add(stick);
		stuff.add(steak);
		stuff.add(levitationPotions);
		stuff.add(healPotions);
		stuff.add(damagePotions);
		stuff.add(poisonPotions);
	}

	@Override
	public void setItemDescription() {
		description.add("§a» §3Contenu:");
		description.add("§8- §7x1 Chapeau en cuir (Protection 2)");
		description.add("§8- §7x1 Tunique en cuir (Protection 2)");
		description.add("§8- §7x1 Pantalon en cuir (Protection 2)");
		description.add("§8- §7x1 Bottes en cuir (Protection 2)");
		description.add("§8- §7x1 Boule de feu (2sec cooldown)");
		description.add("§8- §7x1 Répulseur (Recul 2)");
		description.add("§8- §7x32 Steak");
		description.add("§8- §7x1 Potion de Lévitation jetable");
		description.add("§8- §7x6 Potions de Soins instantanés jetable II");
		description.add("§8- §7x4 Potions de Dégats instantanés jetable");
		description.add("§8- §7x2 Potions de Poison jetable");
		description.add("§a» §3Cliquez pour sélectionner ce kit.");
		menuItem = new ItemBuilder(Material.FIREBALL, 1, (short) 0, menuItem.getItemMeta().getDisplayName(),
				description);

	}

	public static void launchFireBall(Player player, ItemStack item) {
		Location loc = player.getEyeLocation();
		Vector dir = loc.getDirection();
		loc.add(dir.normalize());
		Fireball fireball = (Fireball) player.launchProjectile(Fireball.class);
		fireball.setShooter(player);
		fireball.setIsIncendiary(true);
		fireball.setYield(3);
		fireball.setDirection(dir);
		fireball.setFireTicks(0);
		fireball.setBounce(false);
		fireball.setVelocity(dir.normalize().multiply(-0.1));
		FireballAndLaunchers.put(fireball, item);
	}

	public static void explosion(Location loc, float radius, Entity source) {
		for (Entity ent : loc.getWorld().getNearbyEntities(loc, (double) radius, (double) radius, (double) radius)) {
			if (ent instanceof LivingEntity) {
				if (ent.getLocation().distance(loc) <= radius) {
					double distance = ent.getLocation().distance(loc);
					Vector vect = ent.getLocation().subtract(loc).toVector().normalize();
					if (distance >= 1)
						ent.setVelocity(vect.multiply(1.4 / distance));
					else
						ent.setVelocity(vect.multiply(1.4));
				}
			}
		}
		FireballAndLaunchers.remove(source);
	}

	@Override
	public void customAdd(PBPlayer player) {
		// TODO Auto-generated method stub

	}
}
