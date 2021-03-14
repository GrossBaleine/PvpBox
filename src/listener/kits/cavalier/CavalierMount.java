package listener.kits.cavalier;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;

import kits.Cavalier;
import launcher.PvpBox;
import manager.PBPlayer;

public class CavalierMount implements Listener {

	@EventHandler
	public void onMount(PlayerInteractEvent event) {
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		PBPlayer pbPlayer = PvpBox.getInstance().get(player);
		if (pbPlayer.getKit() == null)
			return;
		if (pbPlayer.getKit().getName() == "Cavalier" && item != null && item.getType() == Material.SADDLE) {
			if (!player.hasCooldown(Material.SADDLE) && !player.isInsideVehicle()) 
				pbPlayer.setKitCustomAdd(pbPlayer.getKit());
		}
	}

	@EventHandler
	public void playerDemount(VehicleExitEvent event) {
		event.getVehicle().remove();
		((Player)event.getExited()).setCooldown(Material.SADDLE, Cavalier.horseCD);
	}
}
