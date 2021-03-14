package launcher;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import commands.CommandManager;
import kits.Archer;
import kits.Assassin;
import kits.Cavalier;
import kits.Chasseur;
import kits.Guerrier;
import kits.Invocateur;
import kits.Kit;
import kits.Mage;
import kits.Tank;
import kits.Vampire;
import listener.ListenerManager;
import manager.FightStats;
import manager.PBPlayer;
import manager.Pet;
import manager.ProtectionArea;
import manager.RestrictedArea;
import utils.RefillItems;

public class PvpBox extends JavaPlugin {

	private static PvpBox main;
	public static World world;
	private static ArrayList<PBPlayer> pvpPlayers = new ArrayList<PBPlayer>();
	private static ArrayList<PBPlayer> players = new ArrayList<PBPlayer>();
	public static ArrayList<Kit> kits = new ArrayList<Kit>();
	public static ArrayList<ProtectionArea> protectionAreas = new ArrayList<ProtectionArea>();
	public static ArrayList<RestrictedArea> restrictedAreas = new ArrayList<RestrictedArea>();
	private Location spawn;
	private Location spawnPvp;
	private final String prefix = "§8[§3PvPBox§8]";

	public void onEnable() {
		main = this;
		world = Bukkit.getServer().getWorld("World");
		spawn = new Location(world, 0, 64.5, 0);
		spawnPvp = new Location(world, -1, 149.5, 1010);
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		worldGamerules(world);
		protectionAreas.add(new ProtectionArea(spawnPvp, 10));
		restrictedAreas.add(new RestrictedArea(spawnPvp, 120, 150));
		registerKits();
		new ListenerManager(this);
		new CommandManager(this).registerCommands();
		Assassin.sendInvisibilityPackets();
		Tank.hookingTask();
		FightStats.fightStatsTask();
		Pet.moveTask();
		RefillItems.refillTask();
	}

	public static PvpBox getInstance() {
		return main;
	}

	public Location getSpawnLocation() {
		return this.spawn;
	}

	public Location pvpSpawnLocation() {
		return this.spawnPvp;
	}

	public ArrayList<PBPlayer> getPvpPlayers() {
		return pvpPlayers;
	}

	public ArrayList<PBPlayer> getPBPlayers() {
		return players;
	}

	public PBPlayer get(Player player) {
		for (PBPlayer pp : players) {

			if (pp.getPlayer() == player)
				return pp;
		}
		return null;
	}

	public Kit getKit(String kitName) {
		for (Kit kit : kits) {
			if (kit.getName().equals(kitName))
				return kit;
		}
		return null;
	}

	public void broadcast(String msg) {
		for (Player pls : Bukkit.getOnlinePlayers()) {
			pls.sendMessage(msg);
		}
	}

	public void registerKits() {
		kits.add(new Guerrier());
		kits.add(new Archer());
		kits.add(new Tank());
		kits.add(new Assassin());
		kits.add(new Cavalier());
		kits.add(new Chasseur());
		kits.add(new Mage());
		//kits.add(new Invocateur());
		kits.add(new Vampire());
	}

	public void worldGamerules(World world) {
		world.setDifficulty(Difficulty.NORMAL);
		world.setGameRuleValue("doFireTicks", "false");
		world.setGameRuleValue("doMobSpawning", "false");
	}

	public String getPrefix() {
		return prefix;
	}

	public boolean isEntityProtected(LivingEntity ent) {
		for (ProtectionArea area : protectionAreas) {
			if (area.isInsideArea(ent))
				return true;
		}
		return false;
	}

}
