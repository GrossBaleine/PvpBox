package kits;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import manager.PBPlayer;
import utils.ItemBuilder;

public class Invocateur extends Kit {
	
	@SuppressWarnings("deprecation")
	public Invocateur () {
		super();
		name = "Invocateur";
		menuItem = new ItemBuilder(new ItemStack(Material.MONSTER_EGG, 1, EntityType.BLAZE.getTypeId()), "§6Kit: Invocateur", new String());
		UnitPrice = 30 ;
		FinalPrice = 500 ;
		this.slot = 8;
	}
	
	public void setStuff(){
		
		//super.stuff.add(super.menuItem.getType());
		
	}

	@Override
	public void setItemDescription() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void customAdd(PBPlayer player) {
		// TODO Auto-generated method stub
		
	}

}
