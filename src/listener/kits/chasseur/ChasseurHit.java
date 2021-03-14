package listener.kits.chasseur;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import launcher.PvpBox;
import manager.PBPlayer;
import manager.Pet;

public class ChasseurHit implements Listener {

	@EventHandler
	public void chasseurHitEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Projectile) {
			Entity ent = (Entity) ((Projectile) event.getDamager()).getShooter();
			if (ent instanceof Player) {
				PBPlayer pbPlayerDamager = PvpBox.getInstance().get((Player) ent);
				chasseurPetChangeTarget(pbPlayerDamager, event.getEntity());
				if(event.getEntity() instanceof Player){
					PBPlayer pbPlayerDamaged = PvpBox.getInstance().get((Player) event.getEntity());
					chasseurPetChangeTarget(pbPlayerDamaged,ent);
				}
			}
		}
		
		else if (event.getDamager() instanceof LivingEntity) {
			if (event.getDamager() instanceof Player) {
				PBPlayer pbPlayerDamager = PvpBox.getInstance().get((Player) event.getDamager());
				if (event.getEntity() instanceof Player) {
					PBPlayer pbPlayerDamaged = PvpBox.getInstance().get((Player) event.getEntity());
					chasseurPetChangeTarget(pbPlayerDamaged, event.getDamager());
				}
				chasseurPetChangeTarget(pbPlayerDamager, event.getEntity());
			} else if (event.getEntity() instanceof Player) {
				PBPlayer pbPlayerDamaged = PvpBox.getInstance().get((Player) event.getEntity());
				chasseurPetChangeTarget(pbPlayerDamaged, event.getDamager());
			}
		}

	}

	private void chasseurPetChangeTarget(PBPlayer player, Entity target) {
		if (player.getKit()!= null && player.getKit().getName() == "Chasseur" && target instanceof LivingEntity) {
			for (Pet pet : player.getPets())
				pet.getCreature().setTarget((LivingEntity) target);
		}
	}

}
