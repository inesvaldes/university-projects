package model;

public class Customer {

    private String name;
    private String surname;
    private String id;

    public Customer(String name, String surname, String id) {
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getId() { return id; }
}

