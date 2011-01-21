package com.smartaleq.bukkit.dwarfcraft;


import java.io.*;
import java.util.*;



public class DwarfCraftSkillEffects {
	
	public static String[][] skillEffectsArray;
	/*
	 * file schema names for v1.0
	 */
	static String fileName = "./plugins/DwarfCraft/effectsdata.config";
	static int totalColumns = 36;
	static int skillLevelStartColumn = 1;
	static int elfLevelColumn = 32;
	static int effectTypeColumn = 33;
	static int effectItemsWatchedColumn = 34;
	static int effectItemsOutputColumn = 35;
		
	
	public static void readEffects(){
		String line = "";
		
	    System.out.println("Attempting to read skill effects file");
		
		skillEffectsArray = new String[1000][totalColumns];
		
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			int effectid = 0;
			int column = 0;
			for(int i = 0; i < 999; i++) {
				line = br.readLine();
				if(line != null){
					String[] theline = line.split(",");
					effectid = Integer.parseInt(theline[0]);
					column = 1;
					while(column<35){
						skillEffectsArray[effectid][column] = theline[column];
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

	public static double getEffectValue(int effectId,int effectLevel){
		String effectValue = skillEffectsArray[effectId][effectLevel+skillLevelStartColumn];
		double value = 0.0;
		if(effectValue != null){
			value = Double.parseDouble(skillEffectsArray[effectId][effectLevel+skillLevelStartColumn]); 
			System.out.println("Got value " + value);
		}
		return value;
	}
	public static int getElfLevel(int effectId){
		int elfLevel = Integer.parseInt(skillEffectsArray[effectId][elfLevelColumn]);
	    System.out.println("GotElfLevel = " + elfLevel + " for effectId " + effectId);
		return elfLevel;
		
	}
	
	
	/*
	 * Checks if an effect is triggered on using an item
	 * not sure if needed
	 */
//
//	public Boolean isEffectIdForItemUse(int effectId){
//		Boolean isItemUse = false;
//		if (skillEffectsArray[effectId][effectTypeColumn]=="itemuse"){isItemUse = true;};
//		return isItemUse;
//	}


	/*
	 * Returns all effects that trigger on specific effect type and specific item
	 */
	public static int[] getEffects(int itemId, String effectType){
		int effectCount=0;
		int effectId;
		int arraySlot = 1;		
		int[] outputArray;

		outputArray = new int[8];
		
		System.out.println(itemId);
		
		for(effectId = 0; effectId<1000; effectId++){
			String activeEffect = skillEffectsArray[effectId][effectTypeColumn];
			if(activeEffect != null){
				if(activeEffect.equalsIgnoreCase(effectType)){
							//checks if correct effect type
					if(Integer.parseInt(skillEffectsArray[effectId][effectItemsWatchedColumn])==itemId){ 
							// checks if correct itemId
						if(effectCount < 8){
							 System.out.println("found one");
						outputArray[arraySlot] = effectId;
						arraySlot++;
						effectCount++;
						}
					}
				}
			}	
		}
//	    System.out.println("Got Effects");
		outputArray[0] = effectCount; 
		return outputArray;
	}
	
	/*
	 * Returns the output item for effects that have an item output
	 */
	static int getEffectResult(int effectId){
		String outputNumber = skillEffectsArray[effectId][effectItemsOutputColumn-1];
		System.out.println(skillEffectsArray[effectId][effectItemsOutputColumn-2]);
		System.out.println(skillEffectsArray[effectId][effectItemsOutputColumn-1]);
		outputNumber = outputNumber.trim();
		int effectResultItem = Integer.parseInt(outputNumber);
	    System.out.println("GotEffectResult");
		return effectResultItem;
	}
	
	public static int getSkillForEffect(int effectId) {
		int skillId = (effectId - (effectId % 10))/10;
		return skillId;
	}
		

//	/*
//	 * not sure if this method is needed any more, keeping for old code
//	 */
//	public static boolean isBlockAffected(int blockMaterialId){
//		for(int effectId = 0 ; effectId < 1000 ; effectId++){
//			if(Integer.parseInt(skillEffectsArray[effectId][effectItemsWatchedColumn]) == blockMaterialId){
//				return true;
//			}
//		}
//		return false;
//	}

	public static int getRandomBlockCount(double blockChance){
		Random generator = new Random();
		int wholeBlocks = (int)Math.floor(blockChance);
		double partBlocks = blockChance % 1;
		double rand = generator.nextDouble();
		if(partBlocks >= rand){wholeBlocks++;};
		
	    System.out.println("gotrandomblock count, whole blocks =" + wholeBlocks + " and chance was " + partBlocks);
		
		return wholeBlocks;
	}
}
