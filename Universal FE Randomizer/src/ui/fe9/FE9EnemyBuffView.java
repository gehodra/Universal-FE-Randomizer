package ui.fe9;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;

import ui.model.FE9EnemyBuffOptions;
import ui.model.FE9EnemyBuffOptions.BossStatMode;
import ui.model.FE9EnemyBuffOptions.MinionGrowthMode;

public class FE9EnemyBuffView extends Composite {

	private Group container;
	
	private Group minionContainer;
	
	private Button buffMinionGrowthsButton;
	private Label minionGrowthLabel;
	private Spinner minionGrowthSpinner;
	private Button minionModeFlatButton;
	private Button minionModeScalingButton;
	
	private Button improveMinionWeaponButton;
	private Label minionWeaponLabel;
	private Spinner minionWeaponSpinner;
	
	private Button minionSkillButton;
	private Label minionSkillLabel;
	private Spinner minionSkillSpinner;
	
	private Group bossContainer;
	
	private Button buffBossStatButton;
	private Label bossStatLabel;
	private Spinner bossStatSpinner;
	private Button bossModeLinearButton;
	private Button bossModeEaseInOutButton;
	
	private Button improveBossWeaponButton;
	private Label bossWeaponLabel;
	private Spinner bossWeaponSpinner;
	
	private Button bossSkillButton;
	private Label bossSkillLabel;
	private Spinner bossSkillSpinner;
	
