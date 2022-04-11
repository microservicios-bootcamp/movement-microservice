package com.demo.app.product.repositories;

import com.demo.app.product.entities.Personal;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonalRepository extends ReactiveMongoRepository<Personal, String> {

    Mono<Personal> findById(String id);

}
