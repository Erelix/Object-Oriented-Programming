package com.brains.dataimport;

import java.time.LocalDate;
import java.util.Date;

public class User {
    int id;
    String name;
    String surname;
    String email;
    String gender;
    String country;
    String domain;
    LocalDate birthDate;

    String extra;

    public User(int id, String name, String surname, String email, String gender, String country, String domain, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.gender = gender;
        this.country = country;
        this.domain = domain;
        this.birthDate = birthDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setExtraCol(String extraCol) {
        this.extra = extraCol;
        //System.out.println(extra);
    }

    public String getExtraCol() {
        return extra;
    }
}
