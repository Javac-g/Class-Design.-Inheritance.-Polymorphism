package task2;

import java.util.Map;

public class Cappuccino extends Coffee {
    public Cappuccino(String name, int rating) {
        super(name, rating);
    }

    @Override
    public Map<String, Integer> makeDrink() {
        addComponent("Water", 100).addComponent("Arabica", 20).addComponent("Milk", 50);
        return getIngredients();
    }
}
