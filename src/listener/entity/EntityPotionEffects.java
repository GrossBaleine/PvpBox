package listener.entity;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import launcher.PvpBox;
import manager.Pet;
import manager.Team;

public class EntityPotionEffects implements Listener {

	static ArrayList<PotionEffectType> beneficEffects = new ArrayList<PotionEffectType>();
	static ArrayList<PotionEffectType> devaluableEffects = new ArrayList<PotionEffectType>();
	static boolean instanciate = false;

	private void typesOfEffects() {
		beneficEffects.add(PotionEffectType.ABSORPTION);
		beneficEffects.add(PotionEffectType.DAMAGE_RESISTANCE);
		beneficEffects.add(PotionEffectType.FIRE_RESISTANCE);
		beneficEffects.add(PotionEffectType.HEAL);
		beneficEffects.add(PotionEffectType.HEALTH_BOOST);
		beneficEffects.add(PotionEffectType.INCREASE_DAMAGE);
		beneficEffects.add(PotionEffectType.INVISIBILITY);
		beneficEffects.add(PotionEffectType.NIGHT_VISION);
		beneficEffects.add(PotionEffectType.REGENERATION);
		beneficEffects.add(PotionEffectType.SPEED);
		beneficEffects.add(PotionEffectType.WATER_BREATHING);

		devaluableEffects.add(PotionEffectType.BLINDNESS);
		devaluableEffects.add(PotionEffectType.CONFUSION);
		devaluableEffects.add(PotionEffectType.HARM);
		devaluableEffects.add(PotionEffectType.GLOWING);
		devaluableEffects.add(PotionEffectType.HUNGER);
		devaluableEffects.add(PotionEffectType.POISON);
		devaluableEffects.add(PotionEffectType.SLOW);
		devaluableEffects.add(PotionEffectType.WEAKNESS);
		devaluableEffects.add(PotionEffectType.WITHER);
		instanciate = true;
	}

	@EventHandler
	public void onReceiveEffect(PotionSplashEvent event) {
		if (!instanciate)
			typesOfEffects();
		Collection<PotionEffect> effects = event.getEntity().getEffects();
		Player shooter = (Player) event.getEntity().getShooter();
		Team shooterTeam = PvpBox.getInstance().get(shooter).getTeam();
		if (shooterTeam == null) {
			playerTeamNull(effects, event.getAffectedEntities(), shooter, event);
		} else {
			shooter.sendMessage("do some shit");
		}
		event.setCancelled(true);
	}

	private void playerTeamNull(Collection<PotionEffect> effects, Collection<LivingEntity> collection, Player thrower,
			PotionSplashEvent event) {
		for (PotionEffect effect : effects) {
			if (beneficEffects.contains(effect.getType())) {
				if (collection.contains(thrower))
					thrower.addPotionEffect(effect);
				for(Pet pet :PvpBox.getInstance().get(thrower).getPets() ){
					if(collection.contains(pet.getCreature()))
						pet.getCreature().addPotionEffect(effect);
				}
			} else if (devaluableEffects.contains(effect.getType())) {
				for (LivingEntity ent : collection) {
					if (ent != thrower) {
						if(ent instanceof Player)
							PvpBox.getInstance().get((Player)ent).getFightStats().setLastDamager(event.getPotion());
						ent.addPotionEffect(effect);
					}
				}
			} else {
				for (LivingEntity ent : collection){
					if(ent.isInsideVehicle() && ent.getVehicle() instanceof LivingEntity)
						((LivingEntity) ent.getVehicle()).addPotionEffect(effect);	
					ent.addPotionEffect(effect);}
			}
		}
	}

}
