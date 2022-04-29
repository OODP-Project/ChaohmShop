package app.main.people.personal;

import app.main.food.Taste;
import app.main.food.ingredient.Ingredient;

import java.util.Objects;

import static app.main.Util.percent;

public class SweetLover extends Personal{

    public SweetLover() {
        super(2, 2, 5, 4);
        baseLovePercent = percent(sugarCanEat, total);
    }

    @Override
    public boolean isEnough(Ingredient ingredient, int amount, int total) {
        if (Objects.equals(ingredient.getTaste(), Taste.SWEET)){
            return percent(amount, total) > baseLovePercent;
        } else {
            return percent(amount, total) < baseLovePercent;
        }
    }
}
