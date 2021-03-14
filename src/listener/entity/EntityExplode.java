package listener.entity;

import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import kits.Mage;

public class EntityExplode implements Listener {
	
	private float radius = 3;

	@EventHandler
	public void onBlockExplode(BlockExplodeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		if (event.getEntity() instanceof Fireball) {
			Location locExp = event.getLocation();
			Mage.explosion(locExp.add(new Vector(0, -1, 0)), radius,event.getEntity());
			event.setYield(radius);
			event.blockList().clear();
			Mage.FireballAndLaunchers.remove(event.getEntity());
		}
	}
}
