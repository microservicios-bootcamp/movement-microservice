package com.demo.app.product.services.impl;


import com.demo.app.product.entities.PasiveCard;
import com.demo.app.product.repositories.PasiveCardRepository;
import com.demo.app.product.services.PasiveCardService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PasiveCardServiceImpl implements PasiveCardService {

    private final PasiveCardRepository cardRepository;

    public PasiveCardServiceImpl(PasiveCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Flux<PasiveCard> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public Mono<PasiveCard> save(PasiveCard card) {
        return cardRepository.save(card);
    }

    @Override
    public Mono<Boolean> findByDni(String dni) {
        return cardRepository.findByDni(dni).hasElement().flatMap(x->{
            if(x)return Mono.just(true);
            return Mono.just(false);
        });
    }

    @Override
    public Mono<Boolean> findByAccount(String accountNumber) {
        return cardRepository.findByAccountNumber(accountNumber).hasElement().flatMap(x->{
            if(x)return Mono.just(true);
            return Mono.just(false);
        });
    }

    @Override
    public Mono<PasiveCard> findByAccount2(String accountNumber) {
        return cardRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Mono<PasiveCard> update(PasiveCard card, String id) {
        return cardRepository.findById(id).flatMap(x->{
            x.setCvc(card.getCvc());
            x.setAccountNumber(card.getAccountNumber());
            x.setCurrency(card.getCurrency());
            x.setBalance(card.getBalance());
            x.setAccountType(card.getAccountType());
            x.setDni(card.getDni());
            return cardRepository.save(x);
        });
    }

    @Override
    public Mono<Void> delete(String id) {
        return cardRepository.deleteById(id);
    }
}
