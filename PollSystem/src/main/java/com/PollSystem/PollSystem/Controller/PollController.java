package com.PollSystem.PollSystem.Controller;

import com.PollSystem.PollSystem.Model.PollModel;
import com.PollSystem.PollSystem.Model.PollToSend;
import com.PollSystem.PollSystem.Service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private AppService appService;

    @PostMapping
    public Response createPoll(@RequestParam(name = "title") String title,
            @RequestParam(name = "answers") String[] answers) {
        Response res;
        try {
            PollModel poll = appService.createPoll(title, answers);
            res = new Response(poll, null);
        } catch (Exception e) {
            res = new Response(400, e.getMessage());
        }
        return res;
    }

    @GetMapping("/{pollId}")
    public Response getPollById(@PathVariable(name = "pollId") long pollId) {
        Response res;
        try {
            PollModel poll = appService.getPollById(pollId);
            res = new Response(poll, null);
        } catch (Exception e) {
            res = new Response(404, "Poll not found");
        }
        return res;
    }

    @DeleteMapping("/{pollId}")
    public Response removePoll(@PathVariable(name = "pollId") long pollId) {
        Response res;
        try {
            appService.removePoll(pollId);
            res = new Response(200, "Poll removed successfully");
        } catch (Exception e) {
            res = new Response(400, "Failed to remove poll");
        }
        return res;
    }

    @PutMapping("/{pollId}")
    public Response updatePoll(@PathVariable(name = "pollId") long pollId,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "answers", required = false) String[] answers) {
        Response res;
        try {
            PollModel updatedPoll = appService.updatePoll(pollId, title, answers);
            res = new Response(updatedPoll, null);
        } catch (Exception e) {
            res = new Response(400, "Failed to update poll");
        }
        return res;
    }

    @PostMapping("/{pollId}/vote")
    public Response vote(@PathVariable(name = "pollId") long pollId,
            @RequestParam(name = "userId") long userId,
            @RequestParam(name = "answerIndex") int answerIndex) {
        Response res;
        try {
            appService.vote(pollId, userId, answerIndex);
            res = new Response(200, "Vote recorded successfully");
        } catch (Exception e) {
            res = new Response(400, "Failed to record vote");
        }
        return res;
    }

    @GetMapping("/{userId}/available")
    public Response getPollsInSystemToVote(@PathVariable(name = "userId") long userId) {
        Response res;
        try {
            PollToSend[] polls = appService.getPollsInSystemToVote(userId);
            res = new Response(polls, null);
        } catch (Exception e) {
            res = new Response(404, "No polls available for voting");
        }
        return res;
    }

    @GetMapping
    public Response getAllPolls() {
        Response res;
        try {
            PollModel[] polls = appService.getAllPolls();
            res = new Response(polls, null);
        } catch (Exception e) {
            res = new Response(500, "Failed to retrieve polls");
        }
        return res;
    }

    @GetMapping("/{pollId}/votes/count")
    public Response getNumberOfVotesForPoll(@PathVariable(name = "pollId") long pollId) {
        Response res;
        try {
            int voteCount = appService.getNumberOfVotesForPoll(pollId);
            res = new Response(voteCount, null);
        } catch (Exception e) {
            res = new Response(404, "Poll not found");
        }
        return res;
    }

    @GetMapping("/{pollId}/results")
    public Response getPollResults(@PathVariable(name = "pollId") long pollId) {
        Response res;
        try {
            Map<Integer, Integer> results = appService.getPollResults(pollId);
            res = new Response(results, null);
        } catch (Exception e) {
            res = new Response(404, "Poll not found");
        }
        return res;
    }
}
