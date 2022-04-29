package app.main.people.personal;

import app.main.food.Taste;
import app.main.food.ingredient.Ingredient;
import java.util.Objects;

import static app.main.Util.percent;

public class LanLover extends Personal {

    public LanLover() {
        super(5, 3, 2, 2);
        baseLovePercent = percent(oilCanEat, total);
    }

    @Override
    public boolean isEnough(Ingredient ingredient, int amount, int total) {
        if (Objects.equals(ingredient.getTaste(), Taste.LAN)){
            return percent(amount, total) > baseLovePercent - 20;
        } else {
            return percent(amount, total) < baseLovePercent + 20;
        }
    }
}
