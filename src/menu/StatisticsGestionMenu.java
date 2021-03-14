package menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import manager.PBPlayer;

public class StatisticsGestionMenu {

	private PBPlayer player;
	private Player watcher;
	private Inventory[] lastInv = null;
	
	public StatisticsGestionMenu(PBPlayer player, Player watcher) {
		this.player = player;
		this.watcher = watcher;
	}
	
	public Inventory InventoryRoot(){
		
		return null;
	}
	
	public void updateInventory(Inventory inv){
		
	}
	
	public void entete(){
		
	}
}
