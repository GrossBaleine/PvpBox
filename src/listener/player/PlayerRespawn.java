package listener.player;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import kits.Vampire;
import launcher.PvpBox;
import manager.PBPlayer;
import menu.MainMenu;

public class PlayerRespawn implements Listener{

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		PBPlayer died = PvpBox.getInstance().get(event.getPlayer());
		PvpBox.getInstance().getPvpPlayers().remove(died);
		Vampire.playersTransformed.remove(died);
		event.setRespawnLocation(PvpBox.getInstance().getSpawnLocation());
		event.getPlayer().setGameMode(GameMode.ADVENTURE);
		new MainMenu(event.getPlayer());
	}
}
