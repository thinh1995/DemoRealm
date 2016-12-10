package com.example.kenguyen.realmigration.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Required;

public class Person extends RealmObject {
    @Required
    private String fullName;
    private String email;
    private int age;

    public Person(String fullName, String email, int age) {
        this.fullName = fullName;
        this.email = email;
        this.age = age;
    }

    public Person() {

    }
}