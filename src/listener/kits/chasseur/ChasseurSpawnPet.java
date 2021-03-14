package listener.kits.chasseur;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;

import kits.Chasseur;
import launcher.PvpBox;
import manager.PBPlayer;

public class ChasseurSpawnPet implements Listener {

	@EventHandler
	public void spawnPet(PlayerInteractEvent event) {
		if (event.getMaterial() == Material.MONSTER_EGG) {
			SpawnEggMeta sem = (SpawnEggMeta) event.getItem().getItemMeta();
			if (sem.getSpawnedType() == EntityType.WOLF) {
				PBPlayer player = PvpBox.getInstance().get(event.getPlayer());
				Chasseur.spawnWolf(player);
				event.setCancelled(true);
			}
		}
	}

	@EventHandler 
	public void spawnPetOnEntity(PlayerInteractAtEntityEvent event){
		if(event.getRightClicked() instanceof Wolf){
			ItemStack hand = null;
			if(event.getHand() == EquipmentSlot.HAND)
				hand = event.getPlayer().getInventory().getItemInMainHand();
			else
				hand = event.getPlayer().getInventory().getItemInOffHand();
			if(hand.getType() == Material.MONSTER_EGG){
				SpawnEggMeta sem = (SpawnEggMeta) hand.getItemMeta();
				if (sem.getSpawnedType() == EntityType.WOLF) {
					
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void cancelSpawnCreaturesByEgg(CreatureSpawnEvent event){
		if(event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)
			event.setCancelled(true);
	}
}
