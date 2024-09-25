package com.PollSystem.PollSystem.Repos;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.PollSystem.PollSystem.Model.UserAnswer;

@Repository
public class AnswerUsers {

    private final String TABLE_NAME = "answers";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper rowMapper = (rs, rowNum) -> {
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.pollId = rs.getLong("pollId");
        userAnswer.userId = rs.getLong("userId");
        userAnswer.answerIndex = rs.getInt("answerIndex");
        return userAnswer;
    };


    public List<Long> getVoters(long pollId, int index) {
        String sql = "SELECT userId FROM answers WHERE pollId = ? AND answerIndex = ?";
        
        List<UserAnswer> usersForAnswer =  jdbcTemplate.query(sql, rowMapper, pollId, index);
        List<Long> votersToRet = new ArrayList<>();
        for (UserAnswer userAnswer : usersForAnswer) {
            votersToRet.add(userAnswer.userId);
        }    
        return votersToRet;
    }

    public void insertAnswer(long pollId, long userId, int answerIndex) {
        String sql = "INSERT INTO " + TABLE_NAME + " (pollId, userId, answerIndex) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, pollId, userId, answerIndex);
    }

    public void deleteUser( long userId) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE userId = ?";
        jdbcTemplate.update(sql, userId);
    }

    public void deletePoll(long pollId) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE pollId = ?";
        jdbcTemplate.update(sql, pollId);
    }
    
}
