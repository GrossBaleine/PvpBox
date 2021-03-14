package listener.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import launcher.PvpBox;

public class EntityChangeBlock implements Listener {

	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent event){
		PvpBox.getInstance().broadcast("Appel de EntityChangeBlockEvent");
		event.setCancelled(true);
		
	}
}
