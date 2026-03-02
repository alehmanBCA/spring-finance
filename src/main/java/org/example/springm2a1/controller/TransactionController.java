package org.example.springm2a1.controller;

import org.example.springm2a1.model.Transaction;
import org.example.springm2a1.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    // root mapping is provided by HomeController; leave transaction routes under /transactions

    @GetMapping("/transactions")
    public String list(Model model) {
        List<Transaction> txs = service.findAll();
        model.addAttribute("transactions", txs);
        model.addAttribute("balance", service.getBalance());

        // transactions list is available in the model; template will build chart data with Thymeleaf
        return "transactions";
    }

    @GetMapping("/transactions/new")
    public String createForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "new_transaction";
    }

    @PostMapping("/transactions")
    public String create(@Valid @ModelAttribute("transaction") Transaction transaction, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "new_transaction";
        }
        service.save(transaction);
        return "redirect:/transactions";
    }
}
