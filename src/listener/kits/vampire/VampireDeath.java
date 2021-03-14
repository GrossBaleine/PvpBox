package listener.kits.vampire;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import kits.Vampire;
import launcher.PvpBox;
import manager.PBPlayer;

public class VampireDeath implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDeath(PlayerDeathEvent event) {
		PBPlayer died = PvpBox.getInstance().get(event.getEntity());
		Vampire.vampireDeathRessetDay(died);
		if (died.getPlayer().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent damagerEvent = (EntityDamageByEntityEvent) died.getPlayer().getLastDamageCause();
			Entity damager = damagerEvent.getDamager();
			if(damager instanceof Player){
				PBPlayer killer = PvpBox.getInstance().get((Player)damager);
				Vampire.vampireKill(killer);
			}
		}
	}
}
