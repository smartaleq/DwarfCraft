package com.smartaleq.bukkit.dwarfcraft;

import java.util.Arrays;
import org.bukkit.*;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Player;

public class DwarfCraftSkillTraining extends DwarfCraftPlayerSkills{


	/*
	 * Results from attemptSkillUp:
	 * return -1: not enough items
	 * return -2: skill max
	 * return -3: no such skill
	 * return 0: failed
	 * return 1: success
	 */
	public static int attemptSkillUp(int skillId, Player player){ 
		try{
			if(DwarfCraftPlayerSkills.getSkillLevel(skillId, player) == -1){return -2;}
			if(DwarfCraftPlayerSkills.getSkillLevel(skillId, player) == 30){return -2;}
			int[] skillCost;
			skillCost = new int[8];
			int newSkillLevel = DwarfCraftPlayerSkills.getSkillLevel(skillId, player);
			/* Dan thinks skillId could be removed from the arguments in this function
			   getSkillTrainingCost */
			skillCost = Skills.values()[skillId].getSkillTrainingCost(skillId, newSkillLevel, player);
			for (int i = 0; i < skillCost[0];i++){
				//check inventory quantity for needed items
				if (DwarfCraftInventory.countInventoryItems(skillCost[1+2*i], player) < skillCost[2+2*i]){return -1;}
			}
			return 1;
		}
			catch(NumberFormatException f) {return 0;}
	}
	
	/*
	 * increases a players skill by skillId then backs up skills data
	 */
	public static void increaseSkill(int skillId, Player player){
		int playerNumber = getPlayerNumber(player);
		playerSkillsArray[playerNumber][skillId]++;
		backupSkills();
		saveSkills();
	}
	
	
	/*
	 * makes a player an elf, making most skills behave similarly to vanilla
	 */
	public static void makeElf(Player player){
		int playerNumber = getPlayerNumber(player);
		playerSkillsArray[playerNumber][0] = 1;
		DwarfCraftPlayerSkills.backupSkills();
		DwarfCraftPlayerSkills.saveSkills();
	}
	
	/*
	 * resets a player to dwarfmode with all skills 0
	 */
	public static void makeDwarf(Player player){
		int playerNumber = getPlayerNumber(player);
		for(int i = 0; i < maximumSkillCount; i++){
			playerSkillsArray[playerNumber][i] = 0;
		}
		DwarfCraftPlayerSkills.backupSkills();
		DwarfCraftPlayerSkills.saveSkills();
		player.sendMessage("You're now a Dwarf");
	}
	
	/*
	 * this will count how many skills a player has above level 5
	 */
	public static int countHighSkills(Player player){
		int playerNumber = getPlayerNumber(player);
		int highSkillsCount = 0;
		for(int i = 0; i < maximumSkillCount; i++){
			if(playerSkillsArray[playerNumber][i] > 5){ highSkillsCount++;};
		}
		return highSkillsCount;
	}
	
	/*
	 * this will return the skill level of a players 4th highest skill if they have less that 16 skills above 5
	 * if they have 17+ skills above 5, it will return their top 25th percentile skill level
	 */
	public static int topQuartileThreshold(Player player){
		int[] tempArray;
		tempArray = new int[99];
		int playerNumber = getPlayerNumber(player);
		tempArray = playerSkillsArray[playerNumber];
		Arrays.sort(tempArray);
		int highestQuartileValue = Math.min(tempArray[99-(Math.min((countHighSkills(player)+3)/4,4))],5);
		return highestQuartileValue;
	}
	
	public static int topThreeQuartileThreshold(Player player){
		int[] tempArray;
		tempArray = new int[99];
		int playerNumber = getPlayerNumber(player);
		tempArray = playerSkillsArray[playerNumber];
		Arrays.sort(tempArray);
		int topThreeQuartileValue = Math.min(tempArray[99-(Math.min(3*(countHighSkills(player))/4,12))],5);
		return topThreeQuartileValue;
	}
		
	public static int playerLevel(Player player){
		int playerNumber = getPlayerNumber(player);
		int playerLevel = 5;
		int highestSkill = 0;
		
		for(int i = 0; i < maximumSkillCount; i++){
			int thisSkillLevel = playerSkillsArray[playerNumber][i];
			if(thisSkillLevel > highestSkill){highestSkill = thisSkillLevel;};
			if(thisSkillLevel > 5){playerLevel = playerLevel + thisSkillLevel - 5;};

		}
		if(playerLevel == 5){playerLevel = highestSkill;};
		return playerLevel;
	}

	public static void skillInfo(Player player, int skillId) {
		int newSkillLevel = DwarfCraftPlayerSkills.getSkillLevel(skillId, player) + 1;
		player.sendMessage("-----------------------------------------------------");
		player.sendMessage("Skill Information: " + DwarfCraftSkills.getSkillName(skillId) + " (id: " + skillId + "a)");
		player.sendMessage("To train to level " + newSkillLevel + " will cost:");
		int[] trainingCosts;
		trainingCosts = new int[7];
		trainingCosts = DwarfCraftSkills.getSkillTrainingCost(skillId, newSkillLevel, player);
		player.sendMessage("item Id: " + trainingCosts[1] + "  number required: " + trainingCosts[2]);
		if (trainingCosts[0]>1){player.sendMessage("item Id: " + trainingCosts[3] + "  number required: " + trainingCosts[4]);}
		if (trainingCosts[0]>2){player.sendMessage("item Id: " + trainingCosts[5] + "  number required: " + trainingCosts[6]);}
		player.sendMessage("-----------------------------------------------------");
	}

	public static int getSkillIdFromName(String string) {
		for(int skillId = 0; skillId < maximumSkillCount; skillId++){
			System.out.println("trying to get skill name " + string + "at skillId " + skillId);
			/* Not sure if it makes sense to check the validity of skilNameColumn anymore
			   because we define it within an enum, not some funky text file...
			   skillId might make a little more sense to check, though. Admittedly, dan didn't
			   bother to check where maximumSkillCount is made, so blurgle */
			//if(DwarfCraftSkills.skillsArray[skillId][DwarfCraftSkills.skillNameColumn] != null){
			if(skillId <= Skills.values().length-1){
			    if(string.regionMatches(0, Skills.values()[skillId].professionName, 0, 6 /*length to compare skill names*/)){return skillId;}
			}
		}
		return 0;
	}

	public static void schoolInfo(Player player) {
		// TODO Auto-generated method stub
		
	}			
}
