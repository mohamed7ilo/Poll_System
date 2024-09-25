package com.PollSystem.PollSystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PollSystem.PollSystem.Model.PollModel;
import com.PollSystem.PollSystem.Model.PollToSend;
import com.PollSystem.PollSystem.Model.UserModel;
import java.util.*;
@Service
public class AppService {
    
    @Autowired
    private PollService pollService;
    

    @Autowired
    private UserService userService;


    
    public AppService(){}


    public UserModel registerUser(String firstName, String lastName, String email, int age, String address){
        return userService.register(firstName, lastName, email, age,address);
    }

    public void removeUser(long userId){
        userService.removeUser(userId);
        pollService.removeUserAnswers(userId);
    }

    public UserModel getUserById(long userId){
        return userService.getUserById(userId);
    }

    public UserModel updateUser(long userId, String firstName, String lastName, String email, int age, String address){
        return userService.updateUser(userId, firstName, lastName, email, age, address);
    }
    

    public PollModel createPoll(String title ,String[] answers){
        return pollService.createPoll(title, answers);
    }
    

    public PollModel getPollById(long pollId){
        return pollService.getPollById(pollId);
    }

    public void removePoll(long pollId){
        pollService.removePoll(pollId);
    }

    public PollModel updatePoll(long pollId, String title,  String[] answers){
        return pollService.updatePoll(pollId, title, answers);
    }

    public void vote(long pollId, long userId, int answerIndex){
        userService.checkUserExists(userId);
        
        pollService.vote(pollId, userId, answerIndex);
    }


    public PollToSend[] getPollsInSystemToVote(long userId){
        userService.checkUserExists(userId);
        return pollService.getPollsInSystemToVote();
    }
    
    public PollModel[] getAllPolls(){
        return pollService.getAllPolls();
    }


    public int getNumberOfVotesForPoll(long pollId){
        return pollService.getPollById(pollId).getNumberOfVotes();
    }
    
    public Map<Integer,Integer> getPollResults(long pollId){
        PollModel poll = pollService.getPollById(pollId);
        Map<Integer,Integer> results = new HashMap<>();
        for(int i=0; i<poll.getAnswers().length; i++){
            results.put(i+1, poll.getAnswers()[i].getVoters().size());
        }
        return results;
    }

    public int getUserNumberOfPolls(long userId){
        userService.checkUserExists(userId);
        return pollService.getUserNumberOfPolls(userId);
    }






}
