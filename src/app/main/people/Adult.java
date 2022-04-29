package app.main.people;

import app.main.people.personal.SaltyLover;

/**
 * The Adult who is a SaltyLover
 */
public class Adult extends Person {

    public Adult(String name, int age) {
        super(name, age, new SaltyLover());
    }
}
