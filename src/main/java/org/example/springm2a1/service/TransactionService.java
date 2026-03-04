package org.example.springm2a1.service;

import org.example.springm2a1.model.Transaction;
import org.example.springm2a1.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public Optional<Transaction> findById(Long id) {
        return repository.findById(id);
    }

    public Transaction save(Transaction tx) {
        return repository.save(tx);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public BigDecimal getBalance() {
        return repository.findAll().stream()
                .map(t -> "CREDIT".equalsIgnoreCase(t.getType()) ? t.getAmount() : t.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
