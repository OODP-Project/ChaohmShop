package app.main.food;

import app.main.food.ingredient.Ingredient;

import java.util.List;

public class Food {

    private int price = 0;
    private final int sellPrice;
    private final List<Ingredient> ingredients;

    public Food(List<Ingredient> ingredients, int sellPrice){
        this.ingredients = ingredients;
        this.sellPrice = sellPrice;
        ingredients.forEach((ingredient)-> price+=ingredient.getPrice());
    }

    public List<Ingredient> getIngredients(){
        return ingredients;
    }

    public int getPrice() {
        return price;
    }

    public int getSellPrice() {
        return sellPrice;
    }
}
