package pl.marcinycz.paper_rock_scissors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaperRockScissorsRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<PaperRockScissors> getLastGame(int length){
        return jdbcTemplate.query("SELECT id, myChoiceInt, idUser, computerChoice, myChoice, score, verdict, totalScore FROM prs ORDER BY id DESC LIMIT 0,?",
                        BeanPropertyRowMapper.newInstance(PaperRockScissors.class), length);
    }

    public List<PaperRockScissors> getLastGameByIdUser(int idUser, int length){
        return jdbcTemplate.query("SELECT id, myChoiceInt, idUser, computerChoice, myChoice, score, verdict, totalScore FROM prs WHERE idUser=? ORDER BY id DESC LIMIT 0,?",
                BeanPropertyRowMapper.newInstance(PaperRockScissors.class), idUser, length);
    }

    public List<PaperRockScissors> getAll(){
        return jdbcTemplate.query("SELECT id, myChoiceInt, idUser, computerChoice, myChoice, score, verdict, totalScore FROM prs",
                BeanPropertyRowMapper.newInstance(PaperRockScissors.class));
    }

    public List<PaperRockScissors> getAllByIdUser(int idUser){
        return jdbcTemplate.query("SELECT id, myChoiceInt, idUser, computerChoice, myChoice, score, verdict, totalScore FROM prs WHERE idUser=?",
                BeanPropertyRowMapper.newInstance(PaperRockScissors.class), idUser);
    }

    public void save(PaperRockScissors paperRockScissors){
        jdbcTemplate.update("INSERT INTO prs(id, myChoiceInt, idUser, computerChoice, myChoice, score, verdict, totalScore) VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                paperRockScissors.getId(), paperRockScissors.getMyChoiceInt(), paperRockScissors.getIdUser(), paperRockScissors.getComputerChoice(),
                paperRockScissors.getMyChoice(), paperRockScissors.getScore(), paperRockScissors.getVerdict(), paperRockScissors.getTotalScore()
                );
    }

    public void deleteAll(){
        jdbcTemplate.update("DELETE FROM prs");
    }

    public void deleteAllById(int id){
        jdbcTemplate.update("DELETE FROM prs WHERE idUser=?", id);
    }
}
