package com.PollSystem.PollSystem.Repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import com.PollSystem.PollSystem.Model.UserModel;

@Repository
public class UserRepoImpl implements UserRepo {
    private final String TABLE_NAME = "users";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper to map ResultSet to UserModel object
    private final RowMapper<UserModel> userRowMapper = new RowMapper<UserModel>() {
   
        @Override
        public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserModel user = new UserModel();
            user.setId(rs.getLong("id"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setEmail(rs.getString("email"));
            user.setAge(rs.getInt("age"));
            user.setAddress(rs.getString("address"));
            user.setStartDate(rs.getTimestamp("startDate").toLocalDateTime());
            return user;
        }
    };

    
    public UserModel save(UserModel user) {
        String sql = "INSERT INTO " + TABLE_NAME + " (firstName, lastName, email, age, address, startDate) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmail(), user.getAge(), user.getAddress(), LocalDateTime.now());
        return user; // Optionally return the saved user or its ID
    }

    // @Override
    public UserModel findById(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, id);
    }

    // @Override
    public List<UserModel> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return jdbcTemplate.query(sql, userRowMapper);
    }

    // @Override
    public void update(UserModel user) {
        String sql = "UPDATE " + TABLE_NAME + " SET firstName = ?, lastName = ?, email = ?, age = ?, address = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmail(), user.getAge(), user.getAddress(), user.getId());
    }

    // @Override
    public void delete(Long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}