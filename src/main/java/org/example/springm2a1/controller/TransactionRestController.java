package org.example.springm2a1.controller;

import org.example.springm2a1.model.Transaction;
import org.example.springm2a1.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionRestController {

    private final TransactionRepository repo;

    public TransactionRestController(TransactionRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Transaction> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody Transaction tx) {
        Transaction saved = repo.save(tx);
        return ResponseEntity.created(URI.create("/api/transactions/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> update(@PathVariable Long id, @RequestBody Transaction tx) {
        return repo.findById(id).map(existing -> {
            tx.setId(id);
            Transaction saved = repo.save(tx);
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
