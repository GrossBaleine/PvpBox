package listener.kits.cavalier;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import launcher.PvpBox;
import manager.PBPlayer;

public class CavalierDamage implements Listener {

	@EventHandler
	public void onCavalierRecieveDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			PBPlayer pbPlayer = PvpBox.getInstance().get(player);
			if (pbPlayer.getKit() != null && pbPlayer.getKit().getName() == "Cavalier" && player.isInsideVehicle()) {
				Horse vehicle = (Horse) player.getVehicle();
				if (event.getCause() == DamageCause.FALL)
					return;
				if (event.getDamage() >= 5 && (event.getCause() == DamageCause.BLOCK_EXPLOSION
						|| event.getCause() == DamageCause.ENTITY_EXPLOSION))
					event.setDamage(event.getDamage() / 2);

				vehicle.damage(event.getDamage());
				vehicle.setLastDamageCause(event);
				event.setCancelled(true);
			}
		} else if (event.getEntity() instanceof Horse) {
			if (event.getDamage() >= 5 && (event.getCause() == DamageCause.BLOCK_EXPLOSION
					|| event.getCause() == DamageCause.ENTITY_EXPLOSION))
				event.setDamage(event.getDamage() / 2);
		}
	}

}
