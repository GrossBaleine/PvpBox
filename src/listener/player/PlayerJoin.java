package listener.player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

import launcher.PvpBox;
import manager.PBPlayer;
import menu.MainMenu;

public class PlayerJoin implements Listener {

	private static ArrayList<String> joinMessage = new ArrayList<String>();

	public PlayerJoin() {
		initializeMessages();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent event) throws SQLException {
		Player player = event.getPlayer();
		event.setJoinMessage((getRandomMessage(player)));
		PvpBox.getInstance().getPBPlayers().add(new PBPlayer(player));
		player.teleport(PvpBox.getInstance().getSpawnLocation());
		player.setGameMode(GameMode.ADVENTURE);
		player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
		player.setExp(0f);
		player.setLevel(PvpBox.getInstance().get(player).getPoints());
		for (PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());
		//PlayerDatas.loadPlayerDatas(PvpBox.getInstance().get(player));
		
		new MainMenu(player);

		for (Player pls : Bukkit.getOnlinePlayers()) {
			if (pls.getGameMode() == GameMode.SPECTATOR)
				player.hidePlayer(pls);
		}

		// RankManager rank = pp.getRank();
		// FakeTeam.setTag(player, rank.toString(), rank.getPoints(),
		// rank.getTab(), null);
		/*
		 * if (pp.isVIP()) event.setJoinMessage("�8[�2+�8] �a[VIP] " +
		 * pp.getRank().getTab() + player.getName()); else {
		 * event.setJoinMessage("�8[�2+�8] �r" + pp.getRank().getTab() +
		 * player.getName()); } if (Bukkit.getOnlinePlayers().size() < 10) {
		 * ActionBar.sendActionBar(player,
		 * "�4Team interdite sous peine de sanction (si moins de 10 joueurs)");
		 * }
		 */
	}

	private void initializeMessages() {
		joinMessage.add("§eAttention! <<Player>> vient d'arriver, ça va saigner.");
		joinMessage.add("§e<<Player>> se joint à vous, et il est prêt à en découdre.");
		joinMessage.add("§eTous à couvert! <<Player>> est arrivé.");
		joinMessage.add("§e<<Player>> vient d'arriver. Il est trop OP, il faut le nerf.");
		joinMessage.add("§e<<Player>> est arrivé. La fête est terminée");
		joinMessage.add(
				"§e<<Player>> est ici pour casser des bouches et mâcher du chewing-gum. Et <<Player>> est à court de chewing-gum.");
		joinMessage.add("§e<<Player>> est tout feu tout flamme. Préparez vous ça va chauffer!");
		joinMessage.add("§e<<Player>> est de retour! Pour vous jouer un mauvais tour");

	}

	@SuppressWarnings("unchecked")
	public static String getRandomMessage(Player player) {
		Random rn = new Random();
		int index = rn.nextInt(joinMessage.size());
		return PvpBox.getInstance().getPrefix()+"§8»§7» "+((ArrayList<String>) joinMessage.clone()).get(index).replaceAll("<<Player>>", "§6"+player.getName()+"§e");
	}
}
