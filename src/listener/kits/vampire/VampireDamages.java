package listener.kits.vampire;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import kits.Vampire;
import launcher.PvpBox;
import manager.PBPlayer;

public class VampireDamages implements Listener {

	@EventHandler
	public void vampireHited(EntityDamageEvent event) {
		Entity ent = event.getEntity();
		if (ent instanceof Player) {
			PBPlayer pbPlayer = PvpBox.getInstance().get((Player) ent);
			if (pbPlayer.getKit() != null && pbPlayer.getKit().getName() == "Vampire"
					&& Vampire.playersTransformed.contains(pbPlayer)) {
				event.setDamage(100);
			}
		}
	}

	@EventHandler
	public void vampireHitOthers(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			PBPlayer pbPlayer = PvpBox.getInstance().get((Player) event.getDamager());
			if (pbPlayer.getKit() != null && pbPlayer.getKit().getName() == "Vampire") {
				if (Vampire.playersTransformed.contains(pbPlayer))
					event.setCancelled(true);
				else {
					pbPlayer.getFightStats().setHitsGiven(pbPlayer.getFightStats().getHitsGiven() + 1);
					Vampire.attacksHeal(pbPlayer);
				}
			}
		}
	}
}
