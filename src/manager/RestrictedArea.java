package manager;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class RestrictedArea {

	private Location center;
	private int warnRadius;
	private int killRadius;

	public RestrictedArea(Location center, int warnRadius, int killRadius) {
		this.center = center;
		this.warnRadius = warnRadius;
		this.killRadius = killRadius;
	}

	public boolean isInsideArea(Entity ent) {
		Location loc = new Location(ent.getWorld(), ent.getLocation().getX(), 0, ent.getLocation().getZ());
		Location center = new Location(this.center.getWorld(), this.center.getX(), 0, this.center.getZ());
		return (loc.distance(center) >= warnRadius);
	}

	public boolean isKillable(Entity ent) {
		Location loc = new Location(ent.getWorld(), ent.getLocation().getX(), 0, ent.getLocation().getZ());
		Location center = new Location(this.center.getWorld(), this.center.getX(), 0, this.center.getZ());
		return (loc.distance(center) >= killRadius);
	}
}
