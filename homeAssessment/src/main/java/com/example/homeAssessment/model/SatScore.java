package com.example.homeAssessment.model;


import jakarta.persistence.*;

@Entity
@Table(name = "sat_scores",uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class SatScore {

    // Create model view 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique=true)
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "pincode")
    private Integer pincode;

    @Column(name = "satScore")
    private Integer satScore;

    @Column(name = "result")
    private String result;

    // Creating Getters and Setters

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public Integer getPincode() {
        return pincode;
    }
    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }
    public Integer getSatScore() {
        return satScore;
    }
    public void setSatScore(Integer satScore) {
        this.satScore = satScore;
    }
    public String getResult() {
        return result;
    }
    public void setSatScore(String result) {
        this.result = result;
    }


}

