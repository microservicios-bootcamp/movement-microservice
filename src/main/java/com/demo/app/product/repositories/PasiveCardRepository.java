package com.demo.app.product.repositories;

import com.demo.app.product.entities.PasiveCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PasiveCardRepository extends ReactiveMongoRepository<PasiveCard,String> {
    Mono<Boolean> findByDni(String dni);
    Mono<PasiveCard> findByAccountNumber(String accountNumber);
}

