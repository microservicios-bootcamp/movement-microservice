package com.demo.app.movement.repositories;

import com.demo.app.movement.entitites.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction,String> {
}
