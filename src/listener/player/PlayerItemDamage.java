package listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class PlayerItemDamage implements Listener{

	@EventHandler
	public void cancelDamageOnItem(PlayerItemDamageEvent event){
			event.setCancelled(true);
	}
}
