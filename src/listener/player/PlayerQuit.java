package listener.player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import kits.Assassin;
import kits.Vampire;
import launcher.PvpBox;
import manager.PBPlayer;

public class PlayerQuit implements Listener {

	static PvpBox pvpBox = PvpBox.getInstance();
	private static ArrayList<String> leaveMessage = new ArrayList<String>();

	public PlayerQuit() {
		initializeMessages();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent event) throws SQLException {
		Player player = event.getPlayer();
		event.setQuitMessage(getRandomMessage(player));
		PBPlayer p = pvpBox.get(player);
		p.clearPets();
		if (player.isInsideVehicle())
			player.getVehicle().remove();
		Assassin.invisiblePlayers.remove(pvpBox.get(player));
		Vampire.unTransform(p);
		Vampire.vampireDeathRessetDay(p);
		// p.getTeam().playerQuit(p);
		//PlayerDatas.exportPlayerDatas((PvpBox.getInstance().get(player)));
		pvpBox.getPBPlayers().remove(p);
		pvpBox.getPvpPlayers().remove(p);
		
	}

	private void initializeMessages() {
		leaveMessage.add("§e<<Player>> est gravement blessé, une civière est venue l'emmener.");
		leaveMessage.add("§eUn OVNI a kidnappé <<Player>>!");
		leaveMessage.add("§e<<Player>> s'est enfuit, vous étiez trop fort pour lui.");
		leaveMessage.add("§e<<Player>> vous tire sa révérence.");
		leaveMessage.add("§e<<Player>> s'est déconnecté. A-t-il ragequit?");
	}

	@SuppressWarnings("unchecked")
	public static String getRandomMessage(Player player) {	
		Random rn = new Random();
		int index = rn.nextInt(leaveMessage.size());
		String message = pvpBox.getPrefix()+"§8»§7» "+((ArrayList<String>) leaveMessage.clone()).get(index).replaceAll("<<Player>>", "§6"+player.getName()+"§e");
		return message;
	}
	
}