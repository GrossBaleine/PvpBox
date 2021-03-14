package kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.Dye;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import launcher.PvpBox;
import manager.PBPlayer;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import utils.ItemBuilder;
import utils.PotionBuilder;
import utils.RefillItems;

public class Vampire extends Kit {

	public static ArrayList<PBPlayer> playersTransformed = new ArrayList<PBPlayer>();

	public Vampire() {
		super();
		name = "Vampire";
		menuItem = new ItemBuilder(new ItemStack(new Dye(DyeColor.RED).toItemStack(1)), "§6Kit: Vampire", new String());
		UnitPrice = 30;
		FinalPrice = 500;
		this.slot = 6;
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
		ItemStack sword = new ItemBuilder(Material.STONE_SWORD, "Épée en pierre", null);
		ItemStack steak = new ItemBuilder(Material.COOKED_BEEF, 32, (short) 0, "Steak", null);
		
		ItemStack healPotions = new PotionBuilder(Material.SPLASH_POTION, 4, PotionType.INSTANT_HEAL, false, false,
				"Potion de Soins instantanés jetable");
		ItemStack corruptionPotions = new PotionBuilder(Material.SPLASH_POTION, 4, PotionEffectType.CONFUSION, 10 * 20,
				4, "Potion de Corruption jetable", Color.PURPLE);
		PotionMeta meta = (PotionMeta) corruptionPotions.getItemMeta();
		meta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5 * 20, 1), true);
		corruptionPotions.setItemMeta(meta);
		ItemStack transformation = new ItemBuilder(Material.FERMENTED_SPIDER_EYE, "Métamorphose", null);
		sword.addEnchantment(Enchantment.DURABILITY, 3);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		stuff.add(sword);
		stuff.add(steak);
		stuff.add(healPotions);
		stuff.add(corruptionPotions);
		stuff.add(transformation);
		RefillItems corrutionPotionsRefill = new RefillItems(corruptionPotions, 1 , corruptionPotions.getAmount());
		RefillItems healPotionsRefill = new RefillItems(healPotions, 1 , healPotions.getAmount());
		refillItems.add(healPotionsRefill);
		refillItems.add(corrutionPotionsRefill);
	}

	@Override
	public void setItemDescription() {
		description.add("§a» §3Contenu:");
		description.add("§8- §7x1 Chapeau en cuir (Protection2)");
		description.add("§8- §7x1 Tunique en cuir (Protection2)");
		description.add("§8- §7x1 Pantalon en cuir (Protection2)");
		description.add("§8- §7x1 Bottes en cuir (Protection2)");
		description.add("§8- §7x1 Épée en pierre(Tranchant 2)");
		description.add("§8- §7x32 Steak");
		description.add("§8- §7x4 Potion de Soin instantané jetable");
		description.add("§8- §7x4 Potion de Corruption jetable (Aveuglement + Nausée)");
		description.add("§8- §7x1 Transformation : Chauve-souris (10sec cooldown)");
		description.add("§8- §7 Passif : Gain de potion de soin tous les 3 coups");
		description.add("§8- §7 /!\\ One-shot possible lorsque transformé");
		description.add("§a» §3Cliquez pour sélectionner ce kit.");
		menuItem = new ItemBuilder(new ItemStack(new Dye(DyeColor.RED).toItemStack(1)), menuItem.getItemMeta().getDisplayName(), description);
	}

	public static void transform(PBPlayer pbPlayer) {
		Player player = pbPlayer.getPlayer();
		player.setAllowFlight(true);
		player.setFlying(true);
		player.setFlySpeed(0.05F);
		MobDisguise bat = new MobDisguise(DisguiseType.BAT);
		bat.setModifyBoundingBox(true);
		DisguiseAPI.disguiseToAll(player, bat);
		playersTransformed.add(pbPlayer);
		PotionEffect pe = new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, true);
		player.getPlayer().addPotionEffect(pe);
	}

	public static void unTransform(PBPlayer pbPlayer) {
		Player player = pbPlayer.getPlayer();
		player.setFlying(false);
		player.setAllowFlight(false);
		DisguiseAPI.undisguiseToAll(player);
		playersTransformed.remove(pbPlayer);
		player.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
	}

	public static void attacksHeal(PBPlayer pbPlayer) {
		if (pbPlayer.getFightStats().getHitsGiven() != 0 && pbPlayer.getFightStats().getHitsGiven() % 3 == 0) {
			Inventory inv = pbPlayer.getPlayer().getInventory();
			if (inv.contains(Material.SPLASH_POTION)) {
				HashMap<Integer, ? extends ItemStack> potions = inv.all(Material.SPLASH_POTION);
				for (Entry<Integer, ? extends ItemStack> p : potions.entrySet()) {
					if (p.getValue().hasItemMeta() && p.getValue().getItemMeta().getDisplayName() == "Fiole de sang") {
						if (p.getValue().getAmount() < 10) {
							PotionMeta meta = (PotionMeta) p.getValue().getItemMeta();
							if (meta.getBasePotionData().getType() == PotionType.INSTANT_HEAL) {
								ItemStack pot = p.getValue();
								pot.setAmount(p.getValue().getAmount() + 1);
								inv.setItem(p.getKey().intValue(), pot);
								return;
							}
						} else
							return;
					}
				}
			}
			ItemStack healPotions = new PotionBuilder(Material.SPLASH_POTION, 1, PotionType.INSTANT_HEAL, false, true,
					"Fiole de sang");
			inv.addItem(healPotions);
		}
	}

	public static void effectOnKill(PBPlayer pbPlayer) {
		Player player = pbPlayer.getPlayer();
		if (player.getHealth() <= 18)
			player.setHealth(player.getHealth() + 2);
		else
			player.setHealth(20);
		player.setFoodLevel(20);
		player.setSaturation(20);
	}

	public static void vampireKill(PBPlayer killer) {
		if (killer.getKit().getName() == "Vampire") {
			Vampire.effectOnKill(killer);
		}
	}

	public static void vampireDeathRessetDay(PBPlayer died) {
		if (died.getKit() != null && died.getKit().getName() == "Vampire") {
			for (PBPlayer players : PvpBox.getInstance().getPvpPlayers()) {
				if (players.getKit().getName() == "Vampire" && players != died)
					return;
			}
			died.getPlayer().getWorld().setTime(3000);
		}
	}

	@Override
	public void customAdd(PBPlayer player) {
		player.getPlayer().getWorld().setTime(15000);
	}

}
