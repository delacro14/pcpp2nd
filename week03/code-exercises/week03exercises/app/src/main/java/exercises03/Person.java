package exercises03;

public class Person {
    //id (long), name (String), zip (int) and address
    //(String)
    private long id;
    private String name;
    private int zip;
    private String address;

    public Person() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZip() {
        return zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address, int zip) {
        this.address = address;
        this.zip = zip;
    }
}
