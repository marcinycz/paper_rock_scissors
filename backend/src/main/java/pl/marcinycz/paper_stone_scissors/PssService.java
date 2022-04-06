package pl.marcinycz.paper_stone_scissors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PssService {

    @Autowired
    PssRepository pssRepository;

    @Autowired
    ObjectMapper objectMapper;

    //Start single game
    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/pss")
    public ResponseEntity sendMyChoice(@RequestBody Pss pss){
        int lastTotalScore = 0;

        if(!pssRepository.findAll().isEmpty()){
            List<Pss> pssFromDb = pssRepository.findAll();
            lastTotalScore = pssFromDb.get(pssFromDb.size() - 1).getTotalScore();
        }

        pss.startPss(lastTotalScore);

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
        List<Pss> lastGames = new ArrayList<>();
        List<Pss> allGames = pssRepository.findAll();

        //How long history
        int howLong = 5;

        int lastGameLength = howLong;
        int allGamesLength = allGames.toArray().length;

        if(allGamesLength <  howLong){
            lastGameLength = allGamesLength;
        }

        //Last index
        allGamesLength -= 1;

        for (int i = 0; i < lastGameLength; i++){
            lastGames.add(i, allGames.get(allGamesLength));
            allGamesLength --;
        }
        return ResponseEntity.ok(objectMapper.writeValueAsString(lastGames));
    }

    //Game all history
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/pssAllGames")
    public ResponseEntity getAllGames() throws JsonProcessingException {
        List<Pss> allGames = pssRepository.findAll();
        return ResponseEntity.ok(objectMapper.writeValueAsString(allGames));
    }
}
