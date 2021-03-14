package kits;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import manager.PBPlayer;
import utils.RefillItems;

public abstract class Kit {

	protected int FinalPrice;
	protected int UnitPrice;
	protected String name;
	protected ItemStack menuItem;
	protected ItemStack[] armor = new ItemStack[4];
	protected ArrayList<ItemStack> stuff;
	protected ArrayList<String> description;
	protected int slot;
	protected ArrayList<PotionEffect> constantEffects;
	protected ArrayList<RefillItems> refillItems;
	
	public Kit() {
		stuff = new ArrayList<ItemStack>();
		description = new ArrayList<String>();
		constantEffects = new ArrayList<PotionEffect>();
		refillItems = new ArrayList<RefillItems>();
	}

	public int getUnitPrice() {
		return UnitPrice;
	}

	public int getFinalPrice() {
		return FinalPrice;
	}

	public String getName() {
		return name;
	}

	public ItemStack getItem() {
		return menuItem;
	}

	public int getSlot() {
		return slot;
	}

	public ArrayList<ItemStack> getStuff() {
		return stuff;
	}

	public ItemStack[] getArmor() {
		return armor;
	}
	
	public ArrayList<RefillItems> getRefillItems(){
		return refillItems;
	}

	public abstract void setStuff();

	public abstract void setItemDescription();

	public ArrayList<PotionEffect> getConstantEffects() {
		return constantEffects;
	}
	
	public abstract void customAdd(PBPlayer player);
}
