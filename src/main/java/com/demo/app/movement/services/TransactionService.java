package com.demo.app.movement.services;

import com.demo.app.movement.entitites.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Flux<Transaction> findAll();
    Mono<Transaction> saveTransactionOfCurrentAccount(Transaction transaction);
    Mono<Transaction> saveTransactionOfSavingAccount(Transaction transaction);
    Mono<Transaction> saveTransactionOfFixedTermAccount(Transaction transaction);
    Mono<Transaction> findById(String id);
    Mono<Transaction> update(Transaction transaction,String id);
    Mono<Void> delete(String id);
}
