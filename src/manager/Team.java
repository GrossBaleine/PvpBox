package manager;

import java.util.ArrayList;

public class Team {

	public ArrayList<PBPlayer> members;
	public PBPlayer leader;
	public String teamName;
	public int MAXMEMBERS = 5;

	public Team(String teamName, PBPlayer player) {
		this.leader = player;
		this.teamName = teamName;
		addPlayer(leader);
	}

	public void addPlayer(PBPlayer player) {
		if (members.size() >= MAXMEMBERS)
			return;
		player.deleteTeam();
		members.add(player);
		teamBroadcast(player.getPlayer().getName() + " a rejoint votre équipe.");
	}

	public void removePlayer(PBPlayer player) {
		members.remove(player);
		player.deleteTeam();
	}

	public void playerQuit(PBPlayer player) {
		removePlayer(player);
		player.getPlayer().sendMessage("Vous avez quitté votre équipe");
		if (members.size() >= 2) {
			for (PBPlayer p : members) {
				removePlayer(p);
				p.getPlayer().sendMessage("Votre team a été dissoute");
			}
		} else if (leader == player) {
			leader = members.get(0);
			teamBroadcast(leader.getPlayer().getName() + " devient le nouveau leader de la team.");
		}
	}

	public void addBanner(PBPlayer player) {
		// TODO utiliser des packets (enjoy)
	}

	public void removeBanner(PBPlayer player) {

	}

	public void teamBroadcast(String msg) {
		for (PBPlayer p : members) {
			p.getPlayer().sendMessage(msg);
		}
	}

}
