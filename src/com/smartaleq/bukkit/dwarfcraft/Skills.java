package com.smartaleq.bukkit.dwarfcraft;
	
public enum Skills {
    PICKAXEUSE (1,"Pickaxe Use","Tool Use",1,new int[] {10,0,0,0,0,0,0,0},1,4,16,0,0,0,0,1,1.3),
	SHOVELUSE (2,"Shovel Use","Tool Use",1,new int[] {20,0,0,0,0,0,0,0},1,3,32,0,0,0,0,0.5,1.3),
	AXEDURABILITY (3,"Axe Durability","Tool Use",1,new int[] {30,0,0,0,0,0,0,0},1,17,16,0,0,0,0,0.5,1.3),
	PLOWMAN (4,"Plowman","Tool Use",2,new int[] {40,41,0,0,0,0,0,0},2,295,1,296,1,0,0,1,1.4),
	SWORDSMAN (5,"Swordsman","Tool Use",2,new int[] {50,51,0,0,0,0,0,0},2,334,1,0,1,0,0,0.5,1.36),
	SMELTER (8,"Smelter","Tool Use",1,new int[] {80,0,0,0,0,0,0,0},1,327,1,0,0,0,0,1,1.22),
	EXCAVATOR (11,"Excavator","Mining",1,new int[] {110,0,0,0,0,0,0,0},1,4,16,0,0,0,0,1,1.3),
	QUARRY_WORKER (12,"Quarry Worker","Mining",2,new int[] {120,121,0,0,0,0,0,0},1,4,16,0,0,0,0,1,1.3),
	ORE_MINER (13,"Ore Miner","Mining",3,new int[] {130,131,132,0,0,0,0,0},2,263,2,265,2,0,0,1,1.32),
	PRECIOUSSTONEMINER (14,"Precious Stone Miner","Mining",4,new int[] {140,141,142,143,0,0,0,0},3,266,2,264,1,331,4,1,1.25),
	SANDDIGGER (21,"Sand Digger","Digging",2,new int[] {210,211,0,0,0,0,0,0},1,12,32,0,0,0,0,0.5,1.3),
	GRAVELDIGGER (22,"Gravel Digger","Digging",2,new int[] {220,221,0,0,0,0,0,0},1,13,16,0,0,0,0,1,1.28),
	DIRTDIGGER (23,"Dirt Digger","Digging",2,new int[] {230,231,0,0,0,0,0,0},1,3,32,0,0,0,0,1,1.3),
	WOODCUTTING (31,"Woodcutting","Lumberjack",2,new int[] {310,311,0,0,0,0,0,0},1,17,16,0,0,0,0,0.5,1.3),
	WOODSPLITTING (32,"Wood Splitting","Lumberjack",2,new int[] {320,321,0,0,0,0,0,0},1,17,16,0,0,0,0,0.5,1.3),
	WHEATFARMER (41,"Wheat Farmer","Farming",2,new int[] {410,411,0,0,0,0,0,0},2,296,1,295,1,0,0,1,1.4),
	REEDCACTUSFARMER (42,"Reed/Cactus Farmer","Farming",1,new int[] {420,0,0,0,0,0,0,0},2,338,1,81,1,0,0,1,1.4),
	TILELAYER (51,"Tile Layer","Decorating",5,new int[] {510,511,512,513,514,0,0,0},1,44,16,0,0,0,0,1,1.3),
	GLASSWORKER (52,"Glassworker","Decorating",1,new int[] {520,0,0,0,0,0,0,0},1,20,8,0,0,0,0,1,1.35),
	WOODCRAFTING (53,"Woodcrafting","Decorating",3,new int[] {530,531,532,0,0,0,0,0},2,5,4,280,4,0,0,1,1.4),
	BOOKMAKER (61,"Bookmaker","Specialist",1,new int[] {610,0,0,0,0,0,0,0},2,339,1,340,1,0,0,1,1.36),
	BRICKMAKER (62,"Brickmaker","Specialist",1,new int[] {620,0,0,0,0,0,0,0},2,336,1,45,1,0,0,0.5,1.4),
	DEMOLITIONIST (63,"Demolitionist","Specialist",2,new int[] {630,631,0,0,0,0,0,0},2,289,1,46,1,0,0,0.5,1.4),
	FIRESTARTER (64,"Firestarter","Specialist",2,new int[] {640,641,0,0,0,0,0,0},1,259,1,0,0,0,0,1,1.3),
	RAILWORKER (65,"Railworker","Specialist",1,new int[] {650,0,0,0,0,0,0,0},1,343,1,0,0,0,0,0.5,1.3),
	TRAVELING (71,"Traveling","Exploration",4,new int[] {710,711,712,713,0,0,0,0},1,337,4,0,0,0,0,1,1.3),
	CLIMBING (72,"Climbing","Exploration",1,new int[] {720,0,0,0,0,0,0,0},1,86,1,0,0,0,0,0.5,1.3),
	SURVIVALIST (73,"Survivalist","Exploration",2,new int[] {730,731,0,0,0,0,0,0},1,39,1,0,0,0,0,1,1.32),
	SUSTAINABLEHARVESTING (74,"Sustainable Harvesting","Exploration",1,new int[] {740,0,0,0,0,0,0,0},2,37,1,38,1,0,0,1,1.32),
	ANIMALHUNTER (81,"Animal Hunter","Combat",2,new int[] {810,811,0,0,0,0,0,0},3,319,1,334,1,36,2,1,1.36),
	MONSTERHUNTER (82,"Monster Hunter","Combat",2,new int[] {820,821,0,0,0,0,0,0},2,287,2,289,2,0,0,0.5,1.4),
	LIGHTARMORSOLDIER (83,"Light Armor Soldier","Combat",2,new int[] {830,831,0,0,0,0,0,0},1,299,1,0,0,0,0,0.5,1.3),
	HEAVYARMORSCOUT (84,"Heavy Armor Scout","Combat",2,new int[] {840,841,0,0,0,0,0,0},1,307,1,0,0,0,0,0.5,1.3),
	FLETCHER (85,"Fletcher","Combat",3,new int[] {850,851,852,0,0,0,0,0},2,288,5,318,1,0,0,1,1.3),
	NOBLE (99,"Noble","Seekrit",1,new int[] {990,0,0,0,0,0,0,0},1,344,16,0,0,0,0,1,1.3);
	

