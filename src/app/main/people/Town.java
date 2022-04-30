package app.main.people;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * The town that the shop will be placed on
 */
public class Town {

    // Necessary fields here
    private int populationsCount;
    private final String name;
    private final Deque<Person> tempPopulations;
    private final ArrayList<Person> populations;

    public Town(String name){
        // init fields
        this.name = name;
        this.populations = new ArrayList<>();
        this.tempPopulations = new ArrayDeque<>();

        // Get all person's names in the file and create instances with that person's name
        // With this person will be Kid, Adult, or Elder from random age
        try (BufferedReader reader = new BufferedReader(new FileReader("data/persons.dat"))){
            reader.lines().forEach( (line) -> {
                Person person;
                int age = (int) (Math.random() * 70 + 1);
                if (age < 20){ // Kid's age less than 20
                    person = new Kid(line, age);
                } else if (age < 50){
                    person = new Adult(line, age);
                } else {
                    person = new Elder(line, age);
                }
                populations.add(person);
            });

        } catch (Exception e){
            // If any exception is thrown just try to throw RuntimeException
            throw new RuntimeException(e);
        }

        // After load all people in file completed just re populations to temp populations
        rePopulations();
    }

    public String getName(){
        return name;
    }

    /**
     * Decrease populations in the town two persons
     */
    public void decreasePopulation(){
        // Try to remove person from temp populations
        try {
            tempPopulations.removeLast();
            tempPopulations.removeLast();
        } catch (NoSuchElementException ignored) {}
        // Just ignored because delete more than one person this exception will be thrown
    }

    /**
     * Increase populations in the town two persons
     */
    public void increasePopulation(){
        try {
            tempPopulations.add(populations.get(++populationsCount));
            tempPopulations.add(populations.get(++populationsCount));
        } catch (IndexOutOfBoundsException ignored) {}
        // Just ignored because no more persons in the town to be increased
    }

    /**
     * Re populations in the town
     * sometimes some people is not always go outside or not always at the town
     * we just random person in the town to simulate the situations in the real word
     */
    public void rePopulations(){
        // Random person from 20 to 200 persons from all populations
        populationsCount = (int) (Math.random() * (200) + 60);
        for (int i = 0; i < populationsCount; i++){
            tempPopulations.add(populations.get(i));
        }
    }

    public Person getPerson(){
        return tempPopulations.pop();
    }

    /**
     * check is there some persons out there
     * @return boolean
     */
    public boolean isCrowed() {
        return !tempPopulations.isEmpty();
    }

}
