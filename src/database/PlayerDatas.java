package database;

public class PlayerDatas {
	
/*
	public static void loadPlayerDatas(PBPlayer player) throws SQLException {
		String uuid = player.getPlayer().getUniqueId().toString();
		PreparedStatement request = ElenoxAPI.getElenoxAPI().getSQLBridge().getData()
				.prepareStatement("SELECT * from PvPBox_joueurs WHERE UUID = '" + uuid + "'");
		ResultSet result = request.executeQuery();
		if (result.first()) {
			player.setPoints(result.getInt("Points"));
			loadStats(player);
		}

		else
			createPlayer(player);
		// suite du loading
		result.close();
		request.close();
	}

	public static void exportPlayerDatas(PBPlayer player) throws SQLException {
		String uuid = player.getPlayer().getUniqueId().toString();
		String pseudo = player.getPlayer().getName();
		int points = player.getPoints();
		PreparedStatement request = ElenoxAPI.getElenoxAPI().getSQLBridge().getData()
				.prepareStatement("UPDATE PvPBox_joueurs SET Points = '" + points + "', Pseudo = '" + pseudo
						+ "' WHERE UUID = '" + uuid + "'");
		request.executeUpdate();
		request.close();
		saveStats(player);
	}

	private static void saveStats(PBPlayer player) throws SQLException {
		String uuid = player.getPlayer().getUniqueId().toString();
		String pseudo = player.getPlayer().getName();
		for (Statistics stats : player.getStats()) {
			String kit = stats.getKit().getName();
			int kills = stats.getKills();
			int deaths = stats.getDeaths();
			boolean availability = stats.getAvailability();
			int selectedKit = stats.getSelectedKit();
			int units = stats.getUnits();
			PreparedStatement request = ElenoxAPI.getElenoxAPI().getSQLBridge().getData()
					.prepareStatement("UPDATE PvPBox_KitStats SET "
							+ "Pseudo = ? ,Kills = ? ,Disponibilite = ? , Deaths = ? , Unites = ? , Nb_Selection = ? "
							+ "WHERE( UUID = '" + uuid + "' AND Kit = '" + kit + "')");
			request.setString(1, pseudo);
			request.setInt(2, kills);
			request.setBoolean(3, availability);
			request.setInt(4, deaths);
			request.setInt(5, units);
			request.setInt(6, selectedKit);
			request.executeUpdate();
			request.close();
		}

	}

	private static void loadStats(PBPlayer player) throws SQLException {
		String uuid = player.getPlayer().getUniqueId().toString();
		PreparedStatement request = ElenoxAPI.getElenoxAPI().getSQLBridge().getData()
				.prepareStatement("SELECT * from PvPBox_KitStats WHERE UUID = '" + uuid + "'");
		ResultSet result = request.executeQuery();
		while (result.next()) {
			for (Statistics stats : player.getStats()) {
				if (stats.getKit().getName().equals(result.getString("Kit"))) {
					stats.setAvailability(result.getBoolean("Disponibilite"));
					stats.setUnits(result.getInt("Unites"));
					stats.setKills(result.getInt("Kills"));
					stats.setDeaths(result.getInt("Deaths"));
					stats.setSelectedKit(result.getInt("Nb_Selection"));
				}
			}
		}
		result.close();
		request.close();
	}

	private static void createPlayer(PBPlayer player) throws SQLException {
		String uuid = player.getPlayer().getUniqueId().toString();
		String pseudo = player.getPlayer().getName();
		int points = player.getPoints();
		PreparedStatement createPlayer = ElenoxAPI.getElenoxAPI().getSQLBridge().getData()
				.prepareStatement("INSERT INTO PvPBox_joueurs (UUID, Pseudo, Points) VALUES ('" + uuid + "','" + pseudo
						+ "','" + points + "')");
		createPlayer.executeUpdate();
		createPlayer.close();
		PvpBox.getInstance();
		for (Statistics stats : player.getStats()) {
			String kit = stats.getKit().getName();
			int kills = stats.getKills();
			int deaths = stats.getDeaths();
			boolean availability = stats.getAvailability();
			int selectedKit = stats.getSelectedKit();
			int units = stats.getUnits();
			PreparedStatement createStats = ElenoxAPI.getElenoxAPI().getSQLBridge().getData().prepareStatement(
					"INSERT INTO PvPBox_KitStats (UUID, Pseudo, Kit, Unites, Disponibilite, Kills, Deaths, Nb_Selection) "
							+ "VALUES (?,?,?,?,?,?,?,?)");
			createStats.setString(1, uuid);
			createStats.setString(2, pseudo);
			createStats.setString(3, kit);
			createStats.setInt(4, units);
			createStats.setBoolean(5, availability);
			createStats.setInt(6, kills);
			createStats.setInt(7, deaths);
			createStats.setInt(8, selectedKit);
			createStats.executeUpdate();
			createStats.close();
		}
	}
*/
}
