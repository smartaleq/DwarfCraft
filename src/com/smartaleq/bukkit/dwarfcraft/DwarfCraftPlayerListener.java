package com.smartaleq.bukkit.dwarfcraft;

import java.io.IOException;

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
			try{
				if (split[0].equalsIgnoreCase("/dc")){
					if (split[1].equalsIgnoreCase("help")) {
						//print help info
					}
					if (split[1].equalsIgnoreCase("skillinfo")) {
						int skillId;
						if(Integer.parseInt(split[2]) >0 && Integer.parseInt(split[2])<100){
							skillId = Integer.parseInt(split[2]);
						}
						else {
							if(split[3] != null){split[2] = split[2] + " " + split[3];};
							skillId = DwarfCraftSkillTraining.getSkillIdFromName(split[2]);
							if(skillId == 0) {player.sendMessage("Couldn't Find skillId or skillName: " + split[2]);}
							DwarfCraftSkillTraining.skillInfo(player, playerName, skillId);
						}
					}
					if (split[1].equalsIgnoreCase("schoolinfo")) {
						DwarfCraftSkillTraining.schoolInfo(player);
					}
					if (split[1].equalsIgnoreCase("canitrain")) {
						int skillId = Integer.parseInt(split[2]);
						int trainResult = DwarfCraftSkillTraining.attemptSkillUp(skillId, playerName, player);
						if(trainResult == 1){
							player.sendMessage("Yes, you can train this skill right now!");
						}
						else if (trainResult == -1){
							player.sendMessage("No. You need more payment, check cost with /dc skillinfo <id>");
						}
						else if (trainResult == -2){
							player.sendMessage("No. You're already max level!");
						}
						else {
							player.sendMessage("No. training wouldn't work");
				}				
					}
					if (split[1].equalsIgnoreCase("train")) {
						int skillId = Integer.parseInt(split[2]);
						if(DwarfCraftSkillTraining.attemptSkillUp(skillId, playerName, player) == 1){
							DwarfCraftSkillTraining.increaseSkill(skillId, playerName);
						}
						player.sendMessage("Skill level increased!");
						DwarfCraftSkillTraining.skillInfo(player, playerName, skillId);
					}
					if (split[1].equalsIgnoreCase("increaseskill")) {
						if(Integer.parseInt(split[1]) < 100 && Integer.parseInt(split[1]) > 0){
							int skillId = Integer.parseInt(split[2]);
							DwarfCraftSkillTraining.increaseSkill(skillId, playerName);
							int skillLevel = DwarfCraftPlayerSkills.getSkillLevel(skillId, playerName);
							player.sendMessage("Your skill #" + skillId + " has been increased to level " + skillLevel);
							event.setCancelled(true);
						}
						else throw new IOException( "Bad argument");
					}
					else if (split[1].equalsIgnoreCase("skillsheet")){
						String declaredName = split[2];
						if (declaredName.length() >= 1 ){
							playerName = declaredName;
						}
						DwarfCraftPlayerSkills.skillSheet(playerName, player);
						event.setCancelled(true);
					}
					else if (split[1].equalsIgnoreCase("makemeadwarf")){
						player.sendMessage("A wise decision. One second...*poof*");
						DwarfCraftSkillTraining.makeDwarf(playerName);
						player.sendMessage("welcome back to the dwarven brotherhood!");
					}
					else if (split[1].equalsIgnoreCase("makemeanelf")){
						player.sendMessage("If you become an elf you lose all skill levels and act almost like a vanilla player");
						player.sendMessage("if this is what you want, try saying /dc ireallywanttobeanelf");
					}
					else if (split[1].equalsIgnoreCase("ireallywanttobeanelf")){
						player.sendMessage("Ok, wussy...*poof*");
						DwarfCraftSkillTraining.makeElf(playerName);
						player.sendMessage("Now you're an elf! *ick!*");
					}
					else player.sendMessage("DC command not found");
				}
			}
			catch (IOException e) {return;}
			catch (NumberFormatException f) {return;}
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
				player.sendMessage("you are an elf");
				//welcome elf, may you die often due to your unskilled nature
			}
			else{
				player.sendMessage("you are a dwarf");
				//print to screen "welcome dwarf whatever of playerskilllevel whatever"
			}
		 }
	 }

}