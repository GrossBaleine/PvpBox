package listener.player;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.projectiles.ProjectileSource;

import kits.Mage;
import launcher.PvpBox;

@SuppressWarnings("deprecation")
public class PlayerDealDamage implements Listener {

	private PvpBox pvpBox = PvpBox.getInstance();

	@EventHandler
	public void EntityDamageByPlayerRoot(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		Entity target = event.getEntity();
		if (damager instanceof Player)
			damagerIsPlayer((Player) damager, target, event);
		if (damager instanceof Projectile)
			damagerIsProjectile((Projectile) damager, target, event);
	}

	@EventHandler
	public void setFireballExplosionDamage(EntityDamageEvent event) {
		if (event.getCause() == DamageCause.BLOCK_EXPLOSION || event.getCause() == DamageCause.ENTITY_EXPLOSION) {
			if (event.getEntity() instanceof LivingEntity) {
				event.setDamage(Mage.fireballDamages);
				event.setDamage(DamageModifier.ARMOR, 0);
			}
		}
	}
	
	public void damagerIsPlayer(Player damager, Entity target, EntityDamageByEntityEvent event) {
		entitiesToRemoveOnHit(target, event);
		cancelIfNotInPvp((Player) damager, event);
		if (target instanceof LivingEntity) {
			// pets
		}
	}

	public void damagerIsProjectile(Projectile damager, Entity target, EntityDamageByEntityEvent event) {
		ProjectileSource source = ((Projectile) damager).getShooter();
		if (!(source instanceof Player))
			return;
		if (target instanceof LivingEntity) {
			if (damager instanceof Fireball)
				fireballDirectDamage((LivingEntity) target, (Fireball) damager, event);
		}
	}

	public void cancelIfNotInPvp(Player damager, EntityDamageByEntityEvent event) {
		if (!pvpBox.getPvpPlayers().contains(pvpBox.get((Player) damager)))
			event.setCancelled(true);
	}

	public void entitiesToRemoveOnHit(Entity target, EntityDamageByEntityEvent event) {
		if (target instanceof Fireball || target instanceof ArmorStand) {
			target.remove();
		}
	}

	public void fireballDirectDamage(LivingEntity target, Fireball damager, EntityDamageByEntityEvent event) {
		event.setDamage(Mage.fireballDamages);
		event.setDamage(DamageModifier.ARMOR, 0);
	}
	
}
