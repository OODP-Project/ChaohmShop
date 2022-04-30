package app.main.ui;

import app.main.food.ingredient.Chaohm;
import app.main.food.ingredient.Oil;
import app.main.food.ingredient.Salt;
import app.main.food.ingredient.Sugar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashMap;

public class ChaohmFrame extends JFrame {

    private final String[] ingredientName = new String[]{"oil", "chaohm", "salt", "sugar"};
    private final int[] ingredientCost = new int[]{new Oil().getPrice(), new Chaohm().getPrice(), new Salt().getPrice(), new Sugar().getPrice()};
    private final HashMap<String, Integer> ingredientsAmount = new HashMap<>();
    private final HashMap<String, JButton> minusButton = new HashMap<>();
    private final HashMap<String, JButton> plusButton = new HashMap<>();
    private JButton startButton;
    private JTextArea shopMonitor;
    private JLabel moneyLabel;
    private JLabel costLabel;
    private JLabel sellPriceLabel;
    private JLabel sellAmountLabel;
    private JLabel sumCostLabel;

    public ChaohmFrame(){
        // Initialize important details of the frame
        setTitle("ChaohmShop");
        ImageIcon icon = new ImageIcon("omelette.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Exit when close window
        setSize(800, 500);
        setPreferredSize(getSize()); // Frame is the same size when pack
        setLocationRelativeTo(null); // Display the frame at center of the screen
        setBackground(Color.BLACK);
        setLayout(null);
        setResizable(false);
        initComponents();
        Arrays.stream(ingredientName).forEach((name) -> ingredientsAmount.put(name, 1));
    }

    public void initComponents(){
        int h = getHeight()/5 -30;
        Font font = new Font("DialogInput",  Font.BOLD, h/4);

        for (int i = 0; i < 4; i++) {

            JLabel label = new JLabel("$" + ingredientCost[i]);
            label.setFont(font);
            this.add(label);
            label.setBounds(40+h, getHeight()*(i+1)/6 - h/2 - 25, 50, 30);

            JLabel amount = new JLabel("1");
            JButton down = new JButton("-");
            JButton up = new JButton("+");

            JPanel panel = new JPanel();
            panel.add(down);
            panel.add(amount);
            panel.add(up);

            up.setFont(font);
            up.setBackground(Color.WHITE);
            up.setAction(new IncreaseAmountAction(amount));

            down.setFont(font);
            down.setBackground(Color.WHITE);
            down.setAction(new DecreaseAmountAction(amount));

            amount.setFont(font);
            amount.setName(ingredientName[i]);

            this.add(panel);

            panel.setBounds(120, getHeight()*(i+1)/ 6 - h / 2, h * 2, h - 10);

            String key = ingredientName[i];
            minusButton.put(key, down);
            plusButton.put(key, up);
        }

        // start button designs
        startButton = new JButton("Start");
        this.add(startButton);
        startButton.setBounds(getWidth()/2, getHeight() - 80, 200, 40);
        startButton.setFont(font);
        startButton.setBackground(Color.GREEN);

        // Report panel designs
        JPanel reportingPanel = new JPanel();
        this.add(reportingPanel);
        reportingPanel.setLayout(new BorderLayout());
        reportingPanel.setBackground(Color.BLACK);
        reportingPanel.setBounds(getWidth()/2 - 100, getHeight()/2, 400, 150);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
        reportingPanel.add(scrollPane, BorderLayout.CENTER);

        shopMonitor = new JTextArea();
        shopMonitor.setBackground(Color.BLACK);
        shopMonitor.setForeground(Color.WHITE);
        shopMonitor.setBorder(new EmptyBorder(20, 20, 20, 20));
        shopMonitor.setFont(new Font(font.getName(), Font.BOLD, h/5));
        shopMonitor.setEditable(false);
        shopMonitor.setAutoscrolls(true);
        scrollPane.setViewportView(shopMonitor);

        // Money Panel
        JPanel moneyPanel = new JPanel();
        this.add(moneyPanel);
        moneyPanel.setLayout(new GridLayout(1, 2));
        moneyPanel.setBounds(50, getHeight() - 100, 350, 50);

        JLabel moneyTitle = new JLabel("money : $");
        moneyPanel.add(moneyTitle);
        moneyTitle.setFont(font);

        moneyLabel = new JLabel("1000");
        moneyPanel.add(moneyLabel);
        moneyLabel.setFont(font);

        int baseCost = 0;
        for (int cost : ingredientCost){
            baseCost += cost;
        }

        // Cost panel
        JPanel costPanel = new JPanel();
        this.add(costPanel);
        costPanel.setBackground(Color.WHITE);
        costPanel.setBorder(new LineBorder(Color.BLACK));
        costPanel.setLayout(new GridLayout(1, 2));
        costPanel.setBounds(getWidth()/2 -100, getHeight()/10 - h/2 ,200, 30);

        JLabel costTitle  = new JLabel("Cost : $");
        costPanel.add(costTitle);
        costTitle.setFont(font);

        costLabel = new JLabel(String.valueOf(baseCost));
        costPanel.add(costLabel);
        costLabel.setFont(font);

        // Sell amount panel
        JPanel amountPanel = new JPanel();
        this.add(amountPanel);
        amountPanel.setBounds(getWidth()/2 -100, getHeight()*2/10 - h/2, 200, 60);
        amountPanel.setBorder(new LineBorder(Color.BLACK));
        amountPanel.setBackground(Color.WHITE);
        amountPanel.setLayout(new GridLayout(2, 2));

        JLabel amountTitle = new JLabel("Amount :");
        amountPanel.add(amountTitle);
        amountTitle.setFont(font);

        sellAmountLabel = new JLabel("100");
        amountPanel.add(sellAmountLabel);
        sellAmountLabel.setFont(font);

        JButton amountDown = new JButton("-");
        amountPanel.add(amountDown);
        amountDown.setFont(font);
        amountDown.setBackground(Color.yellow);

        JButton amountUp = new JButton("+");
        amountPanel.add(amountUp);
        amountUp.setFont(font);
        amountUp.setBackground(Color.GREEN);

        plusButton.put("sellAmount", amountUp);
        minusButton.put("sellAmount", amountDown);

        // Sell price here
        JPanel sellPricePanel = new JPanel();
        this.add(sellPricePanel);
        sellPricePanel.setBackground(Color.WHITE);
        sellPricePanel.setLayout(new GridLayout(2, 2));
        sellPricePanel.setBorder(new LineBorder(Color.BLACK));
        sellPricePanel.setBounds(getWidth()/2 -100, getHeight()*4/11 - h/2, 200, 60);

        JLabel sellPriceTitle = new JLabel("Sell : $");
        sellPricePanel.add(sellPriceTitle);
        sellPriceTitle.setFont(font);

        sellPriceLabel = new JLabel(String.valueOf(baseCost));
        sellPricePanel.add(sellPriceLabel);
        sellPriceLabel.setFont(font);

        JButton sellDown = new JButton("-");
        sellPricePanel.add(sellDown);
        sellDown.setFont(font);
        sellDown.setBackground(Color.YELLOW);
        sellDown.setAction(new DecreaseSellPriceAction());

        JButton sellUp = new JButton("+");
        sellPricePanel.add(sellUp);
        sellUp.setFont(font);
        sellUp.setBackground(Color.GREEN);
        sellUp.setAction(new IncreaseSellPriceAction());

        plusButton.put("sellPrice", sellUp);
        minusButton.put("sellPrice", sellDown);

        // Sum cost panel
        JPanel sumCostPanel = new JPanel();
        this.add(sumCostPanel);
        sumCostPanel.setLayout(new GridLayout(1, 2));
        sumCostPanel.setBackground(Color.WHITE);
        sumCostPanel.setBorder(new LineBorder(Color.BLACK));
        sumCostPanel.setBounds(getWidth()/2 -100, getHeight()/2 - 30, 400, 30);

        JLabel sumCostTitle = new JLabel("Sum cost : $");
        sumCostPanel.add(sumCostTitle);
        sumCostTitle.setFont(font);

        sumCostLabel = new JLabel("0");
        sumCostPanel.add(sumCostLabel);
        sumCostLabel.setFont(font);

        amountDown.setAction(new DecreaseSellAmountAction());
        amountUp.setAction(new IncreaseSellAmountAction());
    }

    public HashMap<String, Integer> getIngredientsAmount(){
        return ingredientsAmount;
    }

    public HashMap<String, JButton> getMinusButton() {
        return minusButton;
    }

    public HashMap<String, JButton> getPlusButton() {
        return plusButton;
    }

    public int getSellPrice(){
        return Integer.parseInt(sellPriceLabel.getText());
    }

    public int getSellAmount(){
        return Integer.parseInt(sellAmountLabel.getText());
    }
    public int getSellCost() {
        return Integer.parseInt(costLabel.getText());
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JTextArea getShopMonitor(){
        return shopMonitor;
    }

    public JLabel getMoneyLabel() {
        return moneyLabel;
    }

    private class IncreaseAmountAction extends AbstractAction{

        JLabel label;
        public IncreaseAmountAction(JLabel label){
            this.label = label;
            putValue(NAME, "+");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = label.getText();
            int amount = Integer.parseInt(text);
            label.setText(String.valueOf(++amount));
            ingredientsAmount.put(label.getName(), amount);
            int index = Arrays.stream(ingredientName).toList().indexOf(label.getName());
            int cost = Integer.parseInt(costLabel.getText()) + ingredientCost[index];
            costLabel.setText(String.valueOf(cost));

            int sumCost = cost * getSellAmount();
            int money = Integer.parseInt(moneyLabel.getText());

            sumCostLabel.setText(String.valueOf(sumCost));

            if (sumCost > money){
                sumCostLabel.setForeground(Color.RED);
                moneyLabel.setForeground(Color.RED);
            } else {
                sumCostLabel.setForeground(Color.BLACK);
                moneyLabel.setForeground(Color.BLACK);
            }
        }
    }

    private class DecreaseAmountAction extends AbstractAction{

        JLabel label;
        public DecreaseAmountAction(JLabel label){
            this.label = label;
            putValue(NAME, "-");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = label.getText();
            int amount = Integer.parseInt(text);
            if (--amount >= 1){
                label.setText(String.valueOf(amount));
                ingredientsAmount.put(label.getName(), amount);

                int index = Arrays.stream(ingredientName).toList().indexOf(label.getName());
                int cost = Integer.parseInt(costLabel.getText()) - ingredientCost[index];
                costLabel.setText(String.valueOf(cost));

                int sumCost = cost * getSellAmount();
                int money = Integer.parseInt(moneyLabel.getText());

                sumCostLabel.setText(String.valueOf(sumCost));

                if (sumCost > money){
                    sumCostLabel.setForeground(Color.RED);
                    moneyLabel.setForeground(Color.RED);
                } else {
                    sumCostLabel.setForeground(Color.BLACK);
                    moneyLabel.setForeground(Color.BLACK);
                }
            }
        }
    }

    public class IncreaseSellAmountAction extends AbstractAction{
        public IncreaseSellAmountAction(){
            putValue(NAME , "+");
            updateSumCost();
        }

        public void updateSumCost(){
            int sellAmount = Integer.parseInt(sellAmountLabel.getText());
            int sellCost = Integer.parseInt(costLabel.getText());
            int sumCost = sellAmount * sellCost;
            int money = Integer.parseInt(moneyLabel.getText());

            sumCostLabel.setText(String.valueOf(sumCost));

            if (sumCost > money){
                sumCostLabel.setForeground(Color.RED);
                moneyLabel.setForeground(Color.RED);
            } else {
                sumCostLabel.setForeground(Color.BLACK);
                moneyLabel.setForeground(Color.BLACK);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int sellAmount = Integer.parseInt(sellAmountLabel.getText());
            sellAmountLabel.setText(String.valueOf(++sellAmount));
            updateSumCost();
        }
    }

    /**
     * The action for check decrease sell amount button is clicked
     */
    public class DecreaseSellAmountAction extends AbstractAction{
        public DecreaseSellAmountAction(){
            putValue(NAME, "-");
            updateSumCost();
        }

        /**
         * The method for check update sum cost if sell amount is decreased
         */
        public void updateSumCost(){
            int sellAmount = Integer.parseInt(sellAmountLabel.getText());
            int sellCost = Integer.parseInt(costLabel.getText());
            int sumCost = sellCost * sellAmount;
            int money = Integer.parseInt(moneyLabel.getText());

            sumCostLabel.setText(String.valueOf(sumCost));

            // Change sum
            if (sumCost > money){
                sumCostLabel.setForeground(Color.RED);
                moneyLabel.setForeground(Color.RED);
            } else {
                sumCostLabel.setForeground(Color.BLACK);
                moneyLabel.setForeground(Color.BLACK);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int sellAmount = Integer.parseInt(sellAmountLabel.getText());
            if (--sellAmount >= 1){
                sellAmountLabel.setText(String.valueOf(sellAmount));
                updateSumCost();
            }
        }
    }

    /**
     * The action for increase sell price action
     * Which can interact with sell price label and its value
     */
    private class IncreaseSellPriceAction extends AbstractAction{

        public IncreaseSellPriceAction(){
            putValue(NAME, "+");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int value = Integer.parseInt(sellPriceLabel.getText());
            sellPriceLabel.setText(String.valueOf(++value));
        }
    }

    /**
     * The action for decrease sell price action
     * Which can interact with sell price label and its value
     */
    private class DecreaseSellPriceAction extends AbstractAction{
        public DecreaseSellPriceAction(){
            putValue(NAME, "-");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Get current value of the label and increase it by 1
            int value = Integer.parseInt(sellPriceLabel.getText());
            if (--value >= 1){
                sellPriceLabel.setText(String.valueOf(value));
            }
        }
    }


    /**
     * The overriding method to be paint image of ingredients and shop
     * @param g the specified Graphics window
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        ImageIcon oil = new ImageIcon("oil.png");
        ImageIcon broccoli = new ImageIcon("durva.png");
        ImageIcon salt = new ImageIcon("salt.png");
        ImageIcon sugar = new ImageIcon("sugar.png");
        ImageIcon monitor = new ImageIcon("shop.png");

        // Paint image for any bounds
        int h = getHeight()/5 -30;
        g.drawImage(oil.getImage(), 50, getHeight()/6 - h/2, h, h, null);
        g.drawImage(broccoli.getImage(), 50, getHeight()*2/6 -h/2, h, h, null);
        g.drawImage(salt.getImage(), 50, getHeight()*3/6 -h/2, h, h, null);
        g.drawImage(sugar.getImage(), 50, getHeight()*4/6 -h/2,h, h, null);

        g.drawImage(monitor.getImage(), getWidth()/2 + 100, getHeight()/6- h/2, 200, 200, null);
    }
}
