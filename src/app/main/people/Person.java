package app.main.people;

import app.main.ChaohmShop;
import app.main.food.Food;
import app.main.food.Taste;
import app.main.food.ingredient.*;
import app.main.people.personal.Personal;

import java.util.List;
import java.util.Objects;

import static app.main.Util.percent;

/**
 * A person who is a customer of the shop, that can eat and pay for the food
 */
public class Person implements Customer{

    protected String name;
    protected int age;
    protected Personal personal;
    protected Feeling feeling;
    protected Food eat;

    public Person(String name, int age, Personal personal){
        this.name = name;
        this.age = age;
        this.personal = personal;
    }

    /**
     * Let person eat given food and set the feeling after eaten
     * @param food for eat
     */
    @Override
    public void eat(Food food){
        this.eat = food;
        List<Ingredient> ingredients = food.getIngredients();

        // Check the different of the price and sell price
        // Person will decrease score of the food if this food is to expensive
        int different = food.getSellPrice() - food.getPrice();
        int score =  percent(different, food.getPrice()) > 150 ? 1 : -1;

        // Get amount of each ingredient in list to check is enough for the person's personal
        int oil = (int) ingredients.stream().filter(ingredient -> Objects.equals(ingredient.getTaste(), Taste.LAN)).count();
        int sugar = (int) ingredients.stream().filter(ingredient -> Objects.equals(ingredient.getTaste(), Taste.SWEET)).count();
        int chaohm = (int) ingredients.stream().filter(ingredient -> ingredient.getTaste() == null).count();
        int salt = (int) ingredients.stream().filter(ingredient -> Objects.equals(ingredient.getTaste(), Taste.SALTY)).count();
        int total = ingredients.size();

        // Then check amount of ingredient is enough or not if it enough just add score, else decrease score
        score += personal.isEnough(new Oil(), oil, total) ?  1 : -1;
        score += personal.isEnough(new Sugar(), sugar, total) ?  1 : -1;
        score += personal.isEnough(new Chaohm(), chaohm, total) ?  1 : -1;
        score += personal.isEnough(new Salt(), salt, total) ?  1 : -1;

        // Set feelings of person by score
        feeling = switch (score){
            case 5, 4 -> Feeling.HAPPY;
            case 3, 2 -> Feeling.GOOD;
            case 1, 0, -1 -> Feeling.INDIFFERENT;
            case -2, -3 -> Feeling.BAD;
            case -4, -5 -> Feeling.TERRIBLE;
            default -> null;
        };
    }

    /**
     * Pay for the shop with feelings
     * If person feel happier the shop will give more money
     * And, another way person can pay 0 if they don't like the food from the shop
     * @param shop the shop which serve the person for the foods
     * @return amount of money that people paid
     */
    @Override
    public int pay(ChaohmShop shop){
        // set pay amount by feeling
        int pay = switch (feeling) {
            case HAPPY -> (int) (eat.getSellPrice() * 1.75);
            case GOOD -> (int) (eat.getSellPrice() * 1.25);
            case INDIFFERENT -> eat.getSellPrice();
            case BAD -> (int) (eat.getSellPrice() * 0.75);
            case TERRIBLE -> 0;
        };

        shop.addMoney(pay); // Pay to the shop
        return pay;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Feeling getFeeling() {
        return feeling;
    }
}
