package com.demo.app.product.services.impl;


import com.demo.app.product.entities.Movement;
import com.demo.app.product.repositories.MovementRepository;
import com.demo.app.product.services.MovementService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;

    public MovementServiceImpl(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    @Override
    public Flux<Movement> findAll() {
        return movementRepository.findAll();
    }

    @Override
    public Mono<Movement> save(Movement movement) {
        return movementRepository.save(movement);
    }
/*
    @Override
    public Mono<Boolean> findByDni(String dni) {
        return cardRepository.findByDni(dni).hasElement().flatMap(x->{
            if(x)return Mono.just(true);
            return Mono.just(false);
        });
    }
*/
    @Override
    public Mono<Movement> update(Movement movement, long id) {
        return movementRepository.findById(id).flatMap(x->{
            x.setImporte(movement.getImporte());
            x.setAccountNumber(movement.getAccountNumber());
            x.setMovrestantes(movement.getMovrestantes());
            x.setOrigen(movement.getOrigen());
            x.setIdcliente(movement.getIdcliente());
            return movementRepository.save(x);
        });
    }

    @Override
    public Mono<Void> delete(long id) {
        return movementRepository.deleteById(id);
    }
}
