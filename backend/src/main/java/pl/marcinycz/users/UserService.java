package pl.marcinycz.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        if(user.getUsername().length() < 3){
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).build();
        }
        if(user.getUsername().contains(" ")){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        if(usersRepository.getByName(user.getUsername()).size() > 0){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        usersRepository.save(user);
        return ResponseEntity.ok(user);
    }

    //Login
    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user){
        if(user.getUsername().length() < 3){
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).build();
        }
        List <User> loginUser =  usersRepository.getByName(user.getUsername());
        if(loginUser.size() > 0){
            user.setId(loginUser.get(0).getId());
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //User by id
    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/userById")
    public ResponseEntity userById(@RequestBody User user){
        if(usersRepository.getById(user.getId()).size() > 0){
            return ResponseEntity.ok(usersRepository.getById(user.getId()).get(0));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //User by username
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/user/{username}")
    public ResponseEntity getAllUsers(@PathVariable String username) throws JsonProcessingException {
        if(usersRepository.getByName(username).size() > 0){
            return ResponseEntity.ok(usersRepository.getByName(username).get(0));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //Delete all users
    @CrossOrigin(origins = "http://localhost:3000/")
    @DeleteMapping("/usersDelete")
    public ResponseEntity usersDeleteAll(){
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
