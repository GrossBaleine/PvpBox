package listener.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.LeavesDecayEvent;

public class BlockBreak implements Listener {
	
	@EventHandler
	public void cancelBlockBreak(BlockBreakEvent event) {
		event.setCancelled(true);
		event.setExpToDrop(0);
	}

	@EventHandler
	public void cancelLeavesDecay(LeavesDecayEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onPhysics(BlockPhysicsEvent e) {
		e.setCancelled(true);
	}

}
