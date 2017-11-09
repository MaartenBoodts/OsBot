package sudokuSolver.model;

public enum Rune {

    UNDEFINED(0, 0, 0, 0, 0, 0, 0, 0),
    FIRE(554, 1, 125, 0, 0, 168, 34, 21),
    WATER(555, 2, 122, 0, 0, 21, 26, 168),
    AIR(556, 3, 123, 0, 0, 132, 122, 122),
    EARTH(557, 4, 121, 0, 0, 109, 73, 13),
    MIND(558, 5, 124, 3, 0, 72, 70, 9),
    BODY(559, 6, 126, 3, 0, 21, 17, 17),
    DEATH(560, 7, 127, 198, 0, 228, 225, 225),
    NATURE(561, 0, 0, 198, 0, 0, 0, 0),
    CHAOS(562, 8, 128, 99, 0, 224, 168, 26),
    LAW(563, 9, 129, 264, 0, 26, 34, 224),
    COSMIC(564, 0, 0, 55, 0, 0, 0, 0),
    BLOOD(565, 0, 0, 440, 0, 0, 0, 0),
    SOUL(566, 0, 0, 330, 0, 0, 0, 0);

    final int runeId, sudokuId, widgetId;
    final int shopPrice;
    int gePrice;
    final int RValue, GValue, BValue;

    Rune(int runeId, int sudokuId, int widgetId, int shopPrice, int gePrice, int RValue, int GValue, int BValue) {
        this.runeId = runeId;
        this.sudokuId = sudokuId;
        this.widgetId = widgetId;
        this.shopPrice = shopPrice;
        this.gePrice = gePrice;
        this.RValue = RValue;
        this.GValue = GValue;
        this.BValue = BValue;
    }

    public int getRuneId() {
        return runeId;
    }

    public int getSudokuId() {
        return sudokuId;
    }

    public int getWidgetId() {
        return widgetId;
    }

    public int getShopPrice() {
        return shopPrice;
    }

    public int getGePrice() {
        return gePrice;
    }

    public void setGePrice(int gePrice) {
        this.gePrice = gePrice;
    }

    public int getRValue() {
        return RValue;
    }

    public int getGValue() {
        return GValue;
    }

    public int getBValue() {
        return BValue;
    }
}