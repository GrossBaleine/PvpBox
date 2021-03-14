package manager;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Sets;

import launcher.PvpBox;
import net.minecraft.server.v1_12_R1.EntityCreature;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.IRangedEntity;
import net.minecraft.server.v1_12_R1.PathEntity;
import net.minecraft.server.v1_12_R1.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_12_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_12_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_12_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_12_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_12_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_12_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_12_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_12_R1.PathfinderGoalSelector;

public class Pet {

	private PBPlayer owner;
	private Creature creature;

	public Pet(PBPlayer owner, EntityType entityType, String name, Location spawnPoint, boolean meleeAttack) {
		setOwner(owner);
		this.creature = (Creature) owner.getPlayer().getWorld().spawnEntity(spawnPoint, entityType);
		creature.setCustomName(name);
		creature.setCustomNameVisible(true);
		setGoals(creature, meleeAttack);
	}

	public PBPlayer getOwner() {
		return owner;
	}

	public void setOwner(PBPlayer owner) {
		this.owner = owner;
	}

	public Creature getCreature() {
		return creature;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setGoals(Creature e, boolean meleeAttack) {
		EntityCreature c = (EntityCreature) ((EntityInsentient) ((CraftEntity) e).getHandle());
		try {
			Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
			bField.setAccessible(true);
			Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
			cField.setAccessible(true);
			bField.set(c.goalSelector, Sets.newLinkedHashSet());
			bField.set(c.targetSelector, Sets.newLinkedHashSet());
			cField.set(c.goalSelector, Sets.newLinkedHashSet());
			cField.set(c.targetSelector, Sets.newLinkedHashSet());
			c.goalSelector.a(0, new PathfinderGoalFloat(c));
			c.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(c, 1));
			c.goalSelector.a(7, new PathfinderGoalRandomStroll(c, 1));
			c.goalSelector.a(8, new PathfinderGoalLookAtPlayer(c, EntityLiving.class, 8));
			c.goalSelector.a(8, new PathfinderGoalRandomLookaround(c));
			if (meleeAttack) {
				c.goalSelector.a(0, new PathfinderGoalMeleeAttack(c, 1.2, true));
			} else {
				c.goalSelector.a(2, new PathfinderGoalArrowAttack((IRangedEntity) c, 1.0, 12, 20));
			}
			c.targetSelector.a(0,
					new PathfinderGoalNearestAttackableTarget(c, EntityLiving.class, 10, true, true, null));
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public static void moveTask() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) PvpBox.getInstance(), new Runnable() {
			@Override
			public void run() {
				for (PBPlayer players : PvpBox.getInstance().getPvpPlayers()) {
					for (Pet pet : players.getPets()) {
						Creature creature = pet.getCreature();
						setTargetIfClose(pet);
						if (creature.getTarget() == null
								&& pet.getOwner().getPlayer().getLocation().distance(creature.getLocation()) > 4) {
							EntityCreature c = (EntityCreature) ((CraftEntity) creature).getHandle();
							Location ownerLoc = pet.getOwner().getPlayer().getLocation();
							PathEntity pathEntity = c.getNavigation().a(ownerLoc.getX(), ownerLoc.getY(),
									ownerLoc.getZ());
							c.getNavigation().a(pathEntity, pet.getOwner().getPlayer().getWalkSpeed() + 1);
						}
					}
				}
			}
		}, 0, 1);

	}

	public static void setTargetIfClose(Pet pet) {
		Creature creature = pet.getCreature();
		Entity closestEnt = null;
		for (Entity ent : creature.getNearbyEntities(5, 5, 5)) {
			if (closestEnt == null)
				closestEnt = ent;
			else if (closestEnt != null
					&& closestEnt instanceof LivingEntity && creature.getLocation()
							.distance(ent.getLocation()) < creature.getLocation().distance(closestEnt.getLocation())
					&& ent instanceof LivingEntity) {
				isPetOnTeam(pet, (LivingEntity) closestEnt);
			}
		}
	}

	public static void isPetOnTeam(Pet pet, LivingEntity target) {
		Creature creature = pet.getCreature();
		if (pet.getOwner().getPlayer() != target) {
			for (Pet pets : pet.getOwner().getPets()) {
				if (pets.getCreature() == target)
					return;
			}
			creature.setTarget(target);
		}
	}
}
