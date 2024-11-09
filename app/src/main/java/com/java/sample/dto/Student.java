package com.java.sample.dto;


public class Student {

    private Integer id;
    private String fullName;
    private Integer birthYear;
    private String address;

    public Student(Integer id, String fullName, Integer birthYear, String address) {
        this.id = id;
        this.fullName = fullName;
        this.birthYear = birthYear;
        this.address = address;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

}
