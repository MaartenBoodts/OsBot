package sudokuSolver.tasks;

import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import sudokuSolver.util.BasicInformation;
import util.Sleep;
import util.Task;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TalkToAli extends Task {

    private final String[] chatOptions = new String[]{"I would like to have a look at your selection of runes.", "Try to open a large casket of runes", "Examine lock."};

    private NPC npcAli;
    private RS2Widget chatOptionWidget;

    public TalkToAli(MethodProvider api) {
        super(api);
    }

    @Override
    public boolean canProcess() {

        npcAli = returnAli(api.npcs.get(3304, 3211));
        chatOptionWidget = api.widgets.get(219, 0, 2);

        return npcAli != null &&
                !api.store.isOpen() &&
                api.getWidgets().get(288, 10) == null && //Sudoku widget
                (chatOptionWidget == null || !chatOptionWidget.getMessage().equals("Find out what the runes are.")); //Buy runes chat option
    }

    @Override
    public void process() {

        BasicInformation.status = "Chatting with Ali";

        if (api.dialogues.isPendingContinuation()) {

            api.dialogues.clickContinue();
            Sleep.sleepUntil(() -> (api.dialogues.isPendingOption() ||
                    api.dialogues.isPendingContinuation()), 2500, ThreadLocalRandom.current().nextInt(200, 600));

        } else if (api.dialogues.isPendingOption()) {

            api.dialogues.selectOption(chatOptions);
            Sleep.sleepUntil(() -> (
                    api.getWidgets().get(288, 10) != null ||
                            api.dialogues.isPendingOption() ||
                            api.dialogues.isPendingContinuation()), 2500, ThreadLocalRandom.current().nextInt(200, 600));

        } else {

            if (npcAli.isOnScreen() && npcAli.isVisible()) {

                npcAli.interact("Talk-to");
                Sleep.sleepUntil(() -> (api.dialogues.isPendingContinuation()), 2500, ThreadLocalRandom.current().nextInt(200, 600));
            } else {

                api.camera.toEntity(npcAli);
                Sleep.sleepUntil(() -> (npcAli.isOnScreen() && npcAli.isVisible()), 3000, ThreadLocalRandom.current().nextInt(200, 600));
            }
        }
    }

    private NPC returnAli(List<NPC> npcs) {
        for (NPC npc : npcs) {
            if (npc.getId() == 3533) {
                return npc;
            }
        }
        return null;
    }
}
