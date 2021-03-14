package listener.kits.vampire;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import kits.Vampire;
import launcher.PvpBox;
import manager.PBPlayer;

public class VampireTransform implements Listener {

	@EventHandler
	public void transform(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		PBPlayer pbPlayer = PvpBox.getInstance().get(player);
		ItemStack item = event.getItem();
		if (pbPlayer.getKit() != null 
				&& pbPlayer.getKit().getName() == "Vampire" 
				&& item != null
				&& item.getType() == Material.FERMENTED_SPIDER_EYE) {
			if (!Vampire.playersTransformed.contains(pbPlayer)
					&& !player.hasCooldown(Material.FERMENTED_SPIDER_EYE))
				Vampire.transform(pbPlayer);
			else if(Vampire.playersTransformed.contains(pbPlayer)){
				Vampire.unTransform(pbPlayer);
				player.setCooldown(Material.FERMENTED_SPIDER_EYE, 20*10);
			}
		}
	}

}
