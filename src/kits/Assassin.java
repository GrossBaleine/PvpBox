package kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import launcher.PvpBox;
import manager.PBPlayer;
import net.minecraft.server.v1_12_R1.DataWatcherObject;
import net.minecraft.server.v1_12_R1.DataWatcherRegistry;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import utils.ItemBuilder;
import utils.PotionBuilder;
import utils.RefillItems;

public class Assassin extends Kit {

	public static HashMap<PBPlayer, ArrayList<PacketPlayOutEntityEquipment>> invisiblePlayers = new HashMap<PBPlayer, ArrayList<PacketPlayOutEntityEquipment>>();
	private final static int radius = 8;

	public Assassin() {
		super();
		name = "Assassin";
		menuItem = new ItemBuilder(Material.EYE_OF_ENDER, 1, (short) 0, "§6Kit: Assassin", null);
		UnitPrice = 30 ;
		FinalPrice = 500 ;
		
		setItemDescription();
		setStuff();
		this.slot = 3;
	}

	public void setStuff() {
		armor[3] = new ItemBuilder(Material.LEATHER_HELMET, "Chapeau en cuir", null);
		armor[2] = new ItemBuilder(Material.LEATHER_CHESTPLATE, "Tunique en cuir", null);
		armor[1] = new ItemBuilder(Material.LEATHER_LEGGINGS, "Pantalon en cuir", null);
		armor[0] = new ItemBuilder(Material.LEATHER_BOOTS, "Bottes en cuir", null);
		for (ItemStack item : armor) {
			item.addEnchantment(Enchantment.DURABILITY, 3);
			item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		}
		ItemStack axe = new ItemBuilder(Material.STONE_AXE, "Hache en pierre", null);
		ItemStack steak = new ItemBuilder(Material.COOKED_BEEF, 32, (short) 0, "Steak", null);
		ItemStack poisonPotions = new PotionBuilder(Material.SPLASH_POTION, 2, PotionEffectType.POISON, 10 * 20, 0,
				"Potion de Poison jetable", Color.GREEN);
		ItemStack speedPotion = new PotionBuilder(Material.SPLASH_POTION, 1, PotionType.SPEED, false, false,
				"Potion de Rapidité jetable (3min)");
		ItemStack invisibility = new ItemBuilder(Material.EYE_OF_ENDER, "Invisibilité", null);
		axe.addEnchantment(Enchantment.DURABILITY, 3);
		axe.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		RefillItems speedPotionRefill = new RefillItems(speedPotion, 1, speedPotion.getAmount());
		RefillItems poisonPotionRefill = new RefillItems(poisonPotions, 1, poisonPotions.getAmount());
		RefillItems steakRefill = new RefillItems(steak, 1, steak.getAmount());
		refillItems.add(speedPotionRefill);
		refillItems.add(poisonPotionRefill);
		refillItems.add(steakRefill);
		stuff.add(axe);
		stuff.add(steak);
		stuff.add(poisonPotions);
		stuff.add(speedPotion);
		stuff.add(invisibility);
	}

	@Override
	public void setItemDescription() {
		description.add("§a» §3Contenu:");
		description.add("§8- §7x1 Chapeau en cuir (Protection 1)");
		description.add("§8- §7x1 Tunique en cuir (Protection 1)");
		description.add("§8- §7x1 Pantalon en cuir (Protection 1)");
		description.add("§8- §7x1 Bottes en cuir (Protection 1)");
		description.add("§8- §7x1 Hache en pierre (Tranchant 1)");
		description.add("§8- §7x32 Steak");
		description.add("§8- §7x2 Potions de Poison jetable (6sec)");
		description.add("§8- §7x1 Potion de Rapidité jetable (3min)");
		description.add("§8- §7x1 Invisibilité activable (15sec cooldown)");
		description.add("§a» §3Cliquez pour sélectionner ce kit.");

		menuItem = new ItemBuilder(Material.EYE_OF_ENDER, 1, (short) 0, menuItem.getItemMeta().getDisplayName(), description);
	}

