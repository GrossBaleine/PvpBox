package listener.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHit implements Listener {
	
	@EventHandler
	public void removeArrowOnBlocks(ProjectileHitEvent event){
		if(event.getHitBlock() != null){
			event.getEntity().remove();
		}
	}
}
