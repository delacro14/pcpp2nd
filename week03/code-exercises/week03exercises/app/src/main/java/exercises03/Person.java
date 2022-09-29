package exercises03;


public class Person {
    private static int idCounter = -1;
    private final long id;
    private String name;
    private int zip;
    private String address;

    /*public Person() {
        this.id = idCounter.get();
        idCounter.incrementAndGet();
    }

    public Person(int idCounter){
        if (Person.idCounter.get() == 0) {
            Person.idCounter.getAndSet(idCounter);
        } 
        this.id = Person.idCounter.get();
        Person.idCounter.incrementAndGet();

    } */

    public Person() {
        synchronized (Person.class) {
            synchronized (this) {
                idCounter++;
                id = idCounter;
            }
        }
    }

    public Person(int idCounter) {
        synchronized (Person.class) {
            synchronized (this) {
                if (Person.idCounter == -1) {
                    Person.idCounter = idCounter;
                } 
                this.id = Person.idCounter;
                Person.idCounter++;
            }
        }
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
