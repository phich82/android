package com.java.sample.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serial;
import java.io.Serializable;


//@JsonNaming(SnakeCaseStrategy.class)
public class Student implements Serializable {
    @Serial
    private static final Long serialVersionUID = 1L;

    //@JsonProperty("id")
    private Integer id;
    //@JsonProperty("full_name")
    private String fullName;
    //@JsonProperty("birth_year")
    private Integer birthYear;
    //@JsonProperty("address")
    private String address;

    public Student(Integer id,
                   String fullName,
                   Integer birthYear,
                   String address) {
//    public Student(@JsonProperty("id") Integer id,
//                   @JsonProperty("full_name") String fullName,
//                   @JsonProperty("birth_year") Integer birthYear,
//                   @JsonProperty("address") String address) {
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
