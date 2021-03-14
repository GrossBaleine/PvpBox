package listener.kits.mage;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import kits.Kit;
import kits.Mage;
import launcher.PvpBox;

public class MageFireball implements Listener {

	@EventHandler
	public void onFireballLaunch(PlayerInteractEvent event) {
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		Kit kit = PvpBox.getInstance().get(player).getKit();
		if (kit == null || item == null)
			return;
		if (kit.getName() == "Mage" && item.getType() == Material.FIREBALL) {
			if (!player.hasCooldown(Material.FIREBALL)) {
				Mage.launchFireBall(player, item);
				player.setCooldown(Material.FIREBALL, 20*2);
			}
			event.setCancelled(true);
		}
	}

}
