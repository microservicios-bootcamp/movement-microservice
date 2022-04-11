package com.demo.app.product.services;

import com.demo.app.product.entities.PasiveCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PasiveCardService {
    Flux<PasiveCard> findAll();
    Mono<PasiveCard> save(PasiveCard card);
    Mono<Boolean> findByDni(String dni);

    Mono<Boolean> findByAccount(String accountNumber);
    Mono<PasiveCard> findByAccount2(String accountNumber);
    Mono<PasiveCard> update(PasiveCard card, String id);
    Mono<Void> delete(String id);
}
