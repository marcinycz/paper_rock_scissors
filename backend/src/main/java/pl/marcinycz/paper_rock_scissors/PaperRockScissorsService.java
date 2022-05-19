package pl.marcinycz.paper_rock_scissors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@RestController
public class PaperRockScissorsService {
    @Autowired
    PaperRockScissorsRepository prsRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    //Start single game
    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/prs")
    public ResponseEntity sendMyChoice(@RequestBody PaperRockScissors prs){
        int lastTotalScore = 0;

        if(!prsRepository.findAll().isEmpty()){
            List<PaperRockScissors> prsFromDb = prsRepository.findAll();
            lastTotalScore = prsFromDb.get(prsFromDb.size() - 1).getTotalScore();
        }

        prs.startPaperRockScissorsGame(lastTotalScore);
        prsRepository.save(prs);

        return ResponseEntity.ok(prs);
    }

    //Delete all history on database
    @CrossOrigin(origins = "http://localhost:3000/")
    @DeleteMapping("/prsDeleteHitory")
    public ResponseEntity pssDeleteAllHistory(){
        prsRepository.deleteAll();

        return ResponseEntity.ok("Deleted all database");
    }

    //Game history - a few recent games (decide how much)
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/prsLastGames")
    public ResponseEntity getLastGames() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(
                jdbcTemplate.query("SELECT id, myChoiceInt, computerChoice, myChoice, score, verdict, totalScore FROM prs ORDER BY id DESC LIMIT 0,5",
                BeanPropertyRowMapper.newInstance(PaperRockScissors.class))));
    }

    //Game all history
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/prsAllGames")
    public ResponseEntity getAllGames() throws JsonProcessingException {
//        return ResponseEntity.ok(objectMapper.writeValueAsString(
//                jdbcTemplate.query("SELECT id, myChoiceInt, computerChoice, myChoice, score, verdict, totalScore FROM prs",
//                        BeanPropertyRowMapper.newInstance(PaperRockScissors.class))));

        List<PaperRockScissors> allGames = prsRepository.findAll();
        return ResponseEntity.ok(objectMapper.writeValueAsString(allGames));
    }
}
