package pl.marcinycz.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsersRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getAll(){
        return jdbcTemplate.query("SELECT id, username FROM users",
                BeanPropertyRowMapper.newInstance(User.class));
    }

    public List<User> getLast(){
        return jdbcTemplate.query("SELECT id, username FROM users ORDER BY id DESC LIMIT 0,1",
                BeanPropertyRowMapper.newInstance(User.class));
    }

    public List<User> getByName(String username){
        return jdbcTemplate.query("SELECT id, username FROM users WHERE username = ?",
                BeanPropertyRowMapper.newInstance(User.class), username);
    }

    public List<User> getById(int id){
        return jdbcTemplate.query("SELECT id, username FROM users WHERE id = ?",
                BeanPropertyRowMapper.newInstance(User.class), id);
    }

    public void save(User user){
        jdbcTemplate.update("INSERT INTO users(id, username) VALUES(?, ?)",
                user.getId(), user.getUsername()
        );
    }

    public void deleteAll(){
        jdbcTemplate.update("DELETE FROM users");
    }
}
