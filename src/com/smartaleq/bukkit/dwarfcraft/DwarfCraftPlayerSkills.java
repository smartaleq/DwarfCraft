package com.smartaleq.bukkit.dwarfcraft;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;


public class DwarfCraftPlayerSkills {

	static String playerSkillsFileName = "DwarfCraftPlayers.data";
	static String playerSkillsBackupName = "DwarfCraftPlayers.data.backup";
	static String playerSkillsDirectory = "./plugins/DwarfCraft/";
		
	static int maxPlayers = 100;
	static int[][] playerSkillsArray; 
	static String[] playerNamesArray;
	
	
	
	static int totalColumns = 100;
		
	public static int getPlayerNumber(String playerName){
		int playerNumber = 0; //come back and change this
		for(int i=0; i < maxPlayers; i++){
			if(playerNamesArray[i] == playerName){playerNumber = i;};
		}
		if (playerNumber == 0){// throw noSuchPlayer;
		}
		return playerNumber;
	}
	
	public static int countPlayers(){
		int playerCount = 0;
		for(int i=0; i < maxPlayers; i++){
			if(playerNamesArray[i] != null){playerCount++;};
		}
		return playerCount;
	}
	
	static void addNewPlayer(String playerName){
		int newPlayerNumber = countPlayers()+1;
		playerNamesArray[newPlayerNumber] = playerName;
		DwarfCraftSkillTraining.makeDwarf(playerName);
		backupSkills();
		saveSkills();
	}
	
	public static int getSkillLevel(int SkillId, String playerName){
		int playerNumber = getPlayerNumber(playerName);
		int skillLevel = playerSkillsArray[playerNumber][SkillId]; 
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
		 * schema index of string playername is playernumber
		 */
		
		playerSkillsArray = new int[maxPlayers][totalColumns];
		/*
		 * schema: playernumber, isElf, player skillvalue by skillID
		 */		
	
	    System.out.println("init players arrays");
		
		try {
			FileReader fr = new FileReader(playerSkillsDirectory + playerSkillsFileName);
			BufferedReader br = new BufferedReader(fr);
			for(int row = 0; row <= maxPlayers; row++) {
				int column;
				line = br.readLine();
				if(line != null){
					String[] theline = line.split(",");
					playerNamesArray[row] = theline[0];
					column = 0;
						while(column < totalColumns-1){
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
			for(int row=0 ; row < maxPlayers ; row++){
				int column=0;
				writer.write(playerNamesArray[row]);
				while (column < totalColumns-1){
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
		
	
}
