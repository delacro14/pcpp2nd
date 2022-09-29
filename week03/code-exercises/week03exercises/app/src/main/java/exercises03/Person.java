package exercises03;

import javax.annotation.concurrent.GuardedBy;

public class Person {
    @GuardedBy("this") private static int idCounter = 0;
    private final long id;
    private String name;
    private int zip;
    private String address;

    private Person() {
        this.id = idCounter;
        idCounter++;
    }

    public Person initializePerson() {
        Person person = new Person();
        return person;
    }

    public synchronized long getId() {
        return id;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized int getZip() {
        return zip;
    }

    public synchronized String getAddress() {
        return address;
    }

    public synchronized void setAddress(String address, int zip) {
        this.address = address;
        this.zip = zip;
    }
}
