package com.smartaleq.bukkit.dwarfcraft;

import java.io.*;
import org.bukkit.entity.Player;
import org.bukkit.croemmich.searchids.Colors;

public class DwarfCraftPlayerSkills {

	static String playerSkillsFileName = "DwarfCraftPlayers.data";
	static String playerSkillsBackupName = "DwarfCraftPlayers.data.backup";
	static String playerSkillsDirectory = "./plugins/DwarfCraft/";
		
	static int maxPlayers = 100;
	static int[][] playerSkillsArray; 
	static String[] playerNamesArray;
	
	
	
	static int maximumSkillCount = 100;
		
	public static int getPlayerNumber(String playerName){
		int playerNumber = -1; //come back and change this
		for(int i=0; i < maxPlayers; i++){
			if(playerNamesArray[i] == null){
				continue;
				}
			if(playerNamesArray[i].length() == 0){
				continue;
				}
			if(playerNamesArray[i].equalsIgnoreCase(playerName)){playerNumber = i;};
		}
		return playerNumber;
	}
	
	public static int countPlayers(){
		int playerCount = 0;
		for(int i=0; i < maxPlayers; i++){
			if(playerNamesArray[i] == null){continue;}
			if(playerNamesArray[i].length() != 0){playerCount++;};
		}
		System.out.println("counted " + playerCount + " players");
		return playerCount;
	}
	
	static void addNewPlayer(String playerName, Player player){
		int newPlayerNumber = countPlayers()+1;
		playerNamesArray[newPlayerNumber] = playerName;
		player.sendMessage("Welcome to the server, " + Colors.Blue + playerName);
		backupSkills();
		saveSkills();		
		DwarfCraftSkillTraining.makeDwarf(playerName);
	}
	
	public static int getSkillLevel(int skillId, String playerName){
		int playerNumber = getPlayerNumber(playerName);
		int skillLevel = playerSkillsArray[playerNumber][skillId]; 
		if(DwarfCraftSkills.getSkillName(skillId)==null){return -1;}
		return skillLevel;
	}
	
	public static Boolean isPlayerElf(String playerName){
		int playerNumber = getPlayerNumber(playerName);
		Boolean isElf = false;
		int elfValue = playerSkillsArray[playerNumber][0];
		if(elfValue != 0){isElf = true;}
		return isElf;
	}
	
	public static void readPlayers(){
		String line = "";
		System.out.println("Attempting to read players file");
	    
		playerNamesArray= new String[maxPlayers];		
		/*
		 * schema: index of string playername is playernumber
		 */
		
		playerSkillsArray = new int[maxPlayers][maximumSkillCount];
		/*
		 * schema: playernumber, isElf, player skillvalue by skillID
		 */		
	
	    System.out.println("init players arrays");
		
		try {
			FileReader fr = new FileReader(playerSkillsDirectory + playerSkillsFileName);
			BufferedReader br = new BufferedReader(fr);
			for(int row = 0; row < maxPlayers; row++) {
				int column;
				line = br.readLine();
				if(line != null){
					String[] theline = line.split(",");
					playerNamesArray[row] = theline[0];
					column = 0;
						while(column < maximumSkillCount-1){
						playerSkillsArray[row][column] = Integer.parseInt(theline[column+1]);
						column++;
					}
				}
			}
		}
		catch(FileNotFoundException fN) {
			fN.printStackTrace();
		}
		catch(IOException e){
			System.out.println(e);
		}
	}
	public static void backupSkills(){
		File current = new File(playerSkillsDirectory + playerSkillsFileName);
		File backup = new File(playerSkillsDirectory + playerSkillsBackupName);
		current.renameTo(backup);
		current.delete();
	}
	
	public static void saveSkills() {
		File file = new File(playerSkillsDirectory + playerSkillsFileName);
		Writer writer = null;
		for(int i=0;i<maxPlayers;i++){
			if(playerNamesArray[i] == null){
				playerNamesArray[i]="";}
			}
	
		try{
			writer = new BufferedWriter(new FileWriter(file));
			for(int row=0 ; row < maxPlayers-1 ; row++){
				int column=0;
				writer.write(playerNamesArray[row]);
				while (column < maximumSkillCount-1){
					writer.write(","+playerSkillsArray[row][column]);
					column++;
				}
				writer.write(",\n");
				
			}
			writer.close();
		}	
		catch(FileNotFoundException fN) {
			fN.printStackTrace();
		}
		catch(IOException e){
			System.out.println(e);
		}
	}

	public static void skillSheet(String playerName, Player player) {
		String[] skillNames;
		skillNames = new String[3];
		String skillName;
		int[] skillLevels;
		skillLevels = new int[3];
	
		player.sendMessage("Printing Skill Sheet for " + playerName + "  Player Level is " + DwarfCraftSkillTraining.playerLevel(playerName));
		int skillId=0;
		int printLineSkillCount=0;
		if(DwarfCraftPlayerSkills.isPlayerElf(playerName)){
			player.sendMessage("Elves don't have skills, numbskull");
			return;
		}
		while (skillId<maximumSkillCount){
			if (printLineSkillCount == 3) skillId++;
			printLineSkillCount = 0;
			skillNames[0] = "";
			skillNames[1] = "";
			skillNames[2] = "";
			while (printLineSkillCount < 3){
				if (skillId == 100) break;
				skillName = DwarfCraftSkills.skillName(skillId);
				if (skillName != null){
					skillNames[printLineSkillCount] = skillName;
					skillLevels[printLineSkillCount] = DwarfCraftPlayerSkills.getSkillLevel(skillId, playerName);
					printLineSkillCount++;
				}
				skillId++;
			}
			if (printLineSkillCount == 3) {
				player.sendMessage("  "+skillNames[0]+": "+skillLevels[0]+"  "+skillNames[1]+": "+skillLevels[1]+"  "+skillNames[2]+": "+skillLevels[2]);
			}
		}
//		if (printLineSkillCount==2){
//			player.sendMessage("  "+skillNames[0]+": "+skillLevels[0]+"  "+skillNames[1]+": "+skillLevels[1]+"  "+skillNames[2]+": "+skillLevels[2]);
//		}
		if (printLineSkillCount==2){
			player.sendMessage("  "+skillNames[0]+": "+skillLevels[0]+"  "+skillNames[1]+": "+skillLevels[1]);
		}
		if (printLineSkillCount==1){
			player.sendMessage("  "+skillNames[0]+": "+skillLevels[0]);
		}		
	}	
	
}
