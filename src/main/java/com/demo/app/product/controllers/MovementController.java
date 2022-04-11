package com.demo.app.product.controllers;


import com.demo.app.product.config.Scheduler;
import com.demo.app.product.entities.Movement;
import com.demo.app.product.entities.PasiveCard;
import com.demo.app.product.repositories.PersonalRepository;
import com.demo.app.product.services.MovementService;
import com.demo.app.product.services.PasiveCardService;
import com.demo.app.product.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/movement")
public class MovementController {

    private final MovementService movementService;
    private final PasiveCardService pasiveCardService;
    private final PersonalRepository personalRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    public MovementController(MovementService movementService, PasiveCardService pasiveCardService, PersonalRepository personalRepository) {
        this.movementService = movementService;
        this.pasiveCardService = pasiveCardService;
        this.personalRepository = personalRepository;
    }

    @GetMapping
    private ResponseEntity<Flux<Movement>> findAll() {
        Flux<Movement> movement = movementService.findAll();
        return ResponseEntity.ok(movement);
    }

    @PostMapping
    private ResponseEntity<Mono<Movement>> save(@RequestBody Movement movement) {

        if (pasiveCardService.findByDni(movement.getIdcliente()) == Mono.just(false)) {

            return ResponseEntity.notFound().build();
        } else {
            if (movement.getMovrestantes() == 0) {
                return ResponseEntity.notFound().build();
            } else {
                movement.setMovrestantes(movement.getMovrestantes() - 1);
                movement.setId(sequenceGeneratorService.generateSequence(Movement.SEQUENCE_NAME));
                movement.setCreateAt(LocalDateTime.now());
                return ResponseEntity.ok(movementService.save(movement));
            }
        }
    }

    @PutMapping("/{id}")
    private Mono<ResponseEntity<Movement>> update(@RequestBody Movement movement, @PathVariable long id) {
        movement.setUpdateAt(LocalDateTime.now());
        return movementService.update(movement, id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    private Mono<ResponseEntity<Void>> delete(@PathVariable long id) {
        return movementService.delete(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
