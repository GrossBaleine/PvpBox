package listener.player;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SplashPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import kits.Mage;
import kits.Vampire;
import launcher.PvpBox;
import manager.FightStats;
import manager.PBPlayer;
import manager.Pet;

public class PlayerDeath implements Listener {

	private PvpBox pvpBox = PvpBox.getInstance();
	private ArrayList<String> deathByCac = new ArrayList<String>();
	private ArrayList<String> deathByExplosion = new ArrayList<String>();
	private ArrayList<String> deathByProjectile = new ArrayList<String>();
	private ArrayList<String> deathByFire = new ArrayList<String>();
	private ArrayList<String> deathByMagic = new ArrayList<String>();
	private ArrayList<String> deathBySummon = new ArrayList<String>();

	public PlayerDeath() {
		initializeMessages();
	}

	private void initializeMessages() {
		initializeCacMessages();
		initializeExplosionMessages();
		initializeProjectileMessages();
		initializeFireMessages();
		initializeMagicMessages();
		initializeSummonMessages();
	}

	private void initializeSummonMessages() {
		deathBySummon.add("<<Killed>> a été dévoré vivant par <<Summon>> de <<Killer>>.");
		deathBySummon.add("<<Killed>> a contemplé de trop près le <<Summon>> invoqué par <<Killer>>");
	}

	private void initializeMagicMessages() {
		deathByMagic.add("<<Killed>> a subit une injection léthale par <<KillingObject>> de <<Killer>>.");
		deathByMagic.add("<<Killed>> a fait un coma éthylique en buvant <<KillingObject>> de <<Killer>>.");
	}

	private void initializeFireMessages() {
		deathByFire.add("<<Killed>> a été immolé par <<Killer>>.");
		deathByFire.add("Nous commémorons aujourd'hui la mémoire de <<Killed>>, mort brûlé par <<Killer>.");
	}

	private void initializeProjectileMessages() {
		deathByProjectile.add("<<Killed>> a été empalé par <<KillingObject>> de <<Killer>>.");
	}

	private void initializeExplosionMessages() {
		deathByExplosion.add("<<Killed>> a explosé sous la déflagration de la <<KillingObject>> de <<Killer>>.");
		deathByExplosion.add("<<Killed>> a été désintégré par <<KillingObject>> de <<Killer>>.");
		deathByExplosion.add("<<Killed>> a été annihilé par <<KillingObject>> de <<Killer>>.");
	}

