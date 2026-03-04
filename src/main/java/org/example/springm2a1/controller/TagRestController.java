package org.example.springm2a1.controller;

import org.example.springm2a1.model.Tag;
import org.example.springm2a1.repository.TagRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagRestController {

    private final TagRepository repo;

    public TagRestController(TagRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Tag> list() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tag> create(@RequestBody Tag t) {
        Tag saved = repo.save(t);
        return ResponseEntity.created(URI.create("/api/tags/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> update(@PathVariable Long id, @RequestBody Tag t) {
        return repo.findById(id).map(existing -> {
            t.setId(id);
            Tag saved = repo.save(t);
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
