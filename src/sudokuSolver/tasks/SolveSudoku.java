package sudokuSolver.tasks;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import sudokuSolver.model.Rune;
import sudokuSolver.model.Tile;
import sudokuSolver.util.BasicInformation;
import sudokuSolver.util.DetectRuneType;
import sudokuSolver.util.Sudoku;
import util.Sleep;
import util.Task;

import java.awt.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SolveSudoku extends Task {

    private final Map<Tile, Rune> matrix;
    private final ArrayList<Rune> sudokuRunes;

    private final int rootWidgetId = 288;
    private final int minSleepTime, maxSleepTime;

    public SolveSudoku(MethodProvider api, int minSleepTime, int maxSleepTime) {
        super(api);

        this.minSleepTime = minSleepTime;
        this.maxSleepTime = maxSleepTime;

        matrix = new HashMap<>();
        sudokuRunes = new ArrayList<>(Arrays.asList(Rune.FIRE, Rune.WATER, Rune.AIR, Rune.EARTH, Rune.MIND, Rune.BODY, Rune.DEATH, Rune.CHAOS, Rune.LAW));
    }

    @Override
    public boolean canProcess() {
        return api.getWidgets().get(rootWidgetId, 10) != null;
    }

    @Override
    public void process() {

        BasicInformation.status = "Solving sudoku";

        if (matrix.size() == 0) {

            detectProblem();

            if (matrix.containsValue(Rune.UNDEFINED) || runeOnWrongPlace()) {
                closeSudoku(false);
                matrix.clear();
            }

        } else {

            solveSudoku();

            boolean error = runeOnWrongPlace();

            if (!error) {
                BasicInformation.solved++;
                closeSudoku(true);
            } else {
                closeSudoku(false);
            }

            matrix.clear();
        }
    }

    private void solveSudoku() {

        Collections.shuffle(sudokuRunes);
        for (Rune rune : sudokuRunes) {

            //Select correct rune to put into sudoku
            RS2Widget runeWidget = api.widgets.get(rootWidgetId, rune.getWidgetId());
            if (runeWidget != null) {

                runeWidget.interact("Ok");

                //We're not checking if rune is selected, but we're sleeping a random time
                Sleep.sleepUntil(() -> false, maxSleepTime + 1, ThreadLocalRandom.current().nextInt(minSleepTime, maxSleepTime + 1));

                for (Tile tile : Tile.values()) {

                    Rune correctRune = matrix.get(tile);

                    if (correctRune.equals(rune)) {

                        RS2Widget tileWidget = api.widgets.get(rootWidgetId, tile.getWidgetChildId());

                        //If tile doesn't already contains this rune
                        if (tileWidget != null && !getRuneFromWidget(tileWidget).equals(rune)) {

                            //Put rune in correct tile
                            tileWidget.interact("Ok");

                            //Sleep until we detect the rune with minimum sleep
                            Sleep.sleepUntil(() -> getRuneFromWidget(tileWidget).equals(rune), maxSleepTime + 1, ThreadLocalRandom.current().nextInt(minSleepTime, maxSleepTime + 1));
                        }
                    }
                }
            }
        }
    }

    private void detectProblem() {

        ArrayList<String> args = new ArrayList<>();

        for (Tile tile : Tile.values()) {

            RS2Widget tileWidget = api.widgets.get(rootWidgetId, tile.getWidgetChildId());
            Rune rune = getRuneFromWidget(tileWidget);

            if (!rune.equals(Rune.UNDEFINED)) {
                args.add(getArgForSudoku(tile.getX(), tile.getY(), rune.getSudokuId()));
            }
        }

        int[][] numberMatrix = Sudoku.solveSudoku(args);
        setMatrix(numberMatrix);
    }

    private boolean runeOnWrongPlace() {

        for (Tile tile : Tile.values()) {

            Rune correctRune = matrix.get(tile);

            RS2Widget tileWidget = api.widgets.get(rootWidgetId, tile.getWidgetChildId());
            Rune placedRune = getRuneFromWidget(tileWidget);

            if (placedRune != Rune.UNDEFINED) {
                if (!placedRune.equals(correctRune)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getArgForSudoku(int X, int Y, int id) {
        //Y + X for sudoku class
        return "" + Y + X + id;
    }

    private void setMatrix(int[][] numberMatrix) {

        int tileCount = 1;

        for (int[] aNumberMatrix : numberMatrix) {
            for (int solution : aNumberMatrix) {

                for (Rune rune : Rune.values()) {
                    if (solution == rune.getSudokuId()) {
                        matrix.put(Tile.valueOf("T" + tileCount++), rune);
                        break;
                    }
                }
            }
        }
    }

    private void closeSudoku(boolean forReward) {
        if (forReward) {
            api.widgets.get(rootWidgetId, 8).interact("Ok");
        } else {
            api.widgets.get(rootWidgetId, 132).interact("Close");
        }

        Sleep.sleepUntil(() -> api.getWidgets().get(rootWidgetId, 10) == null, 3000);
    }

    private Rune getRuneFromWidget(RS2Widget widget) {
        int X = widget.getPosition().x + 16;
        int Y = widget.getPosition().y + 21;
        Color color = api.colorPicker.colorAt(X, Y);

        return DetectRuneType.getRuneType(color);
    }

    public Map<Tile, Rune> getMatrix() {
        return matrix;
    }
}
