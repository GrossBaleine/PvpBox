package commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatisticsCommands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (!(sender instanceof Player))
			return false;
		Player player = (Player) sender;
		/*ElPlayer ep = ElenoxAPI.getElenoxAPI().getElPlayer(player);
		if (!ep.hasAccess(ElAccesLevel.ADMINISTRATEUR)) {
			player.sendMessage("§cVous ne pouvez pas effectuer cette commande.");
			return false;
		}*/
		//else{
			player.sendMessage("§c/stats <player> <kit> <set|reset>");
	//	}
		return false;
	}
}
