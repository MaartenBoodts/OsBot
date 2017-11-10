package sudokuSolver;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import sudokuSolver.model.Rune;
import sudokuSolver.tasks.BuyRunes;
import sudokuSolver.tasks.SetGameBrightness;
import sudokuSolver.tasks.SolveSudoku;
import sudokuSolver.tasks.TalkToAli;
import sudokuSolver.util.BasicInformation;
import sudokuSolver.view.InGamePaint;
import util.Exchange;
import util.Task;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@ScriptManifest(name = "RandomIncSudokuSolver", author = "RandomInc", version = 1.01, info = "Solves the sudoku in the Rogue Trader minigame and buys runes", logo = "https://i.imgur.com/ZzpEuXV.png")

public class RandomIncSudokuSolver extends Script {

    private ArrayList<Task> tasks;

    public SolveSudoku solveSudoku;
    private SetGameBrightness setGameBrightness;
    private BuyRunes buyRunes;
    private TalkToAli talkToAli;

    private InGamePaint paint;

    @Override
    public void onStart() throws InterruptedException {

        setPriceRunes();

        BasicInformation.version = "1.01";
        BasicInformation.startTime = System.currentTimeMillis();
        BasicInformation.status = "Initializing script";

        this.paint = new InGamePaint(this);

        talkToAli = new TalkToAli(this);
        solveSudoku = new SolveSudoku(this, 50, 200);
        buyRunes = new BuyRunes(this, new ArrayList<>(Arrays.asList(Rune.DEATH, Rune.NATURE, Rune.COSMIC)));
        setGameBrightness = new SetGameBrightness(this);

        tasks = new ArrayList<>(Arrays.asList(talkToAli, solveSudoku, buyRunes, setGameBrightness));
    }

    @Override
    public int onLoop() throws InterruptedException {
        if (this.client.isLoggedIn()) {
            tasks.forEach(Task::run);
        }

        return ThreadLocalRandom.current().nextInt(100, 250);
    }

    @Override
    public void onExit() throws InterruptedException {
        log("Thanks for using RandomIncSudokuSolver! Made by RandomInc");
    }

    @Override
    public void onPaint(Graphics2D g) {
        paint.repaint(g);
    }

    private void setPriceRunes() {
        for (Rune rune : Rune.values()) {
            if (rune.getShopPrice() != 0) {
                rune.setGePrice(Exchange.getPrice(rune.getRuneId()));
                log(rune.getRuneId() + " - " + Exchange.getPrice(rune.getRuneId()));
            }
        }
    }
}