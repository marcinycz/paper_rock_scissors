package pl.marcinycz.paper_stone_scissors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@RestController
public class PaperStoneScissorsService {
    @Autowired
    PaperStoneScissorsRepository pssRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    //Start single game
    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/pss")
    public ResponseEntity sendMyChoice(@RequestBody PaperStoneScissors pss){
        int lastTotalScore = 0;

        if(!pssRepository.findAll().isEmpty()){
            List<PaperStoneScissors> pssFromDb = pssRepository.findAll();
            lastTotalScore = pssFromDb.get(pssFromDb.size() - 1).getTotalScore();
        }

        pss.startPaperStoneScissorsGame(lastTotalScore);
        pssRepository.save(pss);

        return ResponseEntity.ok(pss);
    }

    //Delete all history on database
    @CrossOrigin(origins = "http://localhost:3000/")
    @DeleteMapping("/pssDeleteHitory")
    public ResponseEntity pssDeleteAllHistory(){
        pssRepository.deleteAll();

        return ResponseEntity.ok("Deleted all database");
    }

    //Game history - a few recent games (decide how much)
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/pssLastGames")
    public ResponseEntity getLastGames() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(jdbcTemplate
                .query("SELECT id, myChoiceInt, computerChoice, myChoice, score, verdict, totalScore FROM pss ORDER BY id DESC LIMIT 0,5",
                BeanPropertyRowMapper.newInstance(PaperStoneScissors.class))));
    }

    //Game all history
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/pssAllGames")
    public ResponseEntity getAllGames() throws JsonProcessingException {
        List<PaperStoneScissors> allGames = pssRepository.findAll();
        return ResponseEntity.ok(objectMapper.writeValueAsString(allGames));
    }
}