	private void initializeCacMessages() {
		deathByCac.add("<<Killed>> a été tué par <<KillingObject>> de <<Killer>>.");
		deathByCac.add("<<Killed>> a été décapité par <<KillingObject>> de <<Killer>>.");
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		PvpBox.getInstance().get(player).clearPets();
		event.setDroppedExp(0);
		event.setKeepLevel(true);
		event.getDrops().clear();
		player.setExp(0f);
		FightStats.resetStatsOnDeath(PvpBox.getInstance().get(player));
		if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent damagerEvent = (EntityDamageByEntityEvent) player.getLastDamageCause();
			EntityDamageEvent.DamageCause cause = damagerEvent.getCause();
			Entity damager = damagerEvent.getDamager();
			switch (cause) {
			case ENTITY_ATTACK:
			case ENTITY_SWEEP_ATTACK: {

				if (damager instanceof Player) {
					event.setDeathMessage(selectRandomMessage(deathByCac, player, (Player) damager,
							((Player) damager).getInventory().getItemInMainHand(), null));
					modifyStats(player, (Player) damager);
				} else if (damager instanceof Creature) {
					for (PBPlayer pbPlayers : pvpBox.getPvpPlayers()) {
						if (pbPlayers.getPets() == null)
							continue;
						for (Pet pet : pbPlayers.getPets()) {
							if (pet.getCreature().getUniqueId().equals(damager.getUniqueId())) {
								event.setDeathMessage(selectRandomMessage(deathBySummon, player,
										pet.getOwner().getPlayer(), null, (LivingEntity) pet.getCreature()));
								modifyStats(player, (Player) pet.getOwner().getPlayer());
								break;
							}
						}
					}
				}
				break;
			}
			case ENTITY_EXPLOSION:
				if (damager instanceof Fireball) {
					ProjectileSource shooter = ((Projectile) damager).getShooter();
					if (shooter instanceof Player) {
						event.setDeathMessage(selectRandomMessage(deathByExplosion, player, (Player) shooter,
								Mage.FireballAndLaunchers.get(damager), null));
						modifyStats(player, (Player) shooter);
					}
				} else if (damager instanceof Creature) {
					for (PBPlayer pbPlayers : pvpBox.getPvpPlayers()) {
						for (Pet pet : pbPlayers.getPets()) {
							if (pet.getCreature() == damager) {
								event.setDeathMessage(selectRandomMessage(deathBySummon, player,
										pet.getOwner().getPlayer(), null, (LivingEntity) pet.getCreature()));
								modifyStats(player, (Player) pet.getOwner());
								break;
							}
						}
					}
				}
				break;
			case PROJECTILE:
				if (damager instanceof Arrow || damager instanceof Fireball) {
					Entity shooter = (Entity) ((Projectile) damager).getShooter();
					if (shooter instanceof Player) {
						event.setDeathMessage(selectRandomMessage(deathByProjectile, player, (Player) shooter,
								((Player) shooter).getInventory().getItemInMainHand(), null));
						modifyStats(player, (Player) shooter);
					} else if (shooter instanceof Creature) {
						for (PBPlayer pbPlayers : pvpBox.getPvpPlayers()) {
							for (Pet pet : pbPlayers.getPets()) {
								if (pet.getCreature() == shooter) {
									event.setDeathMessage(selectRandomMessage(deathBySummon, player,
											pet.getOwner().getPlayer(), null, (LivingEntity) pet.getCreature()));
									modifyStats(player, (Player) pet.getOwner());
									break;
								}
							}
						}
					}
				}
				break;
			case FIRE:
			case FIRE_TICK: {
				break;
			}
			default:
				PvpBox.getInstance().broadcast("Mort non déterminée de " + player.getName() + " par cause : " + cause);
				break;
			}
		} else if (player.getLastDamageCause() instanceof EntityDamageEvent) {
			EntityDamageEvent damagerEvent = player.getLastDamageCause();
			EntityDamageEvent.DamageCause cause = damagerEvent.getCause();
			Entity damager = PvpBox.getInstance().get(player).getFightStats().getLastDamager();
			switch (cause) {
			case MAGIC:
			case POISON: {
				if (damager instanceof SplashPotion) {
					ProjectileSource shooter = ((Projectile) damager).getShooter();
					event.setDeathMessage(selectRandomMessage(deathByMagic, player, (Player) shooter,
							((SplashPotion) damager).getItem(), null));
					modifyStats(player, (Player) shooter);
				}
				break;
			}
			case CUSTOM:
				event.setDeathMessage("§6" + player.getName() + "§e s'est aventuré en terre inconnue et a été brûlé vif par un dragon.");
				break;
			case DROWNING:
				event.setDeathMessage("§6" + player.getName() + "§e ne savait pas nager, et il s'est noyé.");
				break;
			default:
				PvpBox.getInstance().broadcast("§6" + player.getName() + "§e est tombé dans le néant");
				break;
			}
		}
	}

	public void modifyStats(Player whoDied, Player whoKilled) {
		PBPlayer died = pvpBox.get(whoDied);
		PBPlayer killer = pvpBox.get(whoKilled);
		died.getKitStats(died.getKit()).addDeath();
		killer.getKitStats(killer.getKit()).addKill();
		killer.addPoints(1);
		updateFightStats(died, killer);
		Vampire.vampireKill(killer);
	}

	public void updateFightStats(PBPlayer died, PBPlayer killer) {
		FightStats FSdied = died.getFightStats();
		FightStats FSkiller = killer.getFightStats();
		FSdied.setKillStreak(0);
		FSkiller.addKillStreak();
		if (FSdied.getPlayersKilled().containsKey(killer)) {
			Integer value = FSdied.getPlayersKilled().get(killer);
			FSdied.getPlayersKilled().put(killer, value + 1);
		} else
			FSdied.getPlayersKilled().put(killer, 1);

		if (FSkiller.getPlayersKilled().containsKey(died)) {
			Integer value = FSkiller.getPlayersKilled().get(died);
			FSkiller.getPlayersKilled().put(died, value + 1);
		} else
			FSkiller.getPlayersKilled().put(died, 1);
	}

	@SuppressWarnings("unchecked")
	private String selectRandomMessage(ArrayList<String> list, Player killed, Player killer, ItemStack killingObject,
			LivingEntity summon) {
		if ((killingObject == null || killingObject.getType() == Material.AIR) && summon == null) {
			String defaultMessage = "§6" + killed.getName() + "§e a été tué par §6" + killer.getName() + "§e.";
			return defaultMessage;
		}
		Random rn = new Random();
		int index = rn.nextInt(list.size());
		String selectedMessage = ((ArrayList<String>) list.clone()).get(index);
		selectedMessage = selectedMessage.replaceAll("<<Killed>>", "§6" + killed.getName() + "§e");
		selectedMessage = selectedMessage.replaceAll("<<Killer>>", "§6" + killer.getName() + "§e");
		if (killingObject != null && killingObject.getType() != Material.AIR) {
			selectedMessage = selectedMessage.replaceAll("<<KillingObject>>",
					"§3[" + killingObject.getItemMeta().getDisplayName() + "]§e");
		}
		if (summon != null) {
			selectedMessage = selectedMessage.replaceAll("<<Summon>>", "§3[" + summon.getName() + "]§e");
		}
		return pvpBox.getPrefix() + selectedMessage;
	}
}
