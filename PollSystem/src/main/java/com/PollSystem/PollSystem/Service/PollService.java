package com.PollSystem.PollSystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PollSystem.PollSystem.Model.PollModel;
import com.PollSystem.PollSystem.Model.PollToSend;
import com.PollSystem.PollSystem.Repos.AnswerUsers;
import com.PollSystem.PollSystem.Repos.PollRepo;

import java.util.*;

@Service
public class PollService {

    private Map<Long, PollModel> polls = new HashMap<>();

    // @Autowired
    private PollRepo pollRepo;

    // @Autowired
    private AnswerUsers answerUsers;
    
    @Autowired
    public PollService(PollRepo pollRepo, AnswerUsers answerUsers) {
        this.pollRepo = pollRepo;
        this.answerUsers = answerUsers;
        
        List<PollModel> pollsFromDb = new ArrayList<>();
        pollsFromDb = pollRepo.findAll();
        for (PollModel poll : pollsFromDb){
            polls.put(poll.getId(), poll);
            PollModel.idGenerator = Math.max(poll.getId(), PollModel.idGenerator);
        }

    }

    private void checkPollId(long pollId) {
        if (!polls.containsKey(pollId)) {
            throw new IllegalArgumentException("Poll with id " + pollId + " does not exist");
        }
    }

    public void removeUserAnswers(long userId) {
        for (PollModel poll : polls.values())
            poll.removeUserAnswers(userId);
        answerUsers.deleteUser(userId);
    }

    public PollModel createPoll(String title, String[] answers) {
        PollModel poll = new PollModel(title, answers);

        polls.put(poll.getId(), poll);
        pollRepo.save(poll);
        return poll;
    }

    public PollModel getPollById(long pollId) {
        checkPollId(pollId);
        return polls.get(pollId);
    }

    public void removePoll(long pollId) {
        checkPollId(pollId);
        polls.remove(pollId);
        pollRepo.deleteById(pollId);
        answerUsers.deletePoll(pollId);
    }

    public PollModel updatePoll(long pollId, String title, String[] answers) {
        checkPollId(pollId);
        PollModel poll = polls.get(pollId);
        poll.setProps(title, answers);
        pollRepo.update(poll);
        return poll;
    }

    public void vote(long pollId, long userId, int answerIndex) {
        checkPollId(pollId);
        PollModel poll = polls.get(pollId);
        poll.vote(userId, answerIndex);
        answerUsers.insertAnswer(pollId, userId, answerIndex);
    }

    public PollToSend[] getPollsInSystemToVote() {
        PollToSend[] pollsToSend = new PollToSend[polls.size()];
        int i = 0; // index for pollsToSend array
        for (PollModel poll : polls.values()) {
            pollsToSend[i] = new PollToSend(poll);
            i++;
        }
        return pollsToSend;

    }

    public PollModel[] getAllPolls() {
        PollModel[] pollsArray = new PollModel[polls.size()];
        int i = 0; // index for pollsArray array
        for (PollModel poll : polls.values()) {
            pollsArray[i] = poll;
            i++;
        }
        return pollsArray;
    }

    public int getUserNumberOfPolls(long userId) {
        return polls.values().size();
    }

}
