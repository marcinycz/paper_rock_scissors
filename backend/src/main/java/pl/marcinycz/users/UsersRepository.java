package pl.marcinycz.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.marcinycz.paper_rock_scissors.PaperRockScissors;

import java.util.List;

@Repository
public class UsersRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getAll(){
        return jdbcTemplate.query("SELECT id, username FROM users",
                BeanPropertyRowMapper.newInstance(User.class));
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
