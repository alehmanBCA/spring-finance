package org.example.springm2a1.service;

import org.example.springm2a1.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TransactionService {

    private final List<Transaction> items = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<Transaction> findAll() {
        synchronized (items) {
            return new ArrayList<>(items);
        }
    }

    public Optional<Transaction> findById(Long id) {
        synchronized (items) {
            return items.stream().filter(t -> t.getId().equals(id)).findFirst();
        }
    }

    public Transaction save(Transaction tx) {
        if (tx.getId() == null) {
            tx.setId(idCounter.getAndIncrement());
            items.add(tx);
        } else {
            // update existing
            synchronized (items) {
                items.removeIf(t -> t.getId().equals(tx.getId()));
                items.add(tx);
            }
        }
        return tx;
    }

    public void deleteById(Long id) {
        synchronized (items) {
            items.removeIf(t -> t.getId().equals(id));
        }
    }

    public BigDecimal getBalance() {
        synchronized (items) {
            return items.stream()
                    .map(t -> "CREDIT".equalsIgnoreCase(t.getType()) ? t.getAmount() : t.getAmount().negate())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }
}
