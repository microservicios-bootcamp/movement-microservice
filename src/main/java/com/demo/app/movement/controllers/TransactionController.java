package com.demo.app.movement.controllers;

import com.demo.app.movement.entitites.Transaction;
import com.demo.app.movement.services.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Test APIs", description = "Test APIs for demo purpose")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    private ResponseEntity<Flux<Transaction>> findAll(){
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/{id}")
    private Mono<Transaction> findById(@PathVariable String id){
        return transactionService.findById(id);
    }

    @PostMapping("/currentAccount")
    private ResponseEntity<Mono<Transaction>> saveTransactionByCurrentAccount(@RequestBody Transaction transaction){
        return ResponseEntity.ok(transactionService.saveTransactionOfCurrentAccount(transaction));
    }
    @PostMapping("/savingAccount")
    private ResponseEntity<Mono<Transaction>> saveTransactionBySavingAccount(@RequestBody Transaction transaction){
        return ResponseEntity.ok(transactionService.saveTransactionOfSavingAccount(transaction));
    }
    @PostMapping("/fixedTermAccount")
    private ResponseEntity<Mono<Transaction>> saveTransactionByFixedTermAccount(@RequestBody Transaction transaction){
        return ResponseEntity.ok(transactionService.saveTransactionOfFixedTermAccount(transaction));
    }
    @PutMapping("/{id}")
    private Mono<ResponseEntity<Transaction>> update(@RequestBody Transaction transaction, @PathVariable String id){
        return transactionService.update(transaction,id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    private Mono<ResponseEntity<Void>> delete(@PathVariable String id){
        return transactionService.delete(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
