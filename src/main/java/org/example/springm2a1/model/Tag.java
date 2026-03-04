package org.example.springm2a1.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Transaction> transactions = new HashSet<>();

    public Tag() {}
    public Tag(String name) { this.name = name; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<Transaction> getTransactions() { return transactions; }
    public void setTransactions(Set<Transaction> transactions) { this.transactions = transactions; }
}
