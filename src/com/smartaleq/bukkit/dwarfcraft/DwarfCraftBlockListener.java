package com.smartaleq.bukkit.dwarfcraft;

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
			String playerName = player.getDisplayName();

			/* 
			 * check to see if block destroyed has itemdrop effects listed
			 */
			int applicableEffects[] = new int[7]; 
			applicableEffects = DwarfCraftSkillEffects.getEffects(destroyedBlockType, "itemdrop");
			if (applicableEffects[0] == 0) {
				return;}
			else {
				//replace block with air and drop appropriate results
				event.setCancelled(true); 	
				block.setType(Material.AIR);	
				for(int i=1; i <= applicableEffects[0]; i++) {
					System.out.println("for effect # " + i);
					int effectId = applicableEffects[i];
					int skillId = DwarfCraftSkillEffects.getSkillForEffect(effectId);
					int playerSkillLevel;
					//elves get the elf level (close as possible to 
					if(DwarfCraftPlayerSkills.isPlayerElf(playerName)){
						playerSkillLevel = DwarfCraftSkillEffects.getElfLevel(effectId);
					}
					else {
						playerSkillLevel = DwarfCraftPlayerSkills.getSkillLevel(skillId, playerName);
					}
					
					double effectBenefit = DwarfCraftSkillEffects.getEffectValue(effectId, playerSkillLevel);
					int outputBlock = DwarfCraftSkillEffects.getEffectResult(effectId);
					int outputCount = DwarfCraftSkillEffects.getRandomBlockCount(effectBenefit);
					byte outputDamage = (byte)0;
					event.getBlock().getWorld().dropItem(destroyedBlockLocation, new ItemStack(outputBlock, outputCount, outputDamage));
				}
			}
		}
	}
}
