package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import kits.Kit;
import launcher.PvpBox;
import menu.MainMenu;

public class PBPlayer {

	private Player player;
	private Kit kit = null;
	private FightStats fightStats = null;
	private ArrayList<Statistics> pStats = new ArrayList<Statistics>();
	private ArrayList<KitPreference> preferences = new ArrayList<KitPreference>();
	private ArrayList<Pet> pets = new ArrayList<Pet>();
	private Team playerTeam = null;
	private int points = 0;
	private PvpBox pvpBox = PvpBox.getInstance();

	public PBPlayer(Player player) {
		this.player = player;
		setFightStats(new FightStats());
		for (Kit kit : PvpBox.kits) {
			Statistics kitStats = new Statistics(player, kit);
			if (kit.getName() == "Guerrier" || kit.getName() == "Archer")
				kitStats.setAvailability(true);
			preferences.add(new KitPreference(this, kit));
			pStats.add(kitStats);
		}
	}

	public Kit getKit() {
		return this.kit;
	}

	public void setKit(Kit kit) {
		this.kit = kit;
	}

	public List<Statistics> getStats() {
		return this.pStats;
	}

	public Statistics getKitStats(Kit kit) {
		for (Statistics statistics : pStats) {
			if (statistics.getKit() == kit)
				return statistics;
		}
		return null;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void createTeam(String name) {
		this.playerTeam = new Team(name, this);
	}

	public Team getTeam() {
		return this.playerTeam;
	}

	public boolean addUnitKit(Kit kit) {
		if (getKitStats(kit).getUnits() < 3) {
			getKitStats(kit).setUnits(getKitStats(kit).getUnits() + 1);
			return true;
		} else
			return false;
	}

	public boolean removeUnitKit(Kit kit) {
		if (getKitStats(kit).getUnits() > 0) {
			getKitStats(kit).setUnits(getKitStats(kit).getUnits() - 1);
			return true;
		} else
			return false;
	}

	public void buyUnitKit(Kit kit) {
		if (this.points < kit.getUnitPrice() && getKitStats(kit).getUnits() < 3) {
			//com.elenox.api.ElenoxAPI.getElenoxAPI().getActionBar().sendActionBar(player,
			//		"Vous n'avez pas asser de points pour dévérouiller ce kit");
			return;
		}
		if (getKitStats(kit).getUnits() >= 3) ;
			//com.elenox.api.ElenoxAPI.getElenoxAPI().getActionBar().sendActionBar(player,
			//		"Vous disposez déja de 3 unités de ce kit");	
		else{
			addUnitKit(kit);
			setPoints(getPoints() - kit.getUnitPrice());
		}
	}

	public void buyFinalKit(Kit kit) {
		if (this.points < kit.getFinalPrice() && !getKitStats(kit).getAvailability()) {
			//com.elenox.api.ElenoxAPI.getElenoxAPI().getActionBar().sendActionBar(player,
			//		"Vous n'avez pas asser de points pour dévérouiller ce kit");
			return;
		}
		if (getKitStats(kit).getAvailability())
			return;
		this.setPoints(this.points - kit.getFinalPrice());
		for (Statistics stats : pStats) {
			if (stats.getKit() == kit)
				stats.setAvailability(true);
		}
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
		player.setLevel(points);
	}

	public void addPoints(int points) {
		this.points += points;
		player.setLevel(this.points);
	}

	public void setPvpMode(Kit kit) {
		setKit(kit);
		setStuff(kit);
		setEffects(kit);
		getFightStats().setLivingPvpTime(0);
		getKitStats(kit).addSelectedKit();
		pvpBox.getPvpPlayers().add(pvpBox.get(player));
		player.setGameMode(GameMode.SURVIVAL);
		player.teleport(PvpBox.getInstance().pvpSpawnLocation());
		setKitCustomAdd(kit);
	}
	
	@SuppressWarnings("deprecation")
	public void removePvpMode(){
		setKit(null);
		pvpBox.getPvpPlayers().remove(pvpBox.get(player));
		this.clearPets();
		for(PotionEffect effect : player.getPlayer().getActivePotionEffects())
			player.removePotionEffect(effect.getType());
		player.setHealth(player.getMaxHealth());
		new MainMenu(player);
	}
	
	public int getKills() {
		int total = 0;
		for (Statistics stat : pStats)
			total += stat.getKills();
		return total;
	}

	public int getDeaths() {
		int total = 0;
		for (Statistics stat : pStats)
			total += stat.getDeaths();
		return total;
	}

	public void setStuff(Kit kit) {
		// clear inventory
		player.getInventory().clear();
		player.getInventory().setHelmet(new ItemStack(Material.AIR));
		player.getInventory().setChestplate(new ItemStack(Material.AIR));
		player.getInventory().setLeggings(new ItemStack(Material.AIR));
		player.getInventory().setBoots(new ItemStack(Material.AIR));
		// kit stuff
		for (Entry<Integer, ItemStack> slot : getKitPref(kit).itemPositions.entrySet())
			player.getInventory().setItem(slot.getKey(), slot.getValue());
		player.getInventory().setArmorContents(kit.getArmor());
	}

	public void deleteTeam() {
		playerTeam = null;
	}

	public KitPreference getKitPref(Kit kit) {
		for (KitPreference kp : preferences)
			if (kp.kit == kit)
				return kp;
		return null;
	}

	public void setEffects(Kit kit) {
		for (PotionEffect effect : kit.getConstantEffects())
			player.addPotionEffect(effect);
	}

	public void setKitCustomAdd(Kit kit) {
		kit.customAdd(this);
	}

	public FightStats getFightStats() {
		return fightStats;
	}

	public void setFightStats(FightStats fightStats) {
		this.fightStats = fightStats;
	}

	public ArrayList<Pet> getPets() {
		return pets;
	}

	public void clearPets() {
		for (Pet pet : pets)
			pet.getCreature().remove();
		pets.clear();
	}
}