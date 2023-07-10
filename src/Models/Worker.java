package Models;

import java.util.Objects;
import java.util.Random;

public class Worker extends DBElem{
    private final int UID;
    private String name;
    private String lastName;
    private int age;
    private int accessLevel;
    private String position;
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return UID == worker.UID && age == worker.age && accessLevel == worker.accessLevel && Objects.equals(name, worker.name) && Objects.equals(lastName, worker.lastName) && Objects.equals(position, worker.position) && Objects.equals(phoneNumber, worker.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UID, name, lastName, age, accessLevel, position, phoneNumber);
    }

    public Worker(String name, String lastName, int age, int accessLevel, String position, String phoneNumber) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.accessLevel = accessLevel;
        this.position = position;
        this.phoneNumber = phoneNumber;
        UID = new Random().nextInt(0, 99999999);
    }

    public Worker(int UID, String name, String lastName, int age, int accessLevel, String position, String phoneNumber) {
        this.UID = UID;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.accessLevel = accessLevel;
        this.position = position;
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getUID() {
        return UID;
    }

    public int getAge() {
        return age;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public String getPosition() {
        return position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
