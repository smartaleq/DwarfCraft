package com.smartaleq.bukkit.dwarfcraft;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

	
public class DwarfCraftSkills {
		
	public static String[][] skillsArray;
	/*
	 * skills file schema v1.0
	 * rows: skillId 0-99
	 * columns: skillId, skillName, skillSchool, effectCount, effectId0-7,training item count, (training itemIdX,training itemcountX)x{1-3},trainingNoviceIncrement,trainingMasterMultiplier 
	 */
	static String fileName = "./plugins/DwarfCraft/skillsdata.config";
	
	static int totalColumns = 11;
	static int skillNameColumn = 1;
	static int skillSchoolColumn = 2;
	static int skillTrainingItemCountColumn = 3;
	static int skillTrainingNoviceIncrementColumn = 10;
	static int skillTrainingMasterMultiplierColumn = 11;
	
	public static String skillName(int skillId){
		return skillsArray[skillId][skillNameColumn];
	}
	
	public static void readSkills(){
		String line = "";
		System.out.println("Attempting to read skills file");
		
		skillsArray = new String[100][totalColumns];

		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			int skillId = 0;
			int column = 0;

			for(int i = 0; i < 99; i++) {
				line = br.readLine();
				if(line != null){
					String[] theline = line.split(",");
					skillId = Integer.parseInt(theline[0]);
					column = 1;
					while(column < totalColumns){
						skillsArray[skillId][column] = theline[column];
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

	public static String getSkillName(int skillId){
		return skillsArray[skillId][skillNameColumn];
	}
	
	public static String getSkillSchool(int skillId){
		return skillsArray[skillId][skillSchoolColumn];
	}
	
	public static int[] getSkillTrainingCost(int skillId, int newSkillLevel, String playerName){
		int[] trainingCosts;
		trainingCosts = new int[7];
		trainingCosts[0] = Integer.parseInt(skillsArray[skillId][skillTrainingItemCountColumn]);
		
		// Calculate multiplier for this level
		double baseMultiplier;
		//multiplier for levels 0-5
		baseMultiplier = Math.ceil(Math.max((double)newSkillLevel, 5) * Double.parseDouble(skillsArray[skillId][skillTrainingNoviceIncrementColumn]));
		//multiplier for levels 6-30
		baseMultiplier = baseMultiplier * (Math.pow(Double.parseDouble(skillsArray[skillId][skillTrainingMasterMultiplierColumn]), Math.min(0,newSkillLevel-5)));
		//mutliplier for secondary skill training
		if(newSkillLevel > 5 && newSkillLevel < DwarfCraftSkillTraining.topQuartileThreshold(playerName)){
			baseMultiplier = baseMultiplier * (1 + (DwarfCraftSkillTraining.playerLevel(playerName)/100+2*DwarfCraftSkillTraining.playerLevel(playerName)));
		}
		
		for(int i=0 ; i<trainingCosts[0] ; i++){
			//insert training items into cells 1, 3, 5
			trainingCosts[1+(2*i)] = Integer.parseInt(skillsArray[skillId][skillTrainingItemCountColumn+(2*i)]);
			//insert training itemcounts into cells 2, 4, 6
			trainingCosts[2+(2*i)] = (int)Math.floor(baseMultiplier * Double.parseDouble(skillsArray[skillId][skillTrainingItemCountColumn+1+(2*i)]));
		}
		System.out.println("Got training costs");
		return trainingCosts;
}
	

	
	
	
	
	
	
	
	// 		Fuck Enums - doing this arraystyle like effects ...Done
	
//public enum DwarfCraftSkills {
//	PICKAXEUSE (1,"Pickaxe Use","Tool Use",1,10,0,0,0,0,0,0,0,1,4,16,0,0,0,0,1,1.3),
//	SHOVELUSE (2,"Shovel Use","Tool Use",1,20,0,0,0,0,0,0,0,1,3,32,0,0,0,0,0.5,1.3),
//	AXEDURABILITY (3,"Axe Durability","Tool Use",1,30,0,0,0,0,0,0,0,1,17,16,0,0,0,0,0.5,1.3),
//	PLOWMAN (4,"Plowman","Tool Use",2,40,41,0,0,0,0,0,0,2,295,1,296,1,0,0,1,1.4),
//	SWORDSMAN (5,"Swordsman","Tool Use",2,50,51,0,0,0,0,0,0,2,334,1,0,1,0,0,0.5,1.36),
//	SMELTER (8,"Smelter","Tool Use",1,80,0,0,0,0,0,0,0,1,327,1,0,0,0,0,1,1.22),
//	EXCAVATOR (11,"Excavator","Mining",1,110,0,0,0,0,0,0,0,1,4,16,0,0,0,0,1,1.3),
//	QUARRY_WORKER (12,"Quarry Worker","Mining",2,120,121,0,0,0,0,0,0,1,4,16,0,0,0,0,1,1.3),
//	ORE_MINER (13,"Ore Miner","Mining",3,130,131,132,0,0,0,0,0,2,263,2,265,2,0,0,1,1.32),
//	PRECIOUSSTONEMINER (14,"Precious Stone Miner","Mining",4,140,141,142,143,0,0,0,0,3,266,2,264,1,331,4,1,1.25),
//	SANDDIGGER (21,"Sand Digger","Digging",2,210,211,0,0,0,0,0,0,1,12,32,0,0,0,0,0.5,1.3),
//	GRAVELDIGGER (22,"Gravel Digger","Digging",2,220,221,0,0,0,0,0,0,1,13,16,0,0,0,0,1,1.28),
//	DIRTDIGGER (23,"Dirt Digger","Digging",2,230,231,0,0,0,0,0,0,1,3,32,0,0,0,0,1,1.3),
//	WOODCUTTING (31,"Woodcutting","Lumberjack",2,310,311,0,0,0,0,0,0,1,17,16,0,0,0,0,0.5,1.3),
//	WOODSPLITTING (32,"Wood Splitting","Lumberjack",2,320,321,0,0,0,0,0,0,1,17,16,0,0,0,0,0.5,1.3),
//	WHEATFARMER (41,"Wheat Farmer","Farming",2,410,411,0,0,0,0,0,0,2,296,1,295,1,0,0,1,1.4),
//	REEDCACTUSFARMER (42,"Reed/Cactus Farmer","Farming",1,420,0,0,0,0,0,0,0,2,338,1,81,1,0,0,1,1.4),
//	TILELAYER (51,"Tile Layer","Decorating",5,510,511,512,513,514,0,0,0,1,44,16,0,0,0,0,1,1.3),
//	GLASSWORKER (52,"Glassworker","Decorating",1,520,0,0,0,0,0,0,0,1,20,8,0,0,0,0,1,1.35),
//	WOODCRAFTING (53,"Woodcrafting","Decorating",3,530,531,532,0,0,0,0,0,2,5,4,280,4,0,0,1,1.4),
//	BOOKMAKER (61,"Bookmaker","Specialist",1,610,0,0,0,0,0,0,0,2,339,1,340,1,0,0,1,1.36),
//	BRICKMAKER (62,"Brickmaker","Specialist",1,620,0,0,0,0,0,0,0,2,336,1,45,1,0,0,0.5,1.4),
//	DEMOLITIONIST (63,"Demolitionist","Specialist",2,630,631,0,0,0,0,0,0,2,289,1,46,1,0,0,0.5,1.4),
//	FIRESTARTER (64,"Firestarter","Specialist",2,640,641,0,0,0,0,0,0,1,259,1,0,0,0,0,1,1.3),
//	RAILWORKER (65,"Railworker","Specialist",1,650,0,0,0,0,0,0,0,1,343,1,0,0,0,0,0.5,1.3),
//	TRAVELING (71,"Traveling","Exploration",4,710,711,712,713,0,0,0,0,1,337,4,0,0,0,0,1,1.3),
//	CLIMBING (72,"Climbing","Exploration",1,720,0,0,0,0,0,0,0,1,86,1,0,0,0,0,0.5,1.3),
//	SURVIVALIST (73,"Survivalist","Exploration",2,730,731,0,0,0,0,0,0,1,39,1,0,0,0,0,1,1.32),
//	SUSTAINABLEHARVESTING (74,"Sustainable Harvesting","Exploration",1,740,0,0,0,0,0,0,0,2,37,1,38,1,0,0,1,1.32),
//	ANIMALHUNTER (81,"Animal Hunter","Combat",2,810,811,0,0,0,0,0,0,3,319,1,334,1,36,2,1,1.36),
//	MONSTERHUNTER (82,"Monster Hunter","Combat",2,820,821,0,0,0,0,0,0,2,287,2,289,2,0,0,0.5,1.4),
//	LIGHTARMORSOLDIER (83,"Light Armor Soldier","Combat",2,830,831,0,0,0,0,0,0,1,299,1,0,0,0,0,0.5,1.3),
//	HEAVYARMORSCOUT (84,"Heavy Armor Scout","Combat",2,840,841,0,0,0,0,0,0,1,307,1,0,0,0,0,0.5,1.3),
//	FLETCHER (85,"Fletcher","Combat",3,850,851,852,0,0,0,0,0,2,288,5,318,1,0,0,1,1.3),
//	NOBLE (99,"Noble","Seekrit",1,990,0,0,0,0,0,0,0,1,344,16,0,0,0,0,1,1.3);
//	
//
//	int skillId;
//	String professionName; 
//	String school;
//	int bonusCount; 
//	int bonusId0; 
//	int bonusId1; 
//	int bonusId2; 
//	int bonusId3; 
//	int bonusId4; 
//	int bonusId5; 
//	int bonusId6; 
//	int bonusId7;
//	int trainingPaymentCount;
//	int trainingPaymentMat1Type;
//	int trainingPaymentMat1Stack;
//	int trainingPaymentMat2Type;
//	int trainingPaymentMat2Stack;
//	int trainingPaymentMat3Type;
//	int trainingPaymentMat3Stack;
//	double trainingNoviceIncrement;
//	double trainingMasterMultiplyer;
//
//
//	DwarfCraftSkills(
//			int skillId, 
//			String professionName, 
//			String school, 
//			int bonusCount, 
//			int bonusId0, 
//			int bonusId1, 
//			int bonusId2, 
//			int bonusId3, 
//			int bonusId4, 
//			int bonusId5, 
//			int bonusId6, 
//			int bonusId7,
//			int trainingPaymentCount,
//			int trainingPaymentMat1Type,
//			int trainingPaymentMat1Stack,
//			int trainingPaymentMat2Type,
//			int trainingPaymentMat2Stack,
//			int trainingPaymentMat3Type,
//			int trainingPaymentMat3Stack,
//			double trainingNoviceIncrement,
//			double trainingMasterMultiplyer)
//		{
//		this.skillId = skillId;
//		this.professionName = professionName; 
//		this.school = school; 
//		this.bonusCount = bonusCount;
//		this.bonusId0 = bonusId0;
//		this.bonusId1 = bonusId1;
//		this.bonusId2 = bonusId2;
//		this.bonusId3 = bonusId3;
//		this.bonusId4 = bonusId4;
//		this.bonusId5 = bonusId5;
//		this.bonusId6 = bonusId6;
//		this.bonusId7 = bonusId7;
//		this.trainingPaymentCount = trainingPaymentCount;
//		this.trainingPaymentMat1Type = trainingPaymentMat1Type;
//		this.trainingPaymentMat1Stack = trainingPaymentMat1Stack;
//		this.trainingPaymentMat2Type = trainingPaymentMat2Type;
//		this.trainingPaymentMat2Stack = trainingPaymentMat2Stack;
//		this.trainingPaymentMat3Type = trainingPaymentMat3Type;
//		this.trainingPaymentMat3Stack = trainingPaymentMat3Stack;
//		this.trainingNoviceIncrement = trainingNoviceIncrement;
//		this.trainingMasterMultiplyer = trainingMasterMultiplyer;
//		}
	


}