	public FE9EnemyBuffView(Composite parent, int style) {
		super(parent, style);
		
		FillLayout layout = new FillLayout();
		setLayout(layout);
		
		container = new Group(this, SWT.NONE);
		container.setText("Buff Enemies");
		container.setToolTipText("Options to mix up enemy units, generally to make the game more challenging.");
		
		FormLayout mainLayout = new FormLayout();
		mainLayout.marginLeft = 5;
		mainLayout.marginRight = 5;
		mainLayout.marginTop = 5;
		mainLayout.marginBottom = 5;
		container.setLayout(mainLayout);
		
		Group minionGroup = new Group(container, SWT.NONE);
		minionGroup.setText("Minions");
		
		FormLayout minionLayout = new FormLayout();
		minionLayout.marginLeft = 5;
		minionLayout.marginRight = 5;
		minionLayout.marginTop = 5;
		minionLayout.marginBottom = 5;
		minionGroup.setLayout(minionLayout);
		
		FormData minionData = new FormData();
		minionData.left = new FormAttachment(0, 0);
		minionData.right = new FormAttachment(100, 0);
		minionGroup.setLayoutData(minionData);
		
		buffMinionGrowthsButton = new Button(minionGroup, SWT.CHECK);
		buffMinionGrowthsButton.setText("Buff Minion Growths");
		buffMinionGrowthsButton.setToolTipText("Increases enemy growth rates.");
		buffMinionGrowthsButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean isEnabled = buffMinionGrowthsButton.getSelection();
				minionGrowthSpinner.setEnabled(isEnabled);
				minionGrowthLabel.setEnabled(isEnabled);
				minionModeFlatButton.setEnabled(isEnabled);
				minionModeScalingButton.setEnabled(isEnabled);
			}
		});
		
		FormData minionBuffData = new FormData();
		minionBuffData.left = new FormAttachment(0, 5);
		minionBuffData.top = new FormAttachment(0, 5);
		buffMinionGrowthsButton.setLayoutData(minionBuffData);
		
		Composite minionBuffParamContainer = new Composite(minionGroup, SWT.NONE);
		
		FormLayout buffParamLayout = new FormLayout();
		buffParamLayout.marginLeft = 5;
		buffParamLayout.marginRight = 5;
		buffParamLayout.marginTop = 5;
		buffParamLayout.marginBottom = 5;
		minionBuffParamContainer.setLayout(buffParamLayout);
		
		minionGrowthLabel = new Label(minionBuffParamContainer, SWT.NONE);
		minionGrowthLabel.setText("Buff Amount:");
		minionGrowthLabel.setEnabled(false);
		
		minionGrowthSpinner = new Spinner(minionBuffParamContainer, SWT.NONE);
		minionGrowthSpinner.setValues(10, 1, 100, 0, 1, 5);
		minionGrowthSpinner.setEnabled(false);
		
		FormData spinnerData = new FormData();
		spinnerData.right = new FormAttachment(100, -5);
		minionGrowthSpinner.setLayoutData(spinnerData);
		
		FormData labelData = new FormData();
		labelData.right = new FormAttachment(minionGrowthSpinner, -5);
		labelData.top = new FormAttachment(minionGrowthSpinner, 0, SWT.CENTER);
		minionGrowthLabel.setLayoutData(labelData);
		
		Composite minionModeContainer = new Composite(minionBuffParamContainer, SWT.NONE);
		minionModeContainer.setLayout(new FillLayout());
		
		minionModeFlatButton = new Button(minionModeContainer, SWT.RADIO);
		minionModeFlatButton.setText("Flat Buff");
		minionModeFlatButton.setToolTipText("The buff amount is directly added to the enemy's growth rates for all stats.");
		minionModeFlatButton.setSelection(true);
		minionModeFlatButton.setEnabled(false);
		
		minionModeScalingButton = new Button(minionModeContainer, SWT.RADIO);
		minionModeScalingButton.setText("Scaling Buff");
		minionModeScalingButton.setToolTipText("The buff amount is multiplied as a percentage to the enemy's growth rates for all stats.");
		minionModeScalingButton.setEnabled(false);
		
		FormData minionModeContainerData = new FormData();
		minionModeContainerData.top = new FormAttachment(minionGrowthSpinner, 5);
		minionModeContainerData.left = new FormAttachment(0, 0);
		minionModeContainerData.right = new FormAttachment(100, 0);
		minionModeContainer.setLayoutData(minionModeContainerData);
		
		FormData minionGrowthBuffContainerData = new FormData();
		minionGrowthBuffContainerData.top = new FormAttachment(buffMinionGrowthsButton, 0);
		minionGrowthBuffContainerData.left = new FormAttachment(buffMinionGrowthsButton, 0, SWT.LEFT);
		minionGrowthBuffContainerData.right = new FormAttachment(100, -5);
		minionBuffParamContainer.setLayoutData(minionGrowthBuffContainerData);
		
		//////////////////////////////////////////////////////////////////
		
		improveMinionWeaponButton = new Button(minionGroup, SWT.CHECK);
		improveMinionWeaponButton.setText("Improve Minion Weapons");
		improveMinionWeaponButton.setToolTipText("Adds a chance for enemies to spawn with a higher tier weapon than normal.");
		improveMinionWeaponButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				minionWeaponSpinner.setEnabled(improveMinionWeaponButton.getSelection());
				minionWeaponLabel.setEnabled(improveMinionWeaponButton.getSelection());
			}
		});
		
		FormData minionWeaponData = new FormData();
		minionWeaponData.left = new FormAttachment(0, 5);
		minionWeaponData.top = new FormAttachment(minionBuffParamContainer, 5);
		improveMinionWeaponButton.setLayoutData(minionWeaponData);
		
		minionWeaponLabel = new Label(minionGroup, SWT.NONE);
		minionWeaponLabel.setText("Chance:");
		minionWeaponLabel.setEnabled(false);
		
		minionWeaponSpinner = new Spinner(minionGroup, SWT.NONE);
		minionWeaponSpinner.setValues(25, 1, 100, 0, 1, 5);
		minionWeaponSpinner.setEnabled(false);
		
		FormData minionWeaponLabelData = new FormData();
		minionWeaponLabelData.right = new FormAttachment(minionWeaponSpinner, -5);
		minionWeaponLabelData.top = new FormAttachment(minionWeaponSpinner, 0, SWT.CENTER);
		minionWeaponLabel.setLayoutData(minionWeaponLabelData);
		
		FormData minionWeaponSpinnerData = new FormData();
		minionWeaponSpinnerData.right = new FormAttachment(100, -10);
		minionWeaponSpinnerData.top = new FormAttachment(improveMinionWeaponButton, 5);
		minionWeaponSpinner.setLayoutData(minionWeaponSpinnerData);
		
		//////////////////////////////////////////////////////////////////
		
		minionSkillButton = new Button(minionGroup, SWT.CHECK);
		minionSkillButton.setText("Give Minions Skills");
		minionSkillButton.setToolTipText("Adds a chance for enemies to spawn with a skill.");
		minionSkillButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				minionSkillLabel.setEnabled(minionSkillButton.getSelection());
				minionSkillSpinner.setEnabled(minionSkillButton.getSelection());
			}
		});
		
		FormData minionSkillData = new FormData();
		minionSkillData.left = new FormAttachment(0, 5);
		minionSkillData.top = new FormAttachment(minionWeaponSpinner, 5);
		minionSkillButton.setLayoutData(minionSkillData);
		
		minionSkillLabel = new Label(minionGroup, SWT.NONE);
		minionSkillLabel.setText("Chance: ");
		minionSkillLabel.setEnabled(false);
		
		minionSkillSpinner = new Spinner(minionGroup, SWT.NONE);
		minionSkillSpinner.setValues(25, 1, 100, 0, 1, 5);
		minionSkillSpinner.setEnabled(false);
		
		FormData minionSkillLabelData = new FormData();
		minionSkillLabelData.right = new FormAttachment(minionSkillSpinner, -5);
		minionSkillLabelData.top = new FormAttachment(minionSkillSpinner, 0, SWT.CENTER);
		minionSkillLabel.setLayoutData(minionSkillLabelData);
		
		FormData minionSkillSpinnerData = new FormData();
		minionSkillSpinnerData.right = new FormAttachment(100, -10);
		minionSkillSpinnerData.top = new FormAttachment(minionSkillButton, 5);
		minionSkillSpinner.setLayoutData(minionSkillSpinnerData);
		
		//////////////////////////////////////////////////////////////////
		
		Group bossGroup = new Group(container, SWT.NONE);
		bossGroup.setText("Bosses");
		
		FormLayout bossLayout = new FormLayout();
		bossLayout.marginLeft = 5;
		bossLayout.marginRight = 5;
		bossLayout.marginTop = 5;
		bossLayout.marginBottom = 5;
		bossGroup.setLayout(bossLayout);
		
		FormData bossData = new FormData();
		bossData.left = new FormAttachment(minionGroup, 0, SWT.LEFT);
		bossData.right = new FormAttachment(minionGroup, 0, SWT.RIGHT);
		bossData.top = new FormAttachment(minionGroup, 5);
		bossGroup.setLayoutData(bossData);
		
		buffBossStatButton = new Button(bossGroup, SWT.CHECK);
		buffBossStatButton.setText("Buff Boss Stats");
		buffBossStatButton.setToolTipText("Increases the base stats for of boss characters.");
		buffBossStatButton.setEnabled(true);
		buffBossStatButton.setSelection(false);
		buffBossStatButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean isEnabled = buffBossStatButton.getSelection();
				bossStatLabel.setEnabled(isEnabled);
				bossStatSpinner.setEnabled(isEnabled);
				bossModeLinearButton.setEnabled(isEnabled);
				bossModeEaseInOutButton.setEnabled(isEnabled);
			}
		});
		
		FormData bossBuffData = new FormData();
		bossBuffData.left = new FormAttachment(0, 5);
		bossBuffData.top = new FormAttachment(0, 5);
		buffBossStatButton.setLayoutData(bossBuffData);
		
		Composite bossBuffParamContainer = new Composite(bossGroup, SWT.NONE);
		
		buffParamLayout = new FormLayout();
		buffParamLayout.marginLeft = 5;
		buffParamLayout.marginRight = 5;
		buffParamLayout.marginTop = 5;
		buffParamLayout.marginBottom = 5;
		bossBuffParamContainer.setLayout(buffParamLayout);
		
		bossStatLabel = new Label(bossBuffParamContainer, SWT.NONE);
		bossStatLabel.setText("Maximum Boost:");
		bossStatLabel.setEnabled(false);
		
		bossStatSpinner = new Spinner(bossBuffParamContainer, SWT.NONE);
		bossStatSpinner.setValues(5, 1, 20, 0, 1, 5);
		bossStatSpinner.setEnabled(false);
		
		spinnerData = new FormData();
		spinnerData.right = new FormAttachment(100, -5);
		bossStatSpinner.setLayoutData(spinnerData);
		
		labelData = new FormData();
		labelData.right = new FormAttachment(bossStatSpinner, -5);
		labelData.top = new FormAttachment(bossStatSpinner, 0, SWT.CENTER);
		bossStatLabel.setLayoutData(labelData);
		
		Composite bossModeContainer = new Composite(bossBuffParamContainer, SWT.NONE);
		bossModeContainer.setLayout(new FillLayout());
		
		bossModeLinearButton = new Button(bossModeContainer, SWT.RADIO);
		bossModeLinearButton.setText("Scale Linearly");
		bossModeLinearButton.setToolTipText("Bosses gradually gain stats in a linear fashion up to the max gain.");
		bossModeLinearButton.setSelection(true);
		bossModeLinearButton.setEnabled(false);
		
		bossModeEaseInOutButton = new Button(bossModeContainer, SWT.RADIO);
		bossModeEaseInOutButton.setText("Ease In/Ease Out");
		bossModeEaseInOutButton.setToolTipText("Ramps up more slowly and eases into the max gain.");
		bossModeEaseInOutButton.setEnabled(false);
		
		FormData bossModeContainerData = new FormData();
		bossModeContainerData.top = new FormAttachment(bossStatSpinner, 5);
		bossModeContainerData.left = new FormAttachment(0, 0);
		bossModeContainerData.right = new FormAttachment(100, 0);
		bossModeContainer.setLayoutData(bossModeContainerData);
		
		FormData bossStatBuffContainerData = new FormData();
		bossStatBuffContainerData.top = new FormAttachment(buffBossStatButton, 0);
		bossStatBuffContainerData.left = new FormAttachment(buffBossStatButton, 0, SWT.LEFT);
		bossStatBuffContainerData.right = new FormAttachment(100, -5);
		bossBuffParamContainer.setLayoutData(bossStatBuffContainerData);
		
		//////////////////////////////////////////////////////////////////
		
		improveBossWeaponButton = new Button(bossGroup, SWT.CHECK);
		improveBossWeaponButton.setText("Improve Boss Weapons");
		improveBossWeaponButton.setToolTipText("Adds a chance for bosses to carry a higher tier weapon than normal.");
		improveBossWeaponButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				bossWeaponSpinner.setEnabled(improveBossWeaponButton.getSelection());
				bossWeaponLabel.setEnabled(improveBossWeaponButton.getSelection());
			}
		});
		
		FormData bossWeaponData = new FormData();
		bossWeaponData.left = new FormAttachment(0, 5);
		bossWeaponData.top = new FormAttachment(bossBuffParamContainer, 5);
		improveBossWeaponButton.setLayoutData(bossWeaponData);
		
		bossWeaponLabel = new Label(bossGroup, SWT.NONE);
		bossWeaponLabel.setText("Chance:");
		bossWeaponLabel.setEnabled(false);
		
		bossWeaponSpinner = new Spinner(bossGroup, SWT.NONE);
		bossWeaponSpinner.setValues(25, 1, 100, 0, 1, 5);
		bossWeaponSpinner.setEnabled(false);
		
		FormData bossWeaponLabelData = new FormData();
		bossWeaponLabelData.right = new FormAttachment(bossWeaponSpinner, -5);
		bossWeaponLabelData.top = new FormAttachment(bossWeaponSpinner, 0, SWT.CENTER);
		bossWeaponLabel.setLayoutData(bossWeaponLabelData);
		
		FormData bossWeaponSpinnerData = new FormData();
		bossWeaponSpinnerData.right = new FormAttachment(100, -10);
		bossWeaponSpinnerData.top = new FormAttachment(improveBossWeaponButton, 5);
		bossWeaponSpinner.setLayoutData(bossWeaponSpinnerData);
		
		//////////////////////////////////////////////////////////////////
		
		bossSkillButton = new Button(bossGroup, SWT.CHECK);
		bossSkillButton.setText("Give Bosses Skills");
		bossSkillButton.setToolTipText("Adds a chance for bosses to come in with a skill.\nBosses that already have skills have a chance to get second skill.");
		bossSkillButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				bossSkillLabel.setEnabled(bossSkillButton.getSelection());
				bossSkillSpinner.setEnabled(bossSkillButton.getSelection());
			}
		});
		
		FormData bossSkillData = new FormData();
		bossSkillData.left = new FormAttachment(0, 5);
		bossSkillData.top = new FormAttachment(bossWeaponSpinner, 5);
		bossSkillButton.setLayoutData(bossSkillData);
		
		bossSkillLabel = new Label(bossGroup, SWT.NONE);
		bossSkillLabel.setText("Chance: ");
		bossSkillLabel.setEnabled(false);
		
		bossSkillSpinner = new Spinner(bossGroup, SWT.NONE);
		bossSkillSpinner.setValues(25, 1, 100, 0, 1, 5);
		bossSkillSpinner.setEnabled(false);
		
		FormData bossSkillLabelData = new FormData();
		bossSkillLabelData.right = new FormAttachment(bossSkillSpinner, -5);
		bossSkillLabelData.top = new FormAttachment(bossSkillSpinner, 0, SWT.CENTER);
		bossSkillLabel.setLayoutData(bossSkillLabelData);
		
		FormData bossSkillSpinnerData = new FormData();
		bossSkillSpinnerData.right = new FormAttachment(100, -10);
		bossSkillSpinnerData.top = new FormAttachment(bossSkillButton, 5);
		bossSkillSpinner.setLayoutData(bossSkillSpinnerData);
		
		//////////////////////////////////////////////////////////////////
	}
	
	public FE9EnemyBuffOptions getEnemyBuffOptions() {
		int minionAmount = minionGrowthSpinner.getSelection();
		MinionGrowthMode minionMode = MinionGrowthMode.NONE;
		if (buffMinionGrowthsButton.getSelection()) {
			if (minionModeFlatButton.getSelection()) { minionMode = MinionGrowthMode.FLAT; }
			if (minionModeScalingButton.getSelection()) { minionMode = MinionGrowthMode.SCALING; }
		}
		boolean minionWeapons = improveMinionWeaponButton.getSelection();
		int minionWeaponChance = minionWeaponSpinner.getSelection();
		boolean minionSkills = minionSkillButton.getSelection();
		int minionSkillChance = minionSkillSpinner.getSelection();
		
		int bossAmount = bossStatSpinner.getSelection();
		BossStatMode bossMode = BossStatMode.NONE;
		if (buffBossStatButton.getSelection()) {
			if (bossModeLinearButton.getSelection()) { bossMode = BossStatMode.LINEAR; }
			if (bossModeEaseInOutButton.getSelection()) { bossMode = BossStatMode.EASE_IN_OUT; }
		}
		boolean bossWeapons = improveBossWeaponButton.getSelection();
		int bossWeaponChance = bossWeaponSpinner.getSelection();
		boolean bossSkills = bossSkillButton.getSelection();
		int bossSkillChance = bossSkillSpinner.getSelection();
		
		return new FE9EnemyBuffOptions(minionMode, minionAmount, minionWeapons, minionWeaponChance, minionSkills, minionSkillChance, 
				bossMode, bossAmount, bossWeapons, bossWeaponChance, bossSkills, bossSkillChance);
	}
	
	public void setEnemyBuffOptions(FE9EnemyBuffOptions options) {
		if (options == null) {
			// Shouldn't happen.
		} else {
			if (options.minionMode != null) {
				switch (options.minionMode) {
				case NONE:
					buffMinionGrowthsButton.setSelection(false);
					minionModeFlatButton.setEnabled(false);
					minionModeScalingButton.setEnabled(false);
					minionGrowthSpinner.setEnabled(false);
					minionGrowthLabel.setEnabled(false);
					break;
				case FLAT:
					buffMinionGrowthsButton.setSelection(true);
					minionModeFlatButton.setEnabled(true);
					minionModeScalingButton.setEnabled(true);
					minionGrowthSpinner.setEnabled(true);
					minionGrowthLabel.setEnabled(true);
					
					minionModeFlatButton.setSelection(true);
					minionModeScalingButton.setSelection(false);
					
					minionGrowthSpinner.setSelection(options.minionBuff);
					break;
				case SCALING:
					buffMinionGrowthsButton.setSelection(true);
					minionModeFlatButton.setEnabled(true);
					minionModeScalingButton.setEnabled(true);
					minionGrowthSpinner.setEnabled(true);
					minionGrowthLabel.setEnabled(true);
					
					minionModeFlatButton.setSelection(false);
					minionModeScalingButton.setSelection(true);
					
					minionGrowthSpinner.setSelection(options.minionBuff);
					break;
				}
			}
			if (options.improveMinionWeapons) {
				improveMinionWeaponButton.setSelection(true);
				minionWeaponSpinner.setEnabled(true);
				minionWeaponLabel.setEnabled(true);
				minionWeaponSpinner.setSelection(options.minionImprovementChance);
			}
			if (options.giveMinionsSkills) {
				minionSkillButton.setSelection(true);
				minionSkillSpinner.setEnabled(true);
				minionSkillLabel.setEnabled(true);
				minionSkillSpinner.setSelection(options.minionSkillChance);
			}
			
			if (options.bossMode != null) {
				switch (options.bossMode) {
				case NONE:
					buffBossStatButton.setSelection(false);
					bossModeLinearButton.setEnabled(false);
					bossModeEaseInOutButton.setEnabled(false);
					bossStatSpinner.setEnabled(false);
					bossStatLabel.setEnabled(false);
					break;
				case LINEAR:
					buffBossStatButton.setSelection(true);
					bossModeLinearButton.setEnabled(true);
					bossModeEaseInOutButton.setEnabled(true);
					bossStatSpinner.setEnabled(true);
					bossStatLabel.setEnabled(true);
					
					bossModeLinearButton.setSelection(true);
					bossModeEaseInOutButton.setSelection(false);
					
					bossStatSpinner.setSelection(options.bossBuff);
					break;
				case EASE_IN_OUT:
					buffBossStatButton.setSelection(true);
					bossModeLinearButton.setEnabled(true);
					bossModeEaseInOutButton.setEnabled(true);
					bossStatSpinner.setEnabled(true);
					bossStatLabel.setEnabled(true);
					
					bossModeLinearButton.setSelection(false);
					bossModeEaseInOutButton.setSelection(true);
					
					bossStatSpinner.setSelection(options.bossBuff);
					break;
				}
			}
			if (options.improveBossWeapons) {
				improveBossWeaponButton.setSelection(true);
				bossWeaponSpinner.setEnabled(true);
				bossWeaponLabel.setEnabled(true);
				bossWeaponSpinner.setSelection(options.bossImprovementChance);
			}
			if (options.giveBossSkills) {
				bossSkillButton.setSelection(true);
				bossSkillLabel.setEnabled(true);
				bossSkillSpinner.setEnabled(true);
				bossSkillSpinner.setSelection(options.bossSkillChance);
			}
		}
	}
}
