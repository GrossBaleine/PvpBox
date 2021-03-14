package commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kits.Kit;
import launcher.PvpBox;
import manager.PBPlayer;

public class KitsCommands implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player player = (Player) sender;
		/*ElPlayer ep = ElenoxAPI.getElenoxAPI().getElPlayer(player);
		if (!ep.hasAccess(ElAccesLevel.ADMINISTRATEUR))
			player.sendMessage("§cVous ne pouvez pas effectuer cette commande.");*/
		if (args.length != 4 || ((!args[1].equals("remove") && !args[1].equals("add"))
				|| (!args[3].equals("unit") && !args[3].equals("permanent")))) {
			player.sendMessage("§c/kit <player | all> <add | remove> <kit> <permanent | unit>");
			return false;
		} else {
			if (args[0].equals("all")) {
				for (PBPlayer pbPlayer : PvpBox.getInstance().getPBPlayers()) {
					kitModifier(pbPlayer, args, player);
					player.sendMessage("§6" + pbPlayer.getPlayer().getName() + "§a a bien reçu son kit");
				}
				sender.sendMessage("§aTous les joueurs ont bien reçu leur kit");
				return true;
			} else {
				for (PBPlayer pbPlayer : PvpBox.getInstance().getPBPlayers()) {
					if (pbPlayer.getPlayer().getName().equals(args[0])) {
						kitModifier(pbPlayer, args, player);
						player.sendMessage("§6" + pbPlayer.getPlayer().getName() + "§a a bien reçu son kit");
						return true;
					}
				}
				sender.sendMessage("§cCe joueur n'est pas connecté.");
			}
		}
		return false;
	}

	public void kitModifier(PBPlayer pbPlayer, String[] args, Player sender) {
		Kit kit = PvpBox.getInstance().getKit(args[2]);
		if (kit == null) {
			sender.sendMessage(
					"§cChoisissez un de ces arguments: <Guerrier | Archer | Tank | Assassin | Cavalier | Chasseur | Mage | Vampire | Invocateur>");
		}
		if (args[1].equals("add") && args[3].equals("permanent")) {
			pbPlayer.getKitStats(kit).setAvailability(true);
			pbPlayer.getPlayer().sendMessage(
					"§6" + sender.getName() + "§a vous a offert le kit §6" + kit.getName() + "§a permanent.");
		} else if (args[1].equals("remove") && args[3].equals("permanent")) {
			pbPlayer.getKitStats(kit).setAvailability(false);
			pbPlayer.getPlayer().sendMessage(
					"§6" + sender.getName() + "§a vous a retiré le kit §6" + kit.getName() + "§a permanent.");
		} else if (args[1].equals("add") && args[3].equals("unit")) {
			pbPlayer.addUnitKit(kit);
			pbPlayer.getPlayer().sendMessage(
					"§6" + sender.getName() + "§a vous a offert un kit §6" + kit.getName() + "§a unitaire.");
		} else if (args[1].equals("remove") && args[3].equals("unit")) {
			pbPlayer.removeUnitKit(kit);
			pbPlayer.getPlayer().sendMessage(
					"§6" + sender.getName() + "§a vous a retiré un kit §6" + kit.getName() + "§a unitaire.");
		} else {
			sender.sendMessage("§cLa commande rentrée est invalide");
		}
	}

}
