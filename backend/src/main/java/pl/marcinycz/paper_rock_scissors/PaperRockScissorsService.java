package pl.marcinycz.paper_rock_scissors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaperRockScissorsService {
    @Autowired
    PaperRockScissorsRepository prsRepository;

    @Autowired
    ObjectMapper objectMapper;

    //Start single game
    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/prs")
    public ResponseEntity sendMyChoice(@RequestBody PaperRockScissors prs){
        int lastTotalScore = 0;

        if(!prsRepository.getAll().isEmpty()){
            List<PaperRockScissors> prsFromDb = prsRepository.getAll();
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
        return ResponseEntity.ok(objectMapper.writeValueAsString(prsRepository.getLastGame(5)));
    }

    //Game all history
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/prsAllGames")
    public ResponseEntity getAllGames() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(prsRepository.getAll()));
    }
}
