package app.main.people;

import app.main.people.personal.LanLover;

/**
 * The Elder whp is a LanLover
 */
public class Elder extends Person {
    public Elder(String name, int age) {
        super(name, age, new LanLover());
    }
}
