package app.main.ui;

import app.main.ChaohmShop;
import app.main.NotEnoughMoneyException;
import app.main.food.Food;
import app.main.food.FoodMixer;
import app.main.food.ingredient.Chaohm;
import app.main.food.ingredient.Oil;
import app.main.food.ingredient.Salt;
import app.main.food.ingredient.Sugar;
import app.main.people.Customer;
import app.main.people.Town;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Stack;

public class ProcessUI extends ChaohmShop implements ActionListener {

    private Timer timer;
    private final ChaohmGUI frame;
    private final JTextArea monitor;
    private final JLabel moneyLabel;
    private final JButton startButton;
    private int good;
    private int normal;
    private int bad;

    /**
     * A constructor to be called for the program which initialize important things here
     */
    public ProcessUI(){
        // Set up some fields and set action for start button
        super(new Town("MFU village"));
        frame = new ChaohmGUI();
        monitor = frame.getShopMonitor();
        moneyLabel = frame.getMoneyLabel();
        startButton = frame.getStartButton();
        startButton.setAction(new StartAction());
    }

    /**
     * The action listener for start button click
     * We create this class in here because we need to run this class that implemented ActionListener
     */
    private class StartAction extends AbstractAction{
        public StartAction(){
            putValue(NAME, "Start");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Start food mixer from number of the ingredients from the frame
            HashMap<String, Integer> amount = frame.getIngredientsAmount(); // Get hash map of ingredient
            // Mix ingredients
            FoodMixer mixer = new FoodMixer();
            mixer.add(new Sugar(), amount.get("sugar"));
            mixer.add(new Salt(), amount.get("salt"));
            mixer.add(new Chaohm(), amount.get("chaohm"));
            mixer.add(new Oil(), amount.get("oil"));

            // Beware that sum cost of the foods have to less than the shop' money
            int sellAmount = frame.getSellAmount();
            int sellPrice = frame.getSellPrice();
            int sumCost = sellAmount * mixer.getCost();

            try{
                // Try to use money to buy the ingredients about the sum cost
                // IIf money is not enough NotEnough Money Exception will be thrown
                useMoney(sumCost);
                moneyLabel.setText(String.valueOf(money));

                // Disable all button and start the program
                startButton.setEnabled(false);
                startButton.setBackground(Color.YELLOW);
                frame.getPlusButton().values().forEach((b) -> b.setEnabled(false));
                frame.getMinusButton().values().forEach((b) -> b.setEnabled(false));

                start(mixer.makesFoods(sellAmount, sellPrice));

            } catch (NotEnoughMoneyException ex){
                // Show exception in the monitor
                monitor.setText(
                        "Your money is not enough for make foods" +
                        "\nPlease try too manage your ingredients and amount again!"
                );
            }
        }
    }


    /**
     * The start point of the program
     * @param foods that will be served to persons in the town owner
     */
    @Override
    public void start(Stack<Food> foods){
        // Set up foods, clear monitor, and re populations before going
        this.foods = foods;
        monitor.setText("");
        town.rePopulations();

        // Start food shop until no people or foods left
        timer = new Timer(200, this);
        timer.start();
    }

    /**
     * The main action to be run repeatedly when start button of the gui is clicked
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // serve new food when foods stack is not empty and town is crowed
        if (!foods.isEmpty() && town.isCrowed()){
            // Get a Customer who is a person from the town then let he or she eats the food from the foods stack
            Customer customer = town.getPerson();
            customer.eat(foods.pop());

            // Count customer is feeling and increase or decrease populations with current customer's feeling
            switch (customer.getFeeling()){
                case HAPPY, GOOD -> {
                    town.increasePopulation();
                    good++;
                }
                case INDIFFERENT -> normal ++;
                case BAD, TERRIBLE -> {
                    town.decreasePopulation();
                    bad ++;
                }
            }

            // Monitor current customer who is eating about name, feeling, and money paying
            monitor.append(customer.getName() + " is eating....\n");
            monitor.append(customer.getName() +
                    " is feel " + customer.getFeeling() +
                    " he/she pay + $" + customer.pay(this) +
                    "\n\n"
            );

        // no foods left or no person in the town
        } else {
            // Show report of our shop for today and enable all button
            showReports(); // Reports
            startButton.setBackground(Color.GREEN);
            startButton.setEnabled(true);
            frame.getPlusButton().values().forEach((b) -> b.setEnabled(true));
            frame.getMinusButton().values().forEach((b) -> b.setEnabled(true));

            // Stop the action after that
            timer.stop();
        }
    }

    /**
     * The method for show the shop's report when serve all food or no people left in the town
     * This includes : foods left, person left, feelings of customers, and income.
     */
    public void showReports(){
        // Show the reports of the shop
        monitor.append("\nThere are " + getFoodLeft().size() + " dishes\nof chaohm omelette left.");
        monitor.append("\nIn " + town.getName());
        monitor.append("\n" + (!town.isCrowed() ? "Every people have eaten.": "There is some people is hungry!"));
        monitor.append("\n\nCustomer's feeling for today.\n");
        monitor.append("\nCustomer feel positive : " + good);
        monitor.append("\nCustomer fell normal   : " + normal);
        monitor.append("\nCustomer fell negative : " + bad);

        // Check money and set new amount to money label
        int oldMoney = Integer.parseInt(moneyLabel.getText());
        int different = money - oldMoney;
        moneyLabel.setText(String.valueOf(money));
        monitor.append("\n\nYou give $" + different + " this turn.");
    }


    /**
     * Main fo the gui program that will be displayed
     */
    public static void main(String[] args) {
        ProcessUI ui = new ProcessUI();
        EventQueue.invokeLater(() -> ui.frame.setVisible(true));
    }
}
