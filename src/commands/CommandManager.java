package commands;

import commands.admin.KitsCommands;
import commands.moderator.GamemodeCommand;
import launcher.PvpBox;

public class CommandManager {
	private PvpBox instance;

	public CommandManager(PvpBox instance) {
		this.instance = instance;
	}

	public void registerCommands() {
		// admin
		instance.getCommand("kit").setExecutor(new KitsCommands());
		// moderateur
		instance.getCommand("gm").setExecutor(new GamemodeCommand(instance));
	}

}
