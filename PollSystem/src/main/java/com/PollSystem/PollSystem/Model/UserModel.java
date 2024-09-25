package com.PollSystem.PollSystem.Model;

import java.time.LocalDateTime;


public class UserModel {
    
    private long id;
    
    private String firstName;
    
    private String lastName;

    private String email;

    private int age;

    private String address;

    private LocalDateTime startDate;
    
    // private static long idGenerator =0;


	public UserModel(String firstName2, String lastName2, String email2, int age2, String address, long id) {
		this.firstName = firstName2;
        this.lastName = lastName2;
        this.email = email2;
        this.age = age2;
        this.address = address;
        this.id = id; 
        startDate = LocalDateTime.now();
	}

    public UserModel() {
        
    }

    public Long getId() {
       return id;
    }

    public void update(String firstName2, String lastName2, String email2, int age2, String address) {
        this.firstName = firstName2==null? firstName : firstName2;
        this.lastName = lastName2==null? lastName : lastName2;
        this.email = email2==null? email : email2;
        this.age = age2==0? age : age2;
        this.address = address==null? address : address;
        
    }
    

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setId(long long1) {
        id = long1;
    }

    public void setFirstName(String string) {
        firstName = string;
    }

    public void setLastName(String string) {
        lastName = string;
    }

    public void setEmail(String string) {
       email= string;
    }

    public void setAge(int int1) {
        age = int1;
    }

    public void setAddress(String string) {
        address=string;
    }

    public void setStartDate(LocalDateTime localDateTime) {
        startDate = localDateTime;
    }
}
