package manager;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import launcher.PvpBox;

public class FightStats {

	private int killStreak = 0;
	private int livingPvpTime = 0;
	private int hitsGiven = 0;
	private HashMap<PBPlayer,Integer> playersKilled = new HashMap<PBPlayer,Integer>();
	private HashMap<PBPlayer,Integer> killers = new HashMap<PBPlayer,Integer>();
	private Entity lastDamager = null;
	
	
	public FightStats() {
	}

	public void addKillStreak() {
		killStreak++;
	}

	public int getKillStreak() {
		return killStreak;
	}
	
	public void setKillStreak(int ks){
		killStreak = ks;
	}

	public void setLivingPvpTime(int i) {
		livingPvpTime = i;
	}

	public int getLivingPvpTime() {
		return livingPvpTime;
	}

	public int getHitsGiven() {
		return hitsGiven;
	}

	public void setHitsGiven(int hits_given) {
		this.hitsGiven = hits_given;
	}
	
	public HashMap<PBPlayer,Integer> getKillers(){
		return killers;
	}
	
	public HashMap<PBPlayer,Integer> getPlayersKilled(){
		return playersKilled;
	}

	public Entity getLastDamager() {
		return lastDamager;
	}

	public void setLastDamager(Entity lastDamager) {
		this.lastDamager = lastDamager;
	}

	public static void resetStatsOnDeath(PBPlayer player){
		FightStats fs = player.getFightStats();
		fs.killStreak = 0;
		fs.livingPvpTime = 0;
		fs.hitsGiven = 0;
		fs.lastDamager = null;
	}
	
	public static void fightStatsTask() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) PvpBox.getInstance(), new Runnable() {
			public void run() {
				for (PBPlayer p : PvpBox.getInstance().getPvpPlayers()) {
					p.getFightStats().setLivingPvpTime(p.getFightStats().getLivingPvpTime() + 1);		
				}
			}
		}, 0, 1);
	}

}
