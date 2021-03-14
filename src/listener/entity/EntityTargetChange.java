package listener.entity;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import launcher.PvpBox;
import manager.PBPlayer;
import manager.Pet;

public class EntityTargetChange implements Listener {

	@EventHandler
	public void targetChangeEvent(EntityTargetEvent event) {
		Entity target = event.getTarget();
		Entity creature = event.getEntity();
		if (target instanceof Player) {
			PBPlayer player = PvpBox.getInstance().get((Player) target);
			for (Pet pet : player.getPets()) {
				if (pet.getCreature() == creature && pet.getOwner() == player)
					event.setCancelled(true);
			}
		} else if (target instanceof LivingEntity) {
			for (PBPlayer players : PvpBox.getInstance().getPvpPlayers()) {
				for (Pet pets : players.getPets()) {
					if (pets.getCreature() == creature) {
						isTargetInSameTeam(target, players.getPets(), event);
						return;
					}
				}
			}
		}
	}

	private void isTargetInSameTeam(Entity target, List<Pet> pets, EntityTargetEvent event) {
		for (Pet pet : pets) {
			if (pet.getCreature() == target) {
				event.setCancelled(true);
				return;
			}
		}
	}
}
