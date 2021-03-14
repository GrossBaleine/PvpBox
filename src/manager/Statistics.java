package manager;

import org.bukkit.entity.Player;

import kits.Kit;

public class Statistics {

	private Player player;
	private Kit kit;
	private int killsOnKit = 0;
	private int deathsOnKit = 0;
	private int nbSelectedKit = 0;
	private int units = 3;
	private boolean availability;

	public Statistics(Player player, Kit kit) {
		this.player = player;
		this.kit = kit;
		this.availability = false;
	}

	public void addKill() {
		this.killsOnKit += 1;
	}

	public void setKills(int killsNumber) {
		this.killsOnKit = killsNumber;
	}

	public int getKills() {
		return this.killsOnKit;
	}

	public void addDeath() {
		this.deathsOnKit += 1;
	}

	public void setDeaths(int deathsNumber) {
		this.deathsOnKit = deathsNumber;
	}

	public int getDeaths() {
		return this.deathsOnKit;
	}

	public void addSelectedKit() {
		this.nbSelectedKit += 1;
	}

	public void setSelectedKit(int nbSelectedKit) {
		this.nbSelectedKit = nbSelectedKit;
	}

	public int getSelectedKit() {
		return this.nbSelectedKit;
	}

	public Kit getKit() {
		return this.kit;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public boolean getAvailability() {
		return this.availability;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

}
