package pl.marcinycz.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ObjectMapper objectMapper;

    //Add user
    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/user")
    public ResponseEntity addNewUser(@RequestBody User user){
        usersRepository.save(user);

        return ResponseEntity.ok(user);
    }

    //Delete all users
    @CrossOrigin(origins = "http://localhost:3000/")
    @DeleteMapping("/usersDelete")
    public ResponseEntity pssDeleteAllHistory(){
        usersRepository.deleteAll();
        return ResponseEntity.ok("Deleted all users");
    }

    //All users
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/allUsers")
    public ResponseEntity getAllUsers() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(usersRepository.getAll()));
    }
}
