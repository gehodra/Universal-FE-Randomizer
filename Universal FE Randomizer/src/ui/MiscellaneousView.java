package ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import fedata.general.FEBase.GameType;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import ui.general.MultiFileFlowDelegate;
import ui.general.OpenMultiFileFlow;
import ui.model.MiscellaneousOptions;
import ui.model.MiscellaneousOptions.RewardMode;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MiscellaneousView extends Composite implements MultiFileFlowDelegate {

    private Group container;
    private Shell shell;

    GameType type;

    private Button applyEnglishPatch; // pre-FE6 only
    private Button tripleEffectiveness; // FE7 only

    private Button randomizeChestVillageRewards;

    private MiscellaneousOptions.RewardMode rewardMode;

    private Composite rewardModeContainer;
    private Button similarRewardsButton;
    private Button randomRewardsButton;

    private Button enemyDropsButton;
    private Label enemyDropChanceLabel;
    private Spinner enemyDropChanceSpinner;

    private Button readDataFileButton;
    private Button selectDataSetButton;
    private Table dataFileSetTable;

    public MiscellaneousView(Composite parent, int style, GameType gameType, Shell shell) {
        super(parent, style);

        type = gameType;
        this.shell = shell;

        FillLayout layout = new FillLayout();
        setLayout(layout);

        container = new Group(this, SWT.NONE);

        container.setText("Miscellaneous");

        FormLayout mainLayout = new FormLayout();
        mainLayout.marginLeft = 5;
        mainLayout.marginTop = 5;
        mainLayout.marginBottom = 5;
        mainLayout.marginRight = 5;
        container.setLayout(mainLayout);

        //////////////////////////////////////////////////////////////////
        Control lastControl = null;

        if (gameType.hasEnglishPatch()) {
            applyEnglishPatch = new Button(container, SWT.CHECK);
            applyEnglishPatch.setText("Apply English Patch");
            applyEnglishPatch.setToolTipText("Given a raw Japanese version of the game, apply the localization patch from Serenes Forest on it. The result is an English version of the game.");

            FormData patchData = new FormData();
            patchData.left = new FormAttachment(0, 5);
            patchData.top = new FormAttachment(0, 5);

            applyEnglishPatch.setLayoutData(patchData);

            lastControl = applyEnglishPatch;
        }

        if (gameType == GameType.FE7) {
            tripleEffectiveness = new Button(container, SWT.CHECK);
            tripleEffectiveness.setText("Set Effectiveness to 3x");
            tripleEffectiveness.setToolTipText("Reverts the weapon effectiveness to 3x like in the Japanese release, instead of 2x.");

            FormData effectivenessData = new FormData();
            effectivenessData.left = new FormAttachment(0, 5);
            if (lastControl == null) {
                effectivenessData.top = new FormAttachment(0, 5);
            } else {
                effectivenessData.top = new FormAttachment(lastControl, 10);
            }
            tripleEffectiveness.setLayoutData(effectivenessData);

            lastControl = tripleEffectiveness;
        }

        //////////////////////////////////////////////////////////////////
        randomizeChestVillageRewards = new Button(container, SWT.CHECK);
        if (gameType == GameType.FE4) {
            randomizeChestVillageRewards.setText("Randomize Rings");
            randomizeChestVillageRewards.setToolTipText("Every instance of obtainable ring is randomized to a different kind of ring.");
        } else {
            randomizeChestVillageRewards.setText("Randomize Rewards");
            randomizeChestVillageRewards.setToolTipText("Rewards from chests, villages, and story events will now give out random rewards. Plot-important promotion items are excluded.");
        }

        FormData chestVillageData = new FormData();
        chestVillageData.left = new FormAttachment(0, 5);
        if (lastControl != null) {
            chestVillageData.top = new FormAttachment(lastControl, 10);
        } else {
            chestVillageData.top = new FormAttachment(0, 5);
        }
        randomizeChestVillageRewards.setLayoutData(chestVillageData);

        Control previousControl = randomizeChestVillageRewards;

        if (gameType == GameType.FE9) {
            rewardModeContainer = new Composite(container, SWT.NONE);
            rewardModeContainer.setLayout(new FormLayout());

            FormData containerData = new FormData();
            containerData.left = new FormAttachment(randomizeChestVillageRewards, 5, SWT.LEFT);
            containerData.top = new FormAttachment(randomizeChestVillageRewards, 5);
            rewardModeContainer.setLayoutData(containerData);

            similarRewardsButton = new Button(rewardModeContainer, SWT.RADIO);
            similarRewardsButton.setText("Similar Replacements");
            similarRewardsButton.setToolTipText("Replaces rewards with those of a similar type.\ne.g. Weapons are replaced with weapons, stat boosters are replaced with other stat boosters, etc.");
            similarRewardsButton.setSelection(true);
            rewardMode = RewardMode.SIMILAR;

            FormData buttonData = new FormData();
            buttonData.left = new FormAttachment(0, 0);
            buttonData.top = new FormAttachment(0, 0);
            similarRewardsButton.setLayoutData(buttonData);

            randomRewardsButton = new Button(rewardModeContainer, SWT.RADIO);
            randomRewardsButton.setText("Random Replacements");
            randomRewardsButton.setToolTipText("Replaces rewards with anything.");

            buttonData = new FormData();
            buttonData.left = new FormAttachment(0, 0);
            buttonData.top = new FormAttachment(similarRewardsButton, 5);
            randomRewardsButton.setLayoutData(buttonData);

            similarRewardsButton.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    rewardMode = RewardMode.SIMILAR;
                }
            });

            randomRewardsButton.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    rewardMode = RewardMode.RANDOM;
                }
            });

            similarRewardsButton.setEnabled(false);
            randomRewardsButton.setEnabled(false);

            randomizeChestVillageRewards.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    similarRewardsButton.setEnabled(randomizeChestVillageRewards.getSelection());
                    randomRewardsButton.setEnabled(randomizeChestVillageRewards.getSelection());
                }
            });

            previousControl = rewardModeContainer;
        }

        // Random enemy drops
        if (gameType == GameType.FE9 || gameType == GameType.FE7 || gameType == GameType.FE8) {
            enemyDropsButton = new Button(container, SWT.CHECK);
            enemyDropsButton.setText("Add Random Enemy Drops");
            enemyDropsButton.setToolTipText("Gives a chance for random minions to drop weapons or a random item.");
            enemyDropsButton.setSelection(false);

            FormData dropData = new FormData();
            dropData.left = new FormAttachment(0, 5);
            dropData.top = new FormAttachment(previousControl, 10);
            enemyDropsButton.setLayoutData(dropData);

            enemyDropChanceSpinner = new Spinner(container, SWT.NONE);
            enemyDropChanceSpinner.setValues(10, 1, 100, 0, 1, 5);
            enemyDropChanceSpinner.setEnabled(false);

            FormData spinnerData = new FormData();
            spinnerData.right = new FormAttachment(100, -5);
            spinnerData.top = new FormAttachment(enemyDropsButton, 5);
            enemyDropChanceSpinner.setLayoutData(spinnerData);

            enemyDropChanceLabel = new Label(container, SWT.RIGHT);
            enemyDropChanceLabel.setText("Chance: ");
            enemyDropChanceLabel.setEnabled(false);

            FormData labelData = new FormData();
            labelData.right = new FormAttachment(enemyDropChanceSpinner, -5);
            labelData.top = new FormAttachment(enemyDropChanceSpinner, 0, SWT.CENTER);
            enemyDropChanceLabel.setLayoutData(labelData);

            enemyDropsButton.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    enemyDropChanceSpinner.setEnabled(enemyDropsButton.getSelection());
                    enemyDropChanceLabel.setEnabled(enemyDropsButton.getSelection());
                }
            });

            previousControl = enemyDropChanceSpinner;
        }

        if (gameType == GameType.FE9) {
            readDataFileButton = new Button(container, SWT.CHECK);
            readDataFileButton.setText("Read Modifications from File");
            readDataFileButton.setToolTipText("Allows fine tuning of Units/Classes/Items, alternative to randomization.");
            readDataFileButton.setSelection(false);
            readDataFileButton.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    setReadDataFile(readDataFileButton.getSelection());
                }
            });

            FormData dropData = new FormData();
            dropData.left = new FormAttachment(0, 5);
            dropData.top = new FormAttachment(previousControl, 10);
            readDataFileButton.setLayoutData(dropData);

            selectDataSetButton = new Button(container, SWT.PUSH);
            selectDataSetButton.setText("...");
            OpenMultiFileFlow multiFlow = new OpenMultiFileFlow(shell, this);
            multiFlow.addFilterFileTypes("*.txt");
            selectDataSetButton.addListener(SWT.Selection, multiFlow);
            selectDataSetButton.setEnabled(false);

            dropData = new FormData();
            dropData.left = new FormAttachment(previousControl, -50);
            dropData.top = new FormAttachment(previousControl, 5);
            selectDataSetButton.setLayoutData(dropData);

            previousControl = readDataFileButton;

            dataFileSetTable = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
            dataFileSetTable.setLinesVisible(true);
            dataFileSetTable.setEnabled(false);

            TableColumn levelColumn = new TableColumn(dataFileSetTable, SWT.NONE);
            levelColumn.setText("File");
            levelColumn.setWidth(220);

            TableColumn levelColumn2 = new TableColumn(dataFileSetTable, SWT.NONE);
            levelColumn2.setText("Path");
            levelColumn2.setWidth(0);

            dropData = new FormData();
            dropData.left = new FormAttachment(0, 5);
            dropData.top = new FormAttachment(previousControl, 10);
            dataFileSetTable.setLayoutData(dropData);
            previousControl = dataFileSetTable;
        }
    }

    public void setPatchingEnabled(boolean patchingEnabled) {
        if (applyEnglishPatch != null) {
            if (patchingEnabled) {
                applyEnglishPatch.setEnabled(true);
            } else {
                applyEnglishPatch.setEnabled(false);
                applyEnglishPatch.setSelection(false);
            }
        }
    }

    public MiscellaneousOptions getMiscellaneousOptions() {
        if (type.isGBA()) {
            switch (type) {
                case FE6:
                    return new MiscellaneousOptions(applyEnglishPatch.getSelection(), randomizeChestVillageRewards.getSelection(), false);
                case FE7:
                default:
                    return new MiscellaneousOptions(randomizeChestVillageRewards.getSelection(), enemyDropsButton.getSelection() ? enemyDropChanceSpinner.getSelection() : 0, tripleEffectiveness.getSelection());
            }
        } else if (type.isSFC()) {
            switch (type) {
                case FE4:
                    return new MiscellaneousOptions(applyEnglishPatch.getSelection(), randomizeChestVillageRewards.getSelection(), false);
                default:
                    return new MiscellaneousOptions(false, 0, false);
            }
        } else if (type.isGCN()) {
            return new MiscellaneousOptions(false, false, randomizeChestVillageRewards.getSelection(), rewardMode,
                    enemyDropsButton.getSelection() ? enemyDropChanceSpinner.getSelection() : 0,
                    readDataFileButton.getSelection(), Arrays.stream(dataFileSetTable.getItems()).map(x -> x.getText(1)).collect(Collectors.toSet()));
        }

        return new MiscellaneousOptions(false, 0, false);
    }

    public void setMiscellaneousOptions(MiscellaneousOptions options) {
        if (options == null) {
            // Shouldn't happen.
        } else {
            if (applyEnglishPatch != null) {
                applyEnglishPatch.setSelection(options.applyEnglishPatch);
            }
            if (tripleEffectiveness != null) {
                tripleEffectiveness.setSelection(options.tripleEffectiveness);
            }
            if (randomizeChestVillageRewards != null) {
                randomizeChestVillageRewards.setSelection(options.randomizeRewards);
            }

            if (similarRewardsButton != null) {
                similarRewardsButton.setSelection(options.rewardMode == RewardMode.SIMILAR);
                similarRewardsButton.setEnabled(options.randomizeRewards);
            }
            if (randomRewardsButton != null) {
                randomRewardsButton.setSelection(options.rewardMode == RewardMode.RANDOM);
                randomRewardsButton.setEnabled(options.randomizeRewards);
            }
            if (enemyDropsButton != null && enemyDropChanceSpinner != null) {
                enemyDropsButton.setSelection(options.enemyDropChance > 0);
                enemyDropChanceSpinner.setEnabled(options.enemyDropChance > 0);
                enemyDropChanceLabel.setEnabled(options.enemyDropChance > 0);
                if (options.enemyDropChance > 0) {
                    enemyDropChanceSpinner.setSelection(options.enemyDropChance);
                }
            }
            if (readDataFileButton != null) {
                readDataFileButton.setSelection(options.readDataFile);
            }
            if (selectDataSetButton != null) {
                selectDataSetButton.setEnabled(options.readDataFile);
            }
            if (dataFileSetTable != null) {
                dataFileSetTable.setEnabled(options.readDataFile);
                dataFileSetTable.clearAll();
                for (String file : options.dataFileSet)
                {
                    TableItem item = new TableItem(dataFileSetTable, SWT.NONE);
                    String[] filePath = file.split(Pattern.quote(File.separator));
                    item.setText(0, filePath[filePath.length - 1]);
                    item.setText(1, file);
                }
            }
        }
    }

    public void setReadDataFile(boolean enabled)
    {
        selectDataSetButton.setEnabled(enabled);
        dataFileSetTable.setEnabled(enabled);
    }

    @Override
    public void onSelectedFiles(String[] absolutePaths) {
        dataFileSetTable.removeAll();
        for (String file : absolutePaths) {
            TableItem item = new TableItem(dataFileSetTable, SWT.NONE);
            String[] filePath = file.split(Pattern.quote(File.separator));
            item.setText(0, filePath[filePath.length - 1]);
            item.setText(1, file);
        }
    }
}
