package manager;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class ProtectionArea {

	private Location center;
	private int radius;
	
	public ProtectionArea(Location center, int radius){
		this.center = center;
		this.radius = radius;
	}
	
	public boolean isInsideArea(Entity ent){
		return(ent.getLocation().distance(center)<=radius);
	}
	
}
