package app.main.people;


import app.main.people.personal.SweetLover;

/**
 * The Kid who is a SweetLover
 */
public class Kid extends Person {
    public Kid(String name, int age) {
        super(name, age, new SweetLover());
    }
}
