package app.main.people.personal;

import app.main.food.Taste;
import app.main.food.ingredient.Ingredient;

import java.util.Objects;

import static app.main.Util.percent;

public class SaltyLover extends Personal{

    public SaltyLover() {
        super(2, 5, 2, 3);
        baseLovePercent = percent(oilCanEat, total);
    }

    @Override
    public boolean isEnough(Ingredient ingredient, int amount, int total) {
        if (Objects.equals(ingredient.getTaste(), Taste.SALTY)){
            return percent(amount, total) > baseLovePercent;
        } else {
            return percent(amount, total) < baseLovePercent;
        }
    }
}
