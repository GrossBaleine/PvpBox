package listener.entity;

import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

public class EntityDamage implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof ItemFrame)
			event.setCancelled(true);
	}

	@EventHandler
	public void onFrameBrake(HangingBreakEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onVehicleDestroy(VehicleDestroyEvent event) {
		if(event.getVehicle() instanceof Minecart){
			event.setCancelled(true);
		}
	}
}
