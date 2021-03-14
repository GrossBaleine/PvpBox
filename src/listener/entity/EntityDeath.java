package listener.entity;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import launcher.PvpBox;
import manager.PBPlayer;
import manager.Pet;

public class EntityDeath implements Listener {

	@SuppressWarnings("unchecked")
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		event.getDrops().clear();
		event.setDroppedExp(0);
		if (event.getEntity().getPassengers() != null) {
			for (Entity ent : event.getEntity().getPassengers()) {
				event.getEntity().removePassenger(ent);
			}
		}
		ArrayList<PBPlayer> pvpPlayers = (ArrayList<PBPlayer>) PvpBox.getInstance().getPvpPlayers().clone();
		for(PBPlayer players : pvpPlayers){
			ArrayList<Pet> pets = (ArrayList<Pet>) players.getPets().clone();
			for(Pet pet : pets){
				if(pet.getCreature() == event.getEntity())
					players.getPets().remove(pet);
			}
		}
		
	}
}
