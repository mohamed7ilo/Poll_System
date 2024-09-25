package com.PollSystem.PollSystem.Model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.PollSystem.PollSystem.Repos.AnswerUsers;

import java.util.*;

public class AnswerModel {

    private String answerTxt;

    @Autowired
    private static AnswerUsers answerUsers;

    private List<Long> voters = new ArrayList<>();

    public AnswerModel(String string) {
        answerTxt = string;
    }

    public AnswerModel() {
    }

    public static AnswerModel initAnswerModel(String answerTxt,int index,long pollId) {
        AnswerModel answerModel = new AnswerModel(answerTxt);
        List<Long> voters = new ArrayList<>();
        voters = answerUsers.getVoters(pollId, index);
        answerModel.setVoters(voters);
        return answerModel;
    }


    private void setVoters(List<Long> voters2) {
       voters   = voters2;
    }

    public List<Long> getVoters() {
        return voters;
    }

    public void removeUser(long userId) {
        if (voters.contains(userId))
            voters.remove(userId);
    }

    public void vote(long userId) {
        voters.add(userId);
    }

    public void setTxt(String string) {
        answerTxt = string == null ? answerTxt : string;
    }

    public boolean isVoted(long userId) {
        return voters.contains(userId);
    }

    public String getTxt() {
        return answerTxt;
    }
}
