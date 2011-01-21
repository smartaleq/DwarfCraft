package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;


public class DwarfCraftPlayerListener extends PlayerListener {
	 private final DwarfCraft plugin;
	 
	 public DwarfCraftPlayerListener(final DwarfCraft plugin) {
	     this.plugin = plugin;
	 }
	 
	@Override
	public void onPlayerCommand(PlayerChatEvent event) {
		String[] split = event.getMessage().split(" ");
		Player player = event.getPlayer();
		String playerName = player.getDisplayName();
		if (split.length >= 1) {
			if (split[0].equalsIgnoreCase("/levelskill")) {
				if(Integer.parseInt(split[1]) >= 100 || Integer.parseInt(split[1]) < 0){return;};
				int skillId = Integer.parseInt(split[1]);
				DwarfCraftSkillTraining.increaseSkill(skillId, playerName);
				int skillLevel = DwarfCraftPlayerSkills.getSkillLevel(skillId, playerName);
				player.sendMessage("Your skill #" + skillId + " has been increased to level " + skillLevel);
				event.setCancelled(true);
			}
			else if (split[0].equalsIgnoreCase("/myskill")){
				int skillId = Integer.parseInt(split[1]);
				int skillLevel = DwarfCraftPlayerSkills.getSkillLevel(skillId, playerName);
				player.sendMessage("Your skill #" + skillId + " is currently level " + skillLevel);
				event.setCancelled(true);
			}
		}
	}
	
	void onPlayerJoin(PlayerLoginEvent event){
		Player player = event.getPlayer();
			String playerName = player.getDisplayName();
			if(DwarfCraftPlayerSkills.getPlayerNumber(playerName) == -1){
			if(DwarfCraftPlayerSkills.countPlayers() != DwarfCraftPlayerSkills.maxPlayers){
				DwarfCraftPlayerSkills.addNewPlayer(playerName);
			}
			else {
				//throw max players in database reached thingamabob
			}
		}
		else {
			if(DwarfCraftSkillTraining.isPlayerElf(playerName)){
				//welcome elf, may you die often due to your unskilled nature
			}
			else{
				//print to screen "welcome dwarf whatever of playerskilllevel whatever"
			}
		 }
	 }

}