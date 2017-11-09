package sudokuSolver.util;

import sudokuSolver.model.Rune;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DetectRuneType {

    private static final ArrayList<Rune> runes = new ArrayList<>(Arrays.asList(Rune.FIRE, Rune.WATER, Rune.AIR, Rune.EARTH, Rune.MIND, Rune.BODY, Rune.DEATH, Rune.CHAOS, Rune.LAW));

    private DetectRuneType() {
    }

    public static Rune getRuneType(Color color) {

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        for (Rune rune : runes) {
            if (marginallyEqual(r, rune.getRValue())
                    && marginallyEqual(g, rune.getGValue())
                    && marginallyEqual(b, rune.getBValue())) {

                return rune;
            }
        }
        return Rune.UNDEFINED;
    }

    private static boolean marginallyEqual(int a, int b) {

        int delta = Math.abs(a - b);
        int margin = 18; //Go any higher, and empty tile color will too close to color of chaos rune

        return delta <= margin;
    }
}