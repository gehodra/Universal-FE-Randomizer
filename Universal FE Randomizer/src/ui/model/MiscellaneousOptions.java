package ui.model;

import java.util.Set;

public class MiscellaneousOptions {
	
	public enum RewardMode {
		SIMILAR, RANDOM
	}
	
	public final Boolean applyEnglishPatch;
	
	public final Boolean tripleEffectiveness;
	
	public final Boolean randomizeRewards;
	public final Integer enemyDropChance;
	
	public final RewardMode rewardMode;

	public final Boolean readDataFile;
	public final Set<String> dataFileSet;
	
	// FE7, FE8
	public MiscellaneousOptions(Boolean randomRewards, int enemyDropChance, Boolean tripleEffectiveness) {
		super();
		this.applyEnglishPatch = false;
		this.tripleEffectiveness = tripleEffectiveness;
		rewardMode = RewardMode.RANDOM;
		this.randomizeRewards = randomRewards;
		this.enemyDropChance = enemyDropChance;
		readDataFile = false;
		this.dataFileSet = null;
	}

	// FE4, FE6
	public MiscellaneousOptions(Boolean applyEnglishPatch, Boolean randomRewards, Boolean tripleEffectiveness) {
		super();
		this.applyEnglishPatch = applyEnglishPatch;
		this.tripleEffectiveness = tripleEffectiveness;
		rewardMode = RewardMode.RANDOM;
		this.randomizeRewards = randomRewards;
		enemyDropChance = 0;
		readDataFile = false;
		this.dataFileSet = null;
	}
	
	// FE9
	public MiscellaneousOptions(Boolean applyEnglishPatch, Boolean tripleEffectiveness, Boolean randomRewards,
			RewardMode rewardMode, Integer enemyDropChance, Boolean readDataFile, Set<String> dataFileSet) {
		super();
		this.applyEnglishPatch = applyEnglishPatch;
		this.tripleEffectiveness = tripleEffectiveness;
		this.rewardMode = rewardMode;
		this.randomizeRewards = randomRewards;
		this.enemyDropChance = enemyDropChance;
		this.readDataFile = readDataFile;
		this.dataFileSet = dataFileSet;
	}
}