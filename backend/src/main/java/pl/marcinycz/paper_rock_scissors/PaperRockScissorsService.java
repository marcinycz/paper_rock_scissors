package pl.marcinycz.paper_rock_scissors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        if(!prsRepository.getAllByIdUser(prs.getIdUser()).isEmpty()){
            lastTotalScore  = prsRepository.getLastGameByIdUser(prs.getIdUser(),1).get(0).getTotalScore();
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
        return ResponseEntity.ok("Deleted all game history");
    }


    //Delete all history by id on database
    @CrossOrigin(origins = "http://localhost:3000/")
    @DeleteMapping("/prsDeleteHitory/{id}")
    public ResponseEntity prsDeleteAllHistoryById(@PathVariable int id){
        prsRepository.deleteAllById(id);
        return ResponseEntity.ok("Deleted all game history where id= " + id) ;
    }

    //Game history - a few recent games (decide how much)
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/prsLastGames")
    public ResponseEntity getLastGames() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(prsRepository.getLastGame(5)));
    }

    //Game history by name- a few recent games (decide how much)
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/prsLastGames/{id}")
    public ResponseEntity getLastGamesByUsername(@PathVariable int id) throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(prsRepository.getLastGameByIdUser(id, 5)));
    }

    //Game all history
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/prsAllGames")
    public ResponseEntity getAllGames() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(prsRepository.getAll()));
    }
}
