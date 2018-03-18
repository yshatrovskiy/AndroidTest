package com.example.yevgeniyshatrovskiy.crashcourse2018;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by yevgeniyshatrovskiy on 3/15/18.
 */
@IgnoreExtraProperties
public class Person {

    public String firstName;
    public String lastName;

    public Person(){

    }

    public Person(String first, String last){

        this.firstName = first;
        this.lastName = last;

    }

}

