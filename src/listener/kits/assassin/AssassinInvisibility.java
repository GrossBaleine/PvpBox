package listener.kits.assassin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

import kits.Assassin;
import kits.Kit;
import launcher.PvpBox;
import manager.PBPlayer;

public class AssassinInvisibility implements Listener {

	private PvpBox pvpBox = PvpBox.getInstance();

	@EventHandler
	public void enableInvisibility(PlayerInteractEvent event) {
		PBPlayer pbPlayer = PvpBox.getInstance().get(event.getPlayer());
		Player player = pbPlayer.getPlayer();
		Kit kit = pbPlayer.getKit();
		if (kit == null || kit.getName() != "Assassin")
			return;
		if (kit.getName() == "Assassin" && event.getItem() != null
				&& event.getItem().getType() == Material.EYE_OF_ENDER) {
			if (!player.hasCooldown(Material.EYE_OF_ENDER)) {
				// if (new CooldownBuilder(player, event.getItem(), 15 *
				// 20).setCooldown())
				Assassin.setInvisibility(pbPlayer);
				player.setCooldown(Material.EYE_OF_ENDER, 20 * 15);
			}
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void removeInvisibilityOnDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			PBPlayer pbPlayer = pvpBox.get((Player) event.getEntity());
			if (pbPlayer.getKit() != null) {
				if (pbPlayer.getKit().getName() == "Assassin" && Assassin.invisiblePlayers.containsKey(pbPlayer)) {
					if (event.getCause() == DamageCause.FALL)
						return;
					Assassin.removeInvisibility(pbPlayer);
				}
			}
		}
	}

	@EventHandler
	public void removeInvisibilityOnHit(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			PBPlayer pbPlayer = pvpBox.get((Player) event.getDamager());
			if (pbPlayer.getKit() != null) {
				if (pbPlayer.getKit().getName() == "Assassin" && Assassin.invisiblePlayers.containsKey(pbPlayer))
					Assassin.removeInvisibility(pbPlayer);
			}
		}
	}
}
