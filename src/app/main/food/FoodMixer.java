package app.main.food;

import app.main.food.ingredient.Ingredient;

import java.util.*;

/**
 * The class for mixes ingredients of the food and makes a stack of the food from ingredients
 */
public class FoodMixer {

    private final List<Ingredient> ingredients;

    public FoodMixer(){
        ingredients = new ArrayList<>();
    }

    /**
     * Add the ingredient to the mixer for a dish of the food
     * @param ingredient that want to add
     * @param amount of the ingredient
     */
    public void add(Ingredient ingredient, int amount) {
        // Loop to add ingredient follow amount
        for (int i = 0; i < amount; i++){
            ingredients.add(ingredient);
        }
    }

    /**
     * Makes the stack of the foods from ingredients added
     * @param amount of the foods
     * @param sellPrice sell price of each food
     * @return stack of the foods
     */
    public Stack<Food> makesFoods(int amount, int sellPrice) {
        Stack<Food> foods = new Stack<>();
        foods.setSize(amount);
        Collections.fill(foods, new Food(ingredients, sellPrice));
        return foods;
    }

    /**
     * @return base cost for all ingredients at 1 dish
     */
    public int getCost() {
        int cost = 0;
        for (Ingredient i : ingredients){
            cost += i.getPrice();
        }
        return cost;
    }
}
