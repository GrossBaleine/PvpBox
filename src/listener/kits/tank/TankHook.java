package listener.kits.tank;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

import kits.Tank;
import launcher.PvpBox;

public class TankHook implements Listener {

	@EventHandler
	public void onHook(PlayerFishEvent event) {
		Player player = event.getPlayer();
		PlayerFishEvent.State state = event.getState();
		if ((state == PlayerFishEvent.State.FISHING && player.hasCooldown(Material.FISHING_ROD))
				|| state == PlayerFishEvent.State.CAUGHT_FISH) {
			player.sendMessage("Vous ne pouvez pas utiliser ceci pour le moment.");
			event.setCancelled(true);
		}
		if (state == PlayerFishEvent.State.CAUGHT_ENTITY && event.getCaught() instanceof Entity) {
			if (PvpBox.getInstance().get(player).getKit().getName() == "Tank") {
				Entity caught = event.getCaught();
				if (player.getInventory().getItemInMainHand().getType() == Material.FISHING_ROD) {
					if (!player.hasCooldown(Material.FISHING_ROD)) {
						player.setCooldown(Material.FISHING_ROD, 20 * 15);
						Tank.hook(player, caught);
					}
				} else if (player.getInventory().getItemInOffHand().getType() == Material.FISHING_ROD) {
					if (!player.hasCooldown(Material.FISHING_ROD)) {
						player.setCooldown(Material.FISHING_ROD, 20 * 15);
						Tank.hook(player, caught);
					}
				} else
					event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void hookSpeed(ProjectileLaunchEvent event) {
		if (event.getEntity() instanceof FishHook) {
			Projectile ent = event.getEntity();
			Vector vel = ent.getVelocity();
			ent.setVelocity(vel.multiply(2));
		}
	}
}
