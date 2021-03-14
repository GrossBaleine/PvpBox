package listener.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.projectiles.ProjectileSource;

import launcher.PvpBox;

public class PlayerReceiveDamage implements Listener {

	private PvpBox pvpBox = PvpBox.getInstance();

	@EventHandler
	public void playerReceiveDamageByEntityRoot(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		Entity target = event.getEntity();
		if (target instanceof Player) {
			targetNotInPvp((Player) target, event);
			offPlayerDamageNotInPvpAndTeam((Player) target, damager, event);
			if (damager instanceof Projectile && target instanceof LivingEntity)
				offPlayerDamageByHisOwnProjectile((LivingEntity) target, (Projectile) damager, event);
		}
	}

	@EventHandler
	public void PlayerReceiveDamageRoot(EntityDamageEvent event) {
		Entity player = event.getEntity();
		if (player instanceof Player) {
			if (!pvpBox.getPvpPlayers().contains(pvpBox.get((Player) player)))
				event.setCancelled(true);
		}
		offFallDamages(event);
		offFireballExplosionDamage(event);
		offProtectionAreaDamages(event);
	}

	private void offProtectionAreaDamages(EntityDamageEvent event) {
		if (event.getEntity() instanceof LivingEntity
				&& PvpBox.getInstance().isEntityProtected((LivingEntity) event.getEntity()))
			event.setCancelled(true);
	}

	public void targetNotInPvp(Player target, EntityDamageEvent event) {
		if (!pvpBox.getPvpPlayers().contains(pvpBox.get((Player) target)))
			event.setCancelled(true);
	}

	public void offPlayerDamageByHisOwnProjectile(LivingEntity target, Projectile damager,
			EntityDamageByEntityEvent event) {
		if (damager.getShooter() == target) {
			event.setCancelled(true);
		}
	}

	public void offPlayerDamageNotInPvpAndTeam(Player target, Entity damager, EntityDamageByEntityEvent event) {
		if (damager instanceof Player) {
			if (pvpBox.get((Player) damager).getTeam() == pvpBox.get((Player) target).getTeam()
					&& pvpBox.get((Player) damager).getTeam() != null)
				event.setCancelled(true);
		} else if (damager instanceof Projectile) {
			ProjectileSource shooter = ((Projectile) damager).getShooter();
			if (shooter instanceof Player) {
				if (pvpBox.get((Player) shooter).getTeam() == pvpBox.get((Player) target).getTeam()
						&& pvpBox.get((Player) shooter).getTeam() != null)
					event.setCancelled(true);
			}
		}
	}

	public void offFallDamages(EntityDamageEvent event) {
		if (event.getCause() == DamageCause.FALL)
			event.setCancelled(true);
	}

	public void offFireballExplosionDamage(EntityDamageEvent event) {
		if (event.getCause() == DamageCause.BLOCK_EXPLOSION || event.getCause() == DamageCause.ENTITY_EXPLOSION) {
			if (!(event.getEntity() instanceof LivingEntity))
				event.setCancelled(true);
		}
	}

}
