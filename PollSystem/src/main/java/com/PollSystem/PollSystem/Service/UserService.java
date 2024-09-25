package com.PollSystem.PollSystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PollSystem.PollSystem.Model.UserModel;
import com.PollSystem.PollSystem.Repos.UserRepoImpl;

import java.util.*;

@Service
public class UserService {
    
    private Map<Long,UserModel> users = new HashMap<>();

    // @Autowired
    private UserRepoImpl userRepo;

    private long userIdCounter = 0;

    @Autowired
    public UserService(UserRepoImpl userRepo){
        this.userRepo = userRepo;
        List<UserModel> userList = userRepo.findAll();
        for(UserModel user: userList){
            userIdCounter = Math.max(userIdCounter, user.getId());
            users.put(user.getId(), user);
        }
    }


    public UserModel register(String firstName, String lastName, String email, int age,String address){
        UserModel user = new UserModel(firstName, lastName, email, age, address, userIdCounter);
        userIdCounter++;
        users.put(user.getId(), user);
        userRepo.save(user);
        return user;
    }


    public void removeUser(long userId){
      checkUserExists(userId);

        users.remove(userId);
        userRepo.delete(userId);

    }


    public UserModel getUserById(long userId) {
        checkUserExists(userId);
        return users.get(userId);
    }


    public UserModel updateUser(long userId, String firstName, String lastName, String email, int age, String address) {
       checkUserExists(userId);
       UserModel user = users.get(userId);
       user.update(firstName, lastName, email, age, address);
       userRepo.update(user);
       return user;
    }


    public void checkUserExists(long userId) {
        if(!users.containsKey(userId)){
            throw new IllegalArgumentException("User not found");
        }
    }

}
