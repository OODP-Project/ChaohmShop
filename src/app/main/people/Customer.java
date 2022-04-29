package app.main.people;

import app.main.ChaohmShop;
import app.main.food.Food;

/**
 * The interface for customer of the shop
 */
public interface Customer {

    /**
     * Eat some food
     * @param food to eat
     */
    void eat(Food food);

    /**
     * Pay for the shop
     * @param shop to pay
     * @return int that customer paid
     */
    int pay(ChaohmShop shop);

    String getName();
    Feeling getFeeling();
}
