package listener.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import launcher.PvpBox;
import manager.RestrictedArea;

public class PlayerMove implements Listener {

	PvpBox pvpBox = PvpBox.getInstance();

	@EventHandler
	public void isInRestrictedArea(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		for (RestrictedArea area : PvpBox.restrictedAreas) {
			if (area.isInsideArea(event.getPlayer())
					&& pvpBox.getPvpPlayers().contains(pvpBox.get(player))) {
				//ElenoxAPI.getElenoxAPI().getActionBar().sendActionBar(event.getPlayer(),
				//		"§4Vous entrez dans une terre inconnue et dangereuse, peuplée de dragons. Il serait sage de faire demi-tour.");
				if (area.isKillable(player)){
					//PacketPlayOutNamedSoundEffect packet = new PacketPlayOutNamedSoundEffect(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
					player.damage(666);
				}
			}
		}
	}
}
