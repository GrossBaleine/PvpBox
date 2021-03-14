package kits;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import launcher.PvpBox;
import manager.PBPlayer;
import utils.ItemBuilder;
import utils.PlayersTimer;
import utils.RefillItems;

public class Tank extends Kit {

	private static ArrayList<PlayersTimer> hookedPlayers = new ArrayList<PlayersTimer>();

	public Tank() {
		super();
		name = "Tank";
		menuItem = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1, (short) 0, "§6Kit: Tank", null);
		UnitPrice = 30;
		FinalPrice = 500;
		this.slot = 2;
		this.setStuff();
		this.setItemDescription();
	}

	public void setStuff() {
		armor[3] = new ItemBuilder(Material.DIAMOND_HELMET, "Casque en diamant", null);
		armor[2] = new ItemBuilder(Material.DIAMOND_CHESTPLATE, "Armure en diamant", null);
		armor[1] = new ItemBuilder(Material.DIAMOND_LEGGINGS, "Jambières en diamant", null);
		armor[0] = new ItemBuilder(Material.DIAMOND_BOOTS, "Bottes en diamant", null);
		for (ItemStack item : armor) {
			item.addEnchantment(Enchantment.DURABILITY, 3);
			item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		}
		ItemStack sword = new ItemBuilder(Material.STONE_SWORD, "Épée en pierre", null);
		ItemStack steak = new ItemBuilder(Material.COOKED_BEEF, 32, (short) 0, "Steak", null);
		ItemStack goldenApple = new ItemBuilder(Material.GOLDEN_APPLE, 2, (short) 0, "Pomme d'or", null);
		ItemStack hook = new ItemBuilder(Material.FISHING_ROD, "Grappin", null);
		sword.addEnchantment(Enchantment.DURABILITY, 3);
		hook.addEnchantment(Enchantment.DURABILITY, 3);
		stuff.add(sword);
		stuff.add(steak);
		stuff.add(goldenApple);
		stuff.add(hook);
		RefillItems goldenAppleRefill = new RefillItems(goldenApple, 1, goldenApple.getAmount());
		RefillItems steakRefill = new RefillItems(steak, 1, steak.getAmount());
		refillItems.add(goldenAppleRefill);
		refillItems.add(steakRefill);
		constantEffects.add(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0, true, true));
	}

	@Override
	public void setItemDescription() {
		description.add("§a» §3Contenu:");
		description.add("§8- §7x1 Casque en diamant (Protection 1)");
		description.add("§8- §7x1 Plastron en diamant (Protection 1)");
		description.add("§8- §7x1 Jambières en diamant (Protection 1)");
		description.add("§8- §7x1 Bottes en diamant (Protection 1)");
		description.add("§8- §7x1 Épée en pierre");
		description.add("§8- §7x32 Steak");
		description.add("§8- §7x2 Pommes d'or");
		description.add("§8- §7x1 Grappin (10sec cooldown)");
		description.add("§8- §7Effet Lenteur1 permanant");
		description.add("§a» §3Cliquez pour sélectionner ce kit.");
		menuItem = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1, (short) 0, menuItem.getItemMeta().getDisplayName(),
				description);
	}

	public static void hook(Player player, Entity caught) {
		Vector vect = player.getLocation().toVector().subtract(caught.getLocation().toVector());
		caught.setVelocity(vect.normalize().add(new Vector(0, 2, 0)));
		hookedPlayers.add(new PlayersTimer(player, caught, 20 * 1));
	}

	public static void hookingTask() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) PvpBox.getInstance(), new Runnable() {
			public void run() {
				@SuppressWarnings("unchecked")
				ArrayList<PlayersTimer> hookedCopy = (ArrayList<PlayersTimer>) hookedPlayers.clone();
				for (PlayersTimer hooked : hookedCopy) {
					Entity caught = hooked.getEntity();
					Player player = hooked.getPlayer();
					double distance = caught.getLocation().toVector().distance(player.getLocation().toVector());
					if (distance <= 2 || hooked.getTimer() <= 0) {
						caught.setVelocity(new Vector(0, 0, 0));
						hookedPlayers.remove(hooked);
					} else {
						hooked.decrement();
						Vector vect = player.getLocation().toVector().subtract(caught.getLocation().toVector());
						caught.setVelocity(vect.normalize().multiply(distance / 6));
					}
				}
				hookedCopy = hookedPlayers;
				hookedPlayers = hookedCopy;
			}
		}, 0L, 1L);
	}

	@Override
	public void customAdd(PBPlayer player) {
		// nothing
	}

}
