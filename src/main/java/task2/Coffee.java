package task2;

import java.util.HashMap;
import java.util.Map;

public class Coffee implements DrinkReceipt, DrinkPreparation, Rating {
    private String name;
    private int rating;
    private Map<String, Integer> ingredients;

    public Coffee() {
        this.ingredients = new HashMap<>();
    }

    public Coffee(String name, int rating) {
        this.name = name;
        this.rating = rating;
        this.ingredients = new HashMap<>();
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public Map<String, Integer> makeDrink() {
        addComponent("Water", 100).addComponent("Arabica", 20);
        return ingredients;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DrinkReceipt addComponent(String componentName, int componentCount) {
        ingredients.put(componentName, componentCount);
        return this;
    }

    @Override
    public int getRating() {
        return rating;
    }
}