	public static void setInvisibility(PBPlayer player) {
		player.getPlayer()
			.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, true, false));
		player.getPlayer()
			.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
		player.getPlayer()
			.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0, true, false));
		ArrayList<PacketPlayOutEntityEquipment> packets = hidingStuffPackets(player.getPlayer());
		Assassin.invisiblePlayers.put(player, packets);
		player.getPlayer().setSilent(true);
		removeArrowsOnPlayer(player.getPlayer());
	}

	public static void removeInvisibility(PBPlayer invisible) {
		invisible.getPlayer()
			.removePotionEffect(invisible.getPlayer().getPotionEffect(PotionEffectType.INVISIBILITY).getType());
		invisible.getPlayer()
			.removePotionEffect(invisible.getPlayer().getPotionEffect(PotionEffectType.NIGHT_VISION).getType());
		invisible.getPlayer()
			.removePotionEffect(invisible.getPlayer().getPotionEffect(PotionEffectType.INCREASE_DAMAGE).getType());
		ArrayList<PacketPlayOutEntityEquipment> packets = showingStuffPackets(invisible.getPlayer());
		Assassin.invisiblePlayers.remove(invisible);
		for (PBPlayer pbPlayer : PvpBox.getInstance().getPBPlayers()) {
			if (pbPlayer != invisible) {
				sendPacket(pbPlayer, packets);
			}
		}
		potionParticles(invisible.getPlayer(), true);
		invisible.getPlayer().setSilent(false);
	}

	public static void sendInvisibilityPackets() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) PvpBox.getInstance(), new Runnable() {

			private double angle = 0;
			private boolean ressetvar = false;

			public void run() {
				for (Entry<PBPlayer, ArrayList<PacketPlayOutEntityEquipment>> invisiblePlayer : Assassin.invisiblePlayers
						.entrySet()) {
					potionParticles(invisiblePlayer.getKey().getPlayer(), false);
					ArrayList<PacketPlayOutEntityEquipment> packets = invisiblePlayer.getValue();
					for (PBPlayer pbPlayer : PvpBox.getInstance().getPBPlayers()) {
						if (pbPlayer != invisiblePlayer.getKey() && pbPlayer.getKit() != null) {
							if (distance(pbPlayer, invisiblePlayer.getKey()) > radius) {
								sendPacket(pbPlayer, packets);
							} else {
								displayInvisibleOnRange(invisiblePlayer.getKey(), pbPlayer);
							}
						} else {
							if (angle >= 10) {
								displayViewAura(invisiblePlayer.getKey());
								ressetvar = true;
							}
						}
					}
				}
				if (ressetvar)
					angle = 0;
				ressetvar = false;
				angle += 1;
			}
		}, 0, 1);

	}

	public static void removeArrowsOnPlayer(Player player) {
		((CraftPlayer) player).getHandle().getDataWatcher()
				.set(new DataWatcherObject<Integer>(10, DataWatcherRegistry.b), Integer.valueOf(0));
	}

	public static void potionParticles(Player player, boolean visible) {
		for (PotionEffect potionEffect : player.getActivePotionEffects()) {
			if(potionEffect.hasParticles() != visible){
				PotionEffect newEffect = new PotionEffect(potionEffect.getType(), potionEffect.getDuration(),
						potionEffect.getAmplifier(), true, visible);
				player.removePotionEffect(potionEffect.getType());
				player.addPotionEffect(newEffect);
			}
		}
	}

	public static void displayViewAura(PBPlayer player) {
		ArrayList<PacketPlayOutWorldParticles> particles = new ArrayList<PacketPlayOutWorldParticles>();
		final Location loc = player.getPlayer().getLocation();
		for (double angle = 0; angle < 2 * Math.PI; angle += Math.PI / 50) {
			Location circle = new Location(loc.getWorld(), loc.getX() + Math.cos(angle) * radius, loc.getY(),
					loc.getZ() + Math.sin(angle) * radius);
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, false,
					(float) circle.getX(), (float) circle.getY(), (float) circle.getZ(), 0, 0, 0, 0, 1);
			particles.add(packet);
		}
		for (PacketPlayOutWorldParticles packet : particles) {
			CraftPlayer craftReciever = (CraftPlayer) player.getPlayer();
			EntityPlayer handle = craftReciever.getHandle();
			handle.playerConnection.sendPacket(packet);
		}
	}

	public static void displayInvisibleOnRange(PBPlayer invisible, PBPlayer viewer) {
		sendPacket(viewer, showingStuffPackets(invisible.getPlayer()));
	}

	public static ArrayList<PacketPlayOutEntityEquipment> showingStuffPackets(Player player) {
		PacketPlayOutEntityEquipment handPacket = new PacketPlayOutEntityEquipment(player.getPlayer().getEntityId(),
				EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(player.getPlayer().getInventory().getItemInMainHand()));
		PacketPlayOutEntityEquipment secHandPacket = new PacketPlayOutEntityEquipment(player.getPlayer().getEntityId(),
				EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(player.getPlayer().getInventory().getItemInOffHand()));
		PacketPlayOutEntityEquipment helmetPacket = new PacketPlayOutEntityEquipment(player.getPlayer().getEntityId(),
				EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(player.getPlayer().getInventory().getHelmet()));
		PacketPlayOutEntityEquipment chestPacket = new PacketPlayOutEntityEquipment(player.getPlayer().getEntityId(),
				EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(player.getPlayer().getInventory().getChestplate()));
		PacketPlayOutEntityEquipment legPacket = new PacketPlayOutEntityEquipment(player.getPlayer().getEntityId(),
				EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(player.getPlayer().getInventory().getLeggings()));
		PacketPlayOutEntityEquipment bootsPacket = new PacketPlayOutEntityEquipment(player.getPlayer().getEntityId(),
				EnumItemSlot.FEET, CraftItemStack.asNMSCopy(player.getPlayer().getInventory().getBoots()));
		ArrayList<PacketPlayOutEntityEquipment> packets = new ArrayList<PacketPlayOutEntityEquipment>();
		packets.add(handPacket);
		packets.add(secHandPacket);
		packets.add(helmetPacket);
		packets.add(chestPacket);
		packets.add(legPacket);
		packets.add(bootsPacket);
		return packets;
	}

	public static ArrayList<PacketPlayOutEntityEquipment> hidingStuffPackets(Player player) {
		PacketPlayOutEntityEquipment handPacket = new PacketPlayOutEntityEquipment(player.getPlayer().getEntityId(),
				EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)));
		PacketPlayOutEntityEquipment secHandPacket = new PacketPlayOutEntityEquipment(player.getPlayer().getEntityId(),
				EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)));
		PacketPlayOutEntityEquipment helmetPacket = new PacketPlayOutEntityEquipment(player.getPlayer().getEntityId(),
				EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)));
		PacketPlayOutEntityEquipment chestPacket = new PacketPlayOutEntityEquipment(player.getPlayer().getEntityId(),
				EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)));
		PacketPlayOutEntityEquipment legPacket = new PacketPlayOutEntityEquipment(player.getPlayer().getEntityId(),
				EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)));
		PacketPlayOutEntityEquipment bootsPacket = new PacketPlayOutEntityEquipment(player.getPlayer().getEntityId(),
				EnumItemSlot.FEET, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)));
		ArrayList<PacketPlayOutEntityEquipment> packets = new ArrayList<PacketPlayOutEntityEquipment>();
		packets.add(handPacket);
		packets.add(secHandPacket);
		packets.add(helmetPacket);
		packets.add(chestPacket);
		packets.add(legPacket);
		packets.add(bootsPacket);
		return packets;
	}

	public static void sendPacket(PBPlayer pbPlayer, ArrayList<PacketPlayOutEntityEquipment> packets) {
		for (PacketPlayOutEntityEquipment packet : packets) {
			CraftPlayer craftReciever = (CraftPlayer) pbPlayer.getPlayer();
			EntityPlayer handle = craftReciever.getHandle();
			handle.playerConnection.sendPacket(packet);
		}
	}

	public static double distance(PBPlayer player1, PBPlayer player2) {
		double distance = player1.getPlayer().getLocation().distance(player2.getPlayer().getLocation());
		return distance;
	}

	@Override
	public void customAdd(PBPlayer player) {
		// Nothing
	}
}
