package org.example.springm2a1.config;

import org.example.springm2a1.model.Tag;
import org.example.springm2a1.model.Transaction;
import org.example.springm2a1.model.User;
import org.example.springm2a1.repository.TagRepository;
import org.example.springm2a1.repository.TransactionRepository;
import org.example.springm2a1.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepo;
    private final TagRepository tagRepo;
    private final TransactionRepository txRepo;

    public DataLoader(UserRepository userRepo, TagRepository tagRepo, TransactionRepository txRepo) {
        this.userRepo = userRepo;
        this.tagRepo = tagRepo;
        this.txRepo = txRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.count() > 0) return; // don't re-seed

        User alice = userRepo.save(new User("Alice", "alice@example.com"));
        User bob = userRepo.save(new User("Bob", "bob@example.com"));

        Tag groceries = tagRepo.save(new Tag("groceries"));
        Tag rent = tagRepo.save(new Tag("rent"));
        Tag salary = tagRepo.save(new Tag("salary"));

        Transaction t1 = new Transaction();
        t1.setDate(LocalDate.now().minusDays(3));
        t1.setDescription("Grocery store");
        t1.setAmount(new BigDecimal("42.50"));
        t1.setType("DEBIT");
        t1.setCategory("Food");
        t1.setOwner(alice);
        t1.setTags(Set.of(groceries));
        txRepo.save(t1);

        Transaction t2 = new Transaction();
        t2.setDate(LocalDate.now().minusDays(1));
        t2.setDescription("Monthly rent");
        t2.setAmount(new BigDecimal("950.00"));
        t2.setType("DEBIT");
        t2.setCategory("Housing");
        t2.setOwner(bob);
        t2.setTags(Set.of(rent));
        txRepo.save(t2);

        Transaction t3 = new Transaction();
        t3.setDate(LocalDate.now());
        t3.setDescription("Company payroll");
        t3.setAmount(new BigDecimal("2500.00"));
        t3.setType("CREDIT");
        t3.setCategory("Income");
        t3.setOwner(alice);
        t3.setTags(Set.of(salary));
        txRepo.save(t3);
    }
}
