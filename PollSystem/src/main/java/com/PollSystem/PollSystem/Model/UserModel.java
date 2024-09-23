package com.PollSystem.PollSystem.Model;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class UserModel {
    
    private long id;
    
    private String firstName;
    
    private String lastName;

    private String email;

    private int age;

    private String adress;

    private LocalDateTime startDate;
    
    private static long idGenerator =0;

    public UserModel(){
        id = idGenerator++;

        startDate = LocalDateTime.now();
    }
    
}
