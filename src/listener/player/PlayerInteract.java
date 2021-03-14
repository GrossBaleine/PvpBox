package listener.player;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import launcher.PvpBox;
import menu.KitPreferencesMenu;
import menu.KitSelectionMenu;
import menu.StatisticsMenu;

public class PlayerInteract implements Listener {

	PvpBox pvpBox = PvpBox.getInstance();
	Material[] forbbidenBlocks = { Material.CHEST, Material.HOPPER, Material.HOPPER_MINECART, Material.MINECART,
			Material.COMMAND_MINECART, Material.EXPLOSIVE_MINECART, Material.STORAGE_MINECART,
			Material.POWERED_MINECART, Material.BEACON, Material.ANVIL, Material.ARMOR_STAND, Material.ITEM_FRAME,
			Material.TRAP_DOOR, Material.TRAPPED_CHEST };
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getClickedBlock() != null && event.getClickedBlock().getType() != Material.AIR) {
			if (!isAllowBlockOpening(event.getClickedBlock())){
				event.setCancelled(true);
				return;
			}
		}
		if (event.getItem() == null || event.getItem().getItemMeta() == null
				|| pvpBox.getPvpPlayers().contains(pvpBox.get(player)))
			return;
		String name = event.getItem().getItemMeta().getDisplayName();
		if (name == null)
			return;
		switch (name) {
		case "§bChoisir un kit §8(Clic-droit)":
			new KitSelectionMenu(player);
			break;
		case "§bStatistiques §8(Clic-droit)":
			new StatisticsMenu(player);
			break;
		case "§bRetour au hub §8(Clic-droit)":
			try {
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				out.writeUTF("Connect");
				out.writeUTF("hub");
				player.sendPluginMessage(PvpBox.getInstance(), "BungeeCord", out.toByteArray());
			} catch (Exception err) {
				err.printStackTrace();
			}
			break;
		case "§bEdition de kit":
			new KitPreferencesMenu(player);
			break;
		default:
			break;
		}
		return;
	}

	private boolean isAllowBlockOpening(Block bloc) {
		for (Material forbiddenMaterials : forbbidenBlocks) {
			if (bloc.getType() == forbiddenMaterials)
				return false;
		}
		return true;
	}
	
	@EventHandler
    public void onEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.ITEM_FRAME ||
        		event.getRightClicked().getType() == EntityType.MINECART_CHEST)
        	event.setCancelled(true);
    }
    

}
