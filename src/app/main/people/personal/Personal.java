package app.main.people.personal;

import app.main.food.ingredient.Ingredient;

public abstract class Personal {

    int oilCanEat;
    int saltCanEat;
    int sugarCanEat;
    int chaohmCanEat;
    int total;
    int baseLovePercent;

    public Personal(int oilCanEat, int saltCanEat, int sugarCanEat, int chaohmCanEat){
        this.oilCanEat = oilCanEat;
        this.saltCanEat = saltCanEat;
        this.sugarCanEat = sugarCanEat;
        this.chaohmCanEat = chaohmCanEat;
        this.total = oilCanEat + saltCanEat + sugarCanEat + chaohmCanEat;
    }

    public abstract boolean isEnough(Ingredient ingredient, int amount, int total);
}
