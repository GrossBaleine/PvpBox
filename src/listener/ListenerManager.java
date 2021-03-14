package listener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import listener.block.BlockBreak;
import listener.block.BlockPlace;
import listener.entity.EntityChangeBlock;
import listener.entity.EntityDamage;
import listener.entity.EntityDeath;
import listener.entity.EntityExplode;
import listener.entity.EntityPotionEffects;
import listener.entity.EntityTargetChange;
import listener.entity.ProjectileHit;
import listener.inventory.InventoryClick;
import listener.inventory.InventoryClose;
import listener.inventory.InventoryDrag;
import listener.inventory.InventoryKitEdition;
import listener.kits.assassin.AssassinInvisibility;
import listener.kits.cavalier.CavalierDamage;
import listener.kits.cavalier.CavalierMount;
import listener.kits.chasseur.ChasseurHit;
import listener.kits.chasseur.ChasseurSpawnPet;
import listener.kits.mage.MageFireball;
import listener.kits.tank.TankHook;
import listener.kits.vampire.VampireDamages;
import listener.kits.vampire.VampireDeath;
import listener.kits.vampire.VampireTransform;
import listener.player.PlayerDealDamage;
import listener.player.PlayerDeath;
import listener.player.PlayerDropItem;
import listener.player.PlayerFoodLevel;
import listener.player.PlayerInteract;
import listener.player.PlayerItemDamage;
import listener.player.PlayerJoin;
import listener.player.PlayerMove;
import listener.player.PlayerQuit;
import listener.player.PlayerReceiveDamage;
import listener.player.PlayerRespawn;

public class ListenerManager {

	private Plugin plugin;

	public ListenerManager(Plugin plugin) {
		this.plugin = plugin;
		registerListener();
	}

	public void registerListener() {
		PluginManager pm = Bukkit.getPluginManager();
		//ENTITY
		pm.registerEvents(new EntityChangeBlock(), plugin);
		pm.registerEvents(new EntityDamage(), plugin);
		pm.registerEvents(new EntityDeath(), plugin);
		pm.registerEvents(new EntityExplode(), plugin);
		pm.registerEvents(new EntityPotionEffects(), plugin);
		pm.registerEvents(new EntityTargetChange(), plugin);
		pm.registerEvents(new ProjectileHit(), plugin);
		// BLOCK
		pm.registerEvents(new BlockBreak(), plugin);
		pm.registerEvents(new BlockPlace(), plugin);
		// INVENTORY
		pm.registerEvents(new InventoryClick(), plugin);
		pm.registerEvents(new InventoryClose(), plugin);
		pm.registerEvents(new InventoryDrag(), plugin);
		pm.registerEvents(new InventoryKitEdition(), plugin);
		// KITS
		pm.registerEvents(new AssassinInvisibility(), plugin);
		pm.registerEvents(new CavalierDamage(), plugin);
		pm.registerEvents(new CavalierMount(), plugin);
		pm.registerEvents(new ChasseurHit(), plugin);
		pm.registerEvents(new ChasseurSpawnPet(), plugin);
		pm.registerEvents(new MageFireball(), plugin);
		pm.registerEvents(new TankHook(), plugin);
		pm.registerEvents(new VampireDamages(), plugin);
		pm.registerEvents(new VampireDeath(), plugin);
		pm.registerEvents(new VampireTransform(), plugin);
		// PLAYER
		pm.registerEvents(new PlayerDealDamage(), plugin);
		pm.registerEvents(new PlayerDeath(), plugin);
		pm.registerEvents(new PlayerDropItem(), plugin);
		pm.registerEvents(new PlayerFoodLevel(), plugin);
		pm.registerEvents(new PlayerInteract(), plugin);
		pm.registerEvents(new PlayerItemDamage(), plugin);
		pm.registerEvents(new PlayerJoin(), plugin);
		pm.registerEvents(new PlayerMove(), plugin);
		pm.registerEvents(new PlayerQuit(), plugin);
		pm.registerEvents(new PlayerReceiveDamage(), plugin);
		pm.registerEvents(new PlayerRespawn(), plugin);
	}
}
