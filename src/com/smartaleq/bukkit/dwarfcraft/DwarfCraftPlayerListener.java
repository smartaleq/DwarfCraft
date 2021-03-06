package com.smartaleq.bukkit.dwarfcraft;

import java.io.IOException;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
// import org.bukkit.event.player.PlayerJoinEvent;
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
						player.sendMessage("Caught help");
						event.setCancelled(true);
						return;
					}
					if (split[1].equalsIgnoreCase("skillinfo")) {
						player.sendMessage("Caught skillinfo");
						int skillId;
						if(Integer.parseInt(split[2]) >0 && Integer.parseInt(split[2])<100){
							skillId = Integer.parseInt(split[2]);
						}
						else {
							if(split[3] != null){split[2] = split[2] + " " + split[3];};
							skillId = DwarfCraftSkillTraining.getSkillIdFromName(split[2]);
						}
						if(skillId == 0) {
							player.sendMessage("Couldn't Find skillId or skillName: " + split[2]);
							}
						else{
							DwarfCraftSkillTraining.skillInfo(player, skillId);
						}
						event.setCancelled(true);
						return;
					}
					if (split[1].equalsIgnoreCase("schoolinfo")) {
						player.sendMessage("Caught schoolinfo");
						DwarfCraftSkillTraining.schoolInfo(player);
						event.setCancelled(true);
						return;
					}
					if (split[1].equalsIgnoreCase("canitrain")) {
						player.sendMessage("Caught canitrain");
						int skillId = Integer.parseInt(split[2]);
						int trainResult = DwarfCraftSkillTraining.attemptSkillUp(skillId, player);
						if(trainResult == 1){
							player.sendMessage("Yes, you can train this skill right now!");
						}
						else if (trainResult == -1){
							player.sendMessage("No. You need more payment, check cost with /dc skillinfo <id>");
						}
						else if (trainResult == -2){
							player.sendMessage("No. You're already max level!");
						}
						else if (trainResult == -3){
							player.sendMessage("No. There is no such skill!");
						}
						else {
							player.sendMessage("No. training wouldn't work");
						}
						event.setCancelled(true);
						return;
					}
					if (split[1].equalsIgnoreCase("train")) {
						player.sendMessage("Caught train");
						int skillId = Integer.parseInt(split[2]);
						if(DwarfCraftSkillTraining.attemptSkillUp(skillId, player) == 1){
							DwarfCraftSkillTraining.increaseSkill(skillId, player);
						}
						player.sendMessage("Skill level increased!");
						DwarfCraftSkillTraining.skillInfo(player, skillId);
						event.setCancelled(true);
						return;
					}
					if (split[1].equalsIgnoreCase("increaseskill")) {
						player.sendMessage("Caught increaseskill");
						if(Integer.parseInt(split[2]) < 100 && Integer.parseInt(split[2]) > 0){
							int skillId = Integer.parseInt(split[2]);
							DwarfCraftSkillTraining.increaseSkill(skillId, player);
							int skillLevel = DwarfCraftPlayerSkills.getSkillLevel(skillId, player);
							player.sendMessage("Your skill #" + skillId + " has been increased to level " + skillLevel);
							event.setCancelled(true);
							return;
						}
						else throw new IOException( "Bad argument");
					}
					else if (split[1].equalsIgnoreCase("skillsheet")){
						Player viewer = player;
						if (split.length == 3 ){
							String playerViewedName = split[2];
							// player  = DwarfCraft.getserver().getplayer(playerViewedName); not working due to getserver() vs staticness
						}
						DwarfCraftPlayerSkills.skillSheet(player, viewer);
						event.setCancelled(true);
						return;
					}
					else if (split[1].equalsIgnoreCase("makemeadwarf")){
						if(!DwarfCraftPlayerSkills.isPlayerElf(player)){
							player.sendMessage("You're already a dwarf. If you want to reset                     skills  use /dc REALLYmakemeadwarf");
							event.setCancelled(true);
							return;
						}
						player.sendMessage("A wise decision. One second...*poof*");
						DwarfCraftSkillTraining.makeDwarf(player);
						player.sendMessage("welcome back to the dwarven brotherhood!");
						event.setCancelled(true);
						return;
					}
					else if (split[1].equalsIgnoreCase("reallymakemeadwarf")){
						player.sendMessage("Alright, I guess...*poof*");
						DwarfCraftSkillTraining.makeDwarf(player);
						player.sendMessage("welcome back to the dwarven brotherhood!");
						event.setCancelled(true);
						return;
					}
					else if (split[1].equalsIgnoreCase("makemeanelf")){
						player.sendMessage("Elves have no skill levels and act like a vanilla player");
						player.sendMessage("if this is what you want, try saying /dc iREALLYwanttobeanelf");
						event.setCancelled(true);
						return;
					}
					else if (split[1].equalsIgnoreCase("ireallywanttobeanelf")){
						player.sendMessage("Ok, wussy...*poof*");
						DwarfCraftSkillTraining.makeElf(player);
						player.sendMessage("Now you're an elf! *ick!*");
						event.setCancelled(true);
						return;
					}
					else {
						player.sendMessage("DC command not found");
						event.setCancelled(true);
					}
				}
			}
			catch (IOException e) {return;}
			catch (NumberFormatException f) {return;}
		}
	}

	@Override
	public void onPlayerJoin(PlayerEvent event){
		Player player = event.getPlayer();
		String playerName = player.getDisplayName();
		if(DwarfCraftPlayerSkills.getPlayerNumber(player) == -1){
			int playerCount = DwarfCraftPlayerSkills.countPlayers();
			if(playerCount != DwarfCraftPlayerSkills.maxPlayers){
				DwarfCraftPlayerSkills.addNewPlayer(player);
				playerCount++;
				System.out.println("There are now " + playerCount + " players registered with DwarfCraft");
			}
			else {
				//throw max players in database reached thingamabob
			}
		}
		else {
			if(DwarfCraftSkillTraining.isPlayerElf(player)){
				player.sendMessage("Welcome, elf " + playerName);
				//welcome elf, may you die often due to your unskilled nature
			}
			else{
				player.sendMessage("Welcome, dwarf " + playerName);
				//print to screen "welcome dwarf whatever of playerskilllevel whatever"
			}
		 }
	 }

}