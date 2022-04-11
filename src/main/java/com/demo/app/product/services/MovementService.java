package com.demo.app.product.services;

import com.demo.app.product.entities.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {
    Flux<Movement> findAll();
    Mono<Movement> save(Movement movement);
    //Mono<Boolean> findByDni(String dni);
    Mono<Movement> update(Movement movement, long id);
    Mono<Void> delete(long id);
}
