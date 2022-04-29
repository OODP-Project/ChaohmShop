package app.main.food.ingredient;

import app.main.food.Taste;

/**
 * The classes for the ingredient that will be mixed to the food in FoodMixer
 */
public class Ingredient {

    // Just crete fields to final because we don't want to change it later
    private final int price;
    private final Taste taste;

    public Ingredient(int price, Taste taste){
        this.price = price;
        this.taste = taste;
    }

    public Taste getTaste(){
        return taste;
    }

    public int getPrice(){
        return price;
    }
}
