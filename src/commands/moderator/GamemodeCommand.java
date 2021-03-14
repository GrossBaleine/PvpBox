package commands.moderator;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import launcher.PvpBox;
import listener.player.PlayerJoin;
import listener.player.PlayerQuit;

public class GamemodeCommand implements CommandExecutor {

	private PvpBox pvpBox;

	public GamemodeCommand(PvpBox pvpBox) {
		this.pvpBox = pvpBox;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (!(sender instanceof Player))
			return false;
		Player player = (Player) sender;
		/*ElPlayer ep = ElenoxAPI.getElenoxAPI().getElPlayer(player);
		if (!ep.hasAccess(ElAccesLevel.MODERATRICE) || !ep.hasAccess(ElAccesLevel.MODERATEUR)) {
			player.sendMessage("§cVous ne pouvez pas effectuer cette commande.");
			return false;
		}*/
		if (player.getGameMode() == GameMode.SPECTATOR) {
			player.teleport(pvpBox.getSpawnLocation());
			player.setGameMode(GameMode.ADVENTURE);
			pvpBox.get(player).removePvpMode();
			pvpBox.broadcast(PlayerJoin.getRandomMessage(player));
			for (Player pls : Bukkit.getOnlinePlayers()) {
				pls.showPlayer(player);
			}
		} else {
			pvpBox.get(player).removePvpMode();
			player.setGameMode(GameMode.SPECTATOR);
			for (Player pls : Bukkit.getOnlinePlayers()) {
				pls.hidePlayer(player);
			}
			pvpBox.broadcast(PlayerQuit.getRandomMessage(player));
		}
		return true;
	}
}
