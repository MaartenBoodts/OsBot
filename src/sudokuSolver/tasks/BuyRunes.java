package sudokuSolver.tasks;

import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import sudokuSolver.model.Rune;
import sudokuSolver.util.BasicInformation;
import util.Sleep;
import util.Task;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BooleanSupplier;

public class BuyRunes extends Task {

    private final ArrayList<Rune> runesToBuy;
    private final String buyRunesOption = "Find out what the runes are.";

    private RS2Widget chatOption;

    public BuyRunes(MethodProvider api, ArrayList<Rune> runesToBuy) {
        super(api);

        this.runesToBuy = runesToBuy;
    }

    @Override
    public boolean canProcess() {

        chatOption = api.widgets.get(219, 0, 2);

        return (chatOption != null && chatOption.getMessage().equals(buyRunesOption)) || api.store.isOpen();
    }

    @Override
    public void process() {

        BasicInformation.status = "Buying runes";

        if ((chatOption != null && chatOption.isVisible())) {
            api.dialogues.selectOption(buyRunesOption);
            Sleep.sleepUntil(() -> api.store.isOpen(), 1500);
        }

        if (buyActualRunes()) {
            api.store.close();
            Sleep.sleepUntil(() -> !api.store.isOpen(), 1500);
        }
    }

    //Using widgets because nof 09/11/2017, api.store.buy() is broken
    private boolean buyActualRunes() {

        RS2Widget[] runesInStore = api.widgets.getWidgets(300, 2);

        for (RS2Widget runeWidget : runesInStore) {

            for (Rune rune : runesToBuy) {

                int amountInStore = runeWidget.getItemAmount();

                if (amountInStore > 0) {

                    if (runeWidget.getItemId() == rune.getRuneId()) {

                        if (hasEnoughMoneyInInv(rune.getShopPrice())) {

                            runeWidget.interact("Buy 10");

                            Sleep.sleepUntil(new BooleanSupplier() {
                                @Override
                                public boolean getAsBoolean() {
                                    runeWidget.refresh();
                                    return runeWidget.getItemAmount() < amountInStore;
                                }
                            }, 300, ThreadLocalRandom.current().nextInt(100, 300));

                            int currentStock = runeWidget.getItemAmount();
                            setProfit(rune.getGePrice(), rune.getShopPrice(), (amountInStore - currentStock));

                            return false;
                        } else {

                            api.log("Stopping bot because we do nog have money left to buy runes");
                            api.bot.stop();
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean hasEnoughMoneyInInv(int price) {
        Item coins = api.inventory.getItem(995);
        return coins != null && coins.getAmount() >= price;
    }

    private void setProfit(int gePrice, int shopPrice, int amountBought) {
        int difference = gePrice - shopPrice;
        BasicInformation.profit += (difference * amountBought);
    }
}
