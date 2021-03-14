package commands.player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (!(sender instanceof Player))
			return false;
		Player player = (Player) sender;
		//ElPlayer ep = ElenoxAPI.getElenoxAPI().getElPlayer(player);
		
		
		return false;
	}

}
