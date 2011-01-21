package com.smartaleq.bukkit.dwarfcraft;

import java.util.Arrays;

public class DwarfCraftSkillTraining extends DwarfCraftPlayerSkills{


	
	public static void attemptSkillUp(int skillId, String playerName){
		//do you have enough items
		
		//remove items and increase skill OR
		
		//say you don't have enough items
		
	}
	
	/*
	 * increases a players skill by skillId then backs up skills data
	 */
	public static void increaseSkill(int skillId, String playerName){
		int playerNumber = getPlayerNumber(playerName);
		playerSkillsArray[playerNumber][skillId]++;
		backupSkills();
		saveSkills();
	}
	
	
	/*
	 * makes a player an elf, making most skills behave similarly to vanilla
	 */
	public static void makeElf(String playerName){
		int playerNumber = getPlayerNumber(playerName);
		playerSkillsArray[playerNumber][0] = 1;
		// chat you're an elf you pussy
	}
	
	/*
	 * resets a player to dwarfmode with all skills 0
	 */
	public static void makeDwarf(String playerName){
		int playerNumber = getPlayerNumber(playerName);
		for(int i = 0; i < totalColumns; i++){
			playerSkillsArray[playerNumber][i] = 0;
		}
		// chat you're now a dwarvenly dwarf!
	}
	/*
	 * this will count how many skills a player has above level 5
	 */
	public static int countHighSkills(String playerName){
		int playerNumber = getPlayerNumber(playerName);
		int highSkillsCount = 0;
		for(int i = 0; i < totalColumns; i++){
			if(playerSkillsArray[playerNumber][i] > 5){ highSkillsCount++;};
		}
		return highSkillsCount;
	}
	
	/*
	 * this will return the skill level of a players 4th highest skill if they have less that 16 skills above 5
	 * if they have 17+ skills above 5, it will return their top 25th percentile skill level
	 */
	public static int topQuartileThreshold(String playerName){
		int[] tempArray;
		tempArray = new int[99];
		int playerNumber = getPlayerNumber(playerName);
		tempArray = playerSkillsArray[playerNumber];
		Arrays.sort(tempArray);
		int highestQuartileValue = Math.min(tempArray[99-(Math.min((countHighSkills(playerName)+3)/4,4))],5);
		return highestQuartileValue;
	}
	
	public static int topThreeQuartileThreshold(String playerName){
		int[] tempArray;
		tempArray = new int[99];
		int playerNumber = getPlayerNumber(playerName);
		tempArray = playerSkillsArray[playerNumber];
		Arrays.sort(tempArray);
		int topThreeQuartileValue = Math.min(tempArray[99-(Math.min(3*(countHighSkills(playerName))/4,12))],5);
		return topThreeQuartileValue;
	}
		
	public static int playerLevel(String playerName){
		int playerNumber = getPlayerNumber(playerName);
		int playerLevel = 5;
		int highestSkill = 0;
		
		for(int i = 0; i < totalColumns; i++){
			int thisSkillLevel = playerSkillsArray[playerNumber][i];
			if(thisSkillLevel > highestSkill){highestSkill = thisSkillLevel;};
			if(thisSkillLevel > 5){playerLevel = playerLevel + thisSkillLevel - 5;};

		}
		if(playerLevel == 5){playerLevel = highestSkill;};
		return playerLevel;
	}			
}
