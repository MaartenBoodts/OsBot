package sudokuSolver.tasks;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.MethodProvider;
import sudokuSolver.util.BasicInformation;
import util.Sleep;
import util.Task;

import java.util.concurrent.ThreadLocalRandom;

public class SetGameBrightness extends Task {

    private final int configsId = 166;
    private final int brightnessLevel = 4;

    public SetGameBrightness(MethodProvider api) {
        super(api);
    }

    @Override
    public boolean canProcess() {
        return !api.configs.checkBits(configsId, brightnessLevel);
    }

    @Override
    public void process() {

        BasicInformation.status = "Setting game brightness";

        openSettings();
        openBrightnessMenu();
        setCorrectBrightness();

    }

    private void openSettings() {
        if (!api.tabs.getOpen().equals(Tab.SETTINGS)) {

            api.tabs.open(Tab.SETTINGS);
            Sleep.sleepUntil(() -> api.tabs.getOpen().equals(Tab.SETTINGS), 1500, ThreadLocalRandom.current().nextInt(200, 600));
        }
    }

    private void openBrightnessMenu() {

        RS2Widget sunWidget = api.widgets.get(261, 16);

        if (!sunWidget.isVisible()) {

            RS2Widget screenWidget = api.widgets.get(261, 1, 1);
            screenWidget.interact("Display");

            Sleep.sleepUntil(sunWidget::isVisible, 1500, ThreadLocalRandom.current().nextInt(200, 600));
        }
    }

    private void setCorrectBrightness() {

        RS2Widget brightness4Widget = api.widgets.get(261, 18);
        brightness4Widget.interact("Adjust Screen Brightness");

        Sleep.sleepUntil(() -> api.configs.checkBits(configsId, brightnessLevel), 1500, ThreadLocalRandom.current().nextInt(200, 600));
    }
}