	public int skillId;
	public final String professionName; 
	public final String school;
	public final int bonusCount; 
	public final int[] bonuses;
	public final int trainingPaymentCount;
	public final int trainingPaymentMat1Type;
	public final int trainingPaymentMat1Stack;
	public final int trainingPaymentMat2Type;
	public final int trainingPaymentMat2Stack;
	public final int trainingPaymentMat3Type;
	public final int trainingPaymentMat3Stack;
	public final double trainingNoviceIncrement;
	public final double trainingMasterMultiplier;
	

	Skills(
	       int skillId, 
	       String professionName, 
	       String school, 
	       int bonusCount, 
	       int[] bonuses,
	       int trainingPaymentCount,
	       int trainingPaymentMat1Type,
	       int trainingPaymentMat1Stack,
	       int trainingPaymentMat2Type,
	       int trainingPaymentMat2Stack,
	       int trainingPaymentMat3Type,
	       int trainingPaymentMat3Stack,
	       double trainingNoviceIncrement,
	       double trainingMasterMultiplier)
	    {
		this.skillId = skillId;
		this.professionName = professionName; 
		this.school = school; 
		this.bonusCount = bonusCount;
		this.bonuses = bonuses;
		this.trainingPaymentCount = trainingPaymentCount;
		this.trainingPaymentMat1Type = trainingPaymentMat1Type;
		this.trainingPaymentMat1Stack = trainingPaymentMat1Stack;
		this.trainingPaymentMat2Type = trainingPaymentMat2Type;
		this.trainingPaymentMat2Stack = trainingPaymentMat2Stack;
		this.trainingPaymentMat3Type = trainingPaymentMat3Type;
		this.trainingPaymentMat3Stack = trainingPaymentMat3Stack;
		this.trainingNoviceIncrement = trainingNoviceIncrement;
		this.trainingMasterMultiplier = trainingMasterMultiplier;
	    }

	public String skillName(int skillId){
	    return this.professionName;
	}
	

	// REDUNDANT, see skillName(..) above
	public String getSkillName(int skillId){
	    return this.professionName;
	}
	
	public String getSkillSchool(int skillId){
	    return this.school;
	}
	
	/* CHECK THIS ARRAYMATH, int[7], with first value as count? why?
	   what happened to the rest of the bonusIds? there are 8 per dude */
	public int[] getSkillTrainingCost(int skillId, int newSkillLevel, String playerName){
	    int[] trainingCosts;
	    trainingCosts = new int[7];
	    trainingCosts[0] = this.bonusCount;
	    
	    // Calculate multiplier for this level
	    double baseMultiplier;
	    //multiplier for levels 0-5
	    baseMultiplier = Math.ceil(Math.max((double)newSkillLevel, 5) * this.trainingNoviceIncrement);
	    //multiplier for levels 6-30
	    if(newSkillLevel > 5){
		baseMultiplier = baseMultiplier * (Math.pow(this.trainingMasterMultiplier, Math.min(0,newSkillLevel-5)));
		//mutliplier for secondary skill training
		if(newSkillLevel > 5 && newSkillLevel < DwarfCraftSkillTraining.topQuartileThreshold(playerName)){
		    baseMultiplier = baseMultiplier * (1 + (DwarfCraftSkillTraining.playerLevel(playerName)/100+2*DwarfCraftSkillTraining.playerLevel(playerName)));
		}
	    }
	    for(int i=0 ; i<trainingCosts[0] ; i++){
		//insert training items into cells 1, 3, 5
		trainingCosts[1+(2*i)] = this.bonuses[1+(2*i)];
		//insert training itemcounts into cells 2, 4, 6
		/* ALSO, this.bonuses DOESNT RETURN A DOUBLE, WHICH
		   WAS PARSED AS A DOUBLE BEFORE WITH Double.ParseDouble OR SOMETHING
		   SooooOSOOOOOoo WHATS The DelALELllA????? */
		trainingCosts[2+(2*i)] = (int)Math.floor(baseMultiplier * this.bonuses[2+(2*i)]);
	    }
	    return trainingCosts;
	}
}

