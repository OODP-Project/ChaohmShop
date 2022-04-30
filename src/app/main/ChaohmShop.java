package app.main;

import app.main.food.Food;
import app.main.food.FoodMixer;
import app.main.food.ingredient.Chaohm;
import app.main.food.ingredient.Oil;
import app.main.food.ingredient.Salt;
import app.main.food.ingredient.Sugar;
import app.main.people.Customer;
import app.main.people.Town;

import java.util.Stack;

public class ChaohmShop {
    protected Town town;
    protected int money = 1000;
    protected Stack<Food> foods;

    public ChaohmShop(Town town){
        this.town = town;
    }

    public Stack<Food> getFoodLeft(){
        return foods;
    }

    public void start(Stack<Food> foods)  {
        // Set foods stack to the fields and renew populations
        this.foods = foods;
        town.rePopulations();

        System.out.println("----------------- Chaohm shop ----------------");
        System.out.println("Welcome to chaohm shop at " + town.getName());

        // Serve the foods for a customer who is a person in the town until no foods left or no more people in the town
        while (!foods.isEmpty() && town.isCrowed()) {
            Customer customer = town.getPerson();
            customer.eat(foods.pop());

            System.out.println(customer.getName() + "is eating...");
            System.out.println(
                    customer.getName()
                    + " is feel " + customer.getFeeling()
                    + " pay + $" + customer.pay(this)
            );
        }

        // After served all foods or no people left in the town just show the report of today
        showReports();
    }

    /**
     * Show the reports of the day that the shop served all foods or served all customers
     */
    public void showReports(){
        System.out.println("There is " + getFoodLeft().size() + "left");
        if (town.isCrowed()){
            System.out.println("There is some people in the town is hungry!");
        } else {
            System.out.println("There is no people in the town now!");
        }
        System.out.println("Now our shop is have money : " + money);
        System.out.println("Thank you!");
    }

    public void addMoney(int amount){
        money += amount;
    }

    /**
     * Use money from the shop that can be error if money is not enough
     * @param amount of the money that want to use
     * @throws NotEnoughMoneyException when money is not enough
     */
    public void useMoney(int amount) throws NotEnoughMoneyException {
        if (amount > money){
            throw new NotEnoughMoneyException();
        }
        money -= amount;
    }

    public static void main(String[] args) {
        // This is a main here you can try to mix your ingredients below and start the program
        // This is ways to fun that see customer happy or not
        ChaohmShop shop = new ChaohmShop(new Town("MFU Town"));
        FoodMixer mixer = new FoodMixer();
        mixer.add(new Oil(), 1);
        mixer.add(new Sugar(), 5);
        mixer.add(new Chaohm(), 3);
        mixer.add(new Salt(), 2);
        shop.start(mixer.makesFoods(250, 30));
    }
}
