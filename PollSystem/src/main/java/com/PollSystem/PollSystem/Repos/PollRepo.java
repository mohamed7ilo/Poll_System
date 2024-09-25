package com.PollSystem.PollSystem.Repos;

import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.PollSystem.PollSystem.Model.AnswerModel;
import com.PollSystem.PollSystem.Model.PollModel;
import com.PollSystem.PollSystem.Model.UserModel;

@Repository
public class PollRepo{
    private final String TABLE_NAME = "polls";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public PollRepo() {}

    private final RowMapper<PollModel> pollRowMapper = new RowMapper<PollModel>() {

        @Override
        public PollModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            PollModel poll = new PollModel();

            poll.setId(rs.getLong("id"));
            poll.setTitle(rs.getString("title"));
            AnswerModel[] answers = new AnswerModel[4];
            for (int i=1;i<5;i++) {
                String answerTxt = rs.getString("answer"+i);
                answers[i-1] = AnswerModel.initAnswerModel(answerTxt, i-1, poll.getId());
            }
            poll.setAnswers(answers);
            return poll;
        }
    };


    public void save(PollModel poll) {
        String sql = "INSERT INTO " + TABLE_NAME + " (id,title, answer1, answer2, answer3, answer4) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(sql, poll.getId(), poll.getTitle(), poll.getFirstAnswer(), poll.getSecondAnswer(), poll.getThirdAnswer(), poll.getFourthAnswer());
    }


    public PollModel findById(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id =?";
        return jdbcTemplate.queryForObject(sql, pollRowMapper, id);
    }

    public List<PollModel> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return jdbcTemplate.query(sql, pollRowMapper);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id =?";
        jdbcTemplate.update(sql, id);
    }

    public void update(PollModel poll) {
        String sql = "UPDATE " + TABLE_NAME + " SET title =?, answer1 =?, answer2 =?, answer3 =?, answer4 =? WHERE id =?";
        jdbcTemplate.update(sql, poll.getTitle(), poll.getFirstAnswer(), poll.getSecondAnswer(), poll.getThirdAnswer(), poll.getFourthAnswer(), poll.getId());
    }




}
