package org.example.springm2a1.controller;

import org.example.springm2a1.model.User;
import org.example.springm2a1.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserRepository repo;

    public UserRestController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<User> list() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u) {
        User saved = repo.save(u);
        return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User u) {
        return repo.findById(id).map(existing -> {
            u.setId(id);
            User saved = repo.save(u);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
