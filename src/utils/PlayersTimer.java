package utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PlayersTimer {

	private Player player;
	private Entity entity;
	private double timer;

	public PlayersTimer(Player player, Entity entity, double timer) {
		this.player = player;
		this.entity = entity;
		this.timer = timer;
	}

	public void decrement() {
		timer -= 1;
	}

	public double getTimer() {
		return timer;
	}

	public Player getPlayer() {
		return player;
	}

	public Entity getEntity() {
		return entity;
	}
}
