package com.example.yevgeniyshatrovskiy.crashcourse2018.Objects;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by yevgeniyshatrovskiy on 3/15/18.
 */
@IgnoreExtraProperties
public class Person {

    public String firstName;
    public String lastName;
    public int age;

    public Person(){

    }

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

