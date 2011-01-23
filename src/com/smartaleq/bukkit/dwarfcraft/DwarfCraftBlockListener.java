package com.smartaleq.bukkit.dwarfcraft;

import java.util.*;
import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;



/*
 * This java file watches for broken blocks and passes info to SkillEffects
 * 
 */
public class DwarfCraftBlockListener extends BlockListener {
	 private final DwarfCraft plugin;
	 
	 public DwarfCraftBlockListener(final DwarfCraft plugin) {
	     this.plugin = plugin;
	 }
	 
	 public void onBlockDamage(BlockDamageEvent event)
	{
		if (event.getDamageLevel() == org.bukkit.block.BlockDamageLevel.BROKEN && !event.isCancelled())
		{	
			/* 
			 * capture data about destroyed block
			 */
			Block block = event.getBlock();
			Location destroyedBlockLocation = new Location(event.getBlock().getWorld(), event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ(), 0, 0);
			int destroyedBlockType = block.getTypeId();
//			int destroyedBlockMeta = block.getData();
			Player player = event.getPlayer();
			/* 
			 * check to see if block destroyed has itemdrop effects listed
			 */
			List<SkillEffects> applicableEffects = SkillEffects.getEffects(destroyedBlockType, "itemdrop");
			if (applicableEffects.size() == 0)
				return;
			// DAN SAYS: When you have an if(cond) return; else { stuff }
			// you can ommit the else, and just state 'stuff'.
			// DELETE THIS COMMENT once you've read it ^^^^^

			//replace block with air and drop appropriate results
			event.setCancelled(true); 	
			block.setType(Material.AIR);
			for(SkillEffects se : applicableEffects) {
			    System.out.println("for effect # " + se.effectId);
			    //elves get the elf level (close as possible to 
			    int playerSkillLevel;
			    if(DwarfCraftPlayerSkills.isPlayerElf(player)){
				playerSkillLevel = se.elfEffectLevel;
			    }
			    else {
				playerSkillLevel = DwarfCraftPlayerSkills.getSkillLevel(se.getSkillForEffect(), player);
			    }					
			    if (/* Output Block*/ se.createdItemId > 0){
				event.getBlock().getWorld().dropItem(
								     destroyedBlockLocation,
								     new ItemStack(
										   // Output Block
										   se.createdItemId,
										   // Output Count
										   se.getRandomBlockCount(playerSkillLevel),
										   // OutputDamage
										   (byte)0
										   )
								     );
			    }
			}
		}
	}
}
