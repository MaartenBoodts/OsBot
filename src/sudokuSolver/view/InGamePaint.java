package sudokuSolver.view;

import org.osbot.rs07.api.ui.RS2Widget;
import sudokuSolver.RandomIncSudokuSolver;
import sudokuSolver.model.Rune;
import sudokuSolver.model.Tile;
import sudokuSolver.util.BasicInformation;

import java.awt.*;
import java.util.Map;

public class InGamePaint {

    private final Color color1 = new Color(250, 10, 14, 128);
    private final Color color2 = new Color(0, 0, 0);
    private final Color color3 = new Color(42, 10, 14, 233);

    private final BasicStroke stroke1 = new BasicStroke(1);

    private final Font font1 = new Font("Candara", Font.BOLD, 16);
    private final Font font2 = new Font("Candara", Font.PLAIN, 16);
    private final Font font3 = new Font("Candara", Font.PLAIN, 12);

    private final RandomIncSudokuSolver script;

    public InGamePaint(RandomIncSudokuSolver script) {
        this.script = script;
    }

    public void repaint(Graphics2D g) {

        //Mouse paint
        Point mP = script.mouse.getPosition();

        g.setColor(Color.black);
        g.drawLine(mP.x, 501, mP.x, 0);
        g.drawLine(0, mP.y, 764, mP.y);

        //Sudoku runes paint
        if (script.widgets.get(288, 10) != null) {

            Map<Tile, Rune> matrix = script.solveSudoku.getMatrix();
            g.setFont(font3);

            if (matrix.size() > 0) {
                for (Tile tile : matrix.keySet()) {
                    Rune rune = matrix.get(tile);
                    RS2Widget tileWidget = script.widgets.get(288, tile.getWidgetChildId());


                    g.drawString(rune.name(), tileWidget.getPosition().x, tileWidget.getPosition().y + 30);
                }
            }
        }

        //Variables
        final long runeTime = System.currentTimeMillis() - BasicInformation.startTime;
        final int solvedHr = (int) (BasicInformation.solved * 3600000D / runeTime);
        final int profitHr = (int) (BasicInformation.profit * 3600000D / runeTime);

        //Info paint
        g.setColor(color1);
        g.fillRoundRect(550, 206, 183, 256, 10, 10);
        g.setColor(color2);
        g.setStroke(stroke1);
        g.drawRoundRect(550, 206, 183, 256, 10, 10);
        g.setFont(font1);
        g.setColor(color3);
        g.drawString("RandomIncSudokuSolver", 555, 240);
        g.setFont(font2);
        g.drawString("Version: " + BasicInformation.version, 555, 275);
        g.drawString("Runtime: " + formatTimeToHMS(runeTime), 555, 310);
        g.drawString("Status: " + BasicInformation.status, 555, 345);
        g.drawString("Solved: " + BasicInformation.solved + " (" + solvedHr + "/hr)", 555, 380);
        g.drawString("Profit: " + BasicInformation.profit + " (" + profitHr + "/hr)", 555, 415);
        g.setFont(font3);
        g.setColor(color2);
        g.drawString("Made by RandomInc for OsBot", 560, 443);
    }

    private String formatTimeToHMS(long timeToFormat) {
        long second = (timeToFormat / 1000) % 60;
        long minute = (timeToFormat / (1000 * 60)) % 60;
        long hour = (timeToFormat / (1000 * 60 * 60)) % 24;

        return String.format("%02dh %02dm %02ds", hour, minute, second);
    }
}
