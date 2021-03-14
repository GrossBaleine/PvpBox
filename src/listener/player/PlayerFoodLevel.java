package listener.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import launcher.PvpBox;

public class PlayerFoodLevel implements Listener{
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		PvpBox pvpBox = PvpBox.getInstance();
		if (!pvpBox.getPvpPlayers().contains(pvpBox.get((Player) event.getEntity())))
			event.setCancelled(true);
	}
}
