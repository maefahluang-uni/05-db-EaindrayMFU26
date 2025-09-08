package th.mfu.boot;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    public UserRepository repo;

    @PostMapping("/users")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        User existingUser = repo.findByUsername(user.getUsername());
        if (existingUser != null) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }

        repo.save(user);
        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> list() {
        List<User> users = repo.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        repo.deleteById(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}