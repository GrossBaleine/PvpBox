package listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItem implements Listener {

	@EventHandler
	public void onInteract(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}
}
