package com.demo.app.movement.services.impl;

import com.demo.app.movement.entitites.Transaction;
import com.demo.app.movement.models.CurrentAccount;
import com.demo.app.movement.models.FixedTermAccount;
import com.demo.app.movement.models.SavingAccount;
import com.demo.app.movement.repositories.TransactionRepository;
import com.demo.app.movement.services.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final WebClient webClient;

    public TransactionServiceImpl(TransactionRepository transactionRepository, WebClient.Builder webClient,@Value("${pasive.card}") String pasiveCardUrl) {
        this.transactionRepository = transactionRepository;
        this.webClient = webClient.baseUrl(pasiveCardUrl).build();
    }

    private Mono<CurrentAccount> findCurrentAccountByDni(String dni, String account){
        return webClient.get().uri("/currentAccount/dni/" + dni + "/account/"+account).
                retrieve().bodyToMono(CurrentAccount.class);
    }
    private Mono<SavingAccount> findSavingAccountByDni(String dni, String account){
        return webClient.get().uri("/savingAccount/dni/" + dni + "/account/"+account).
                retrieve().bodyToMono(SavingAccount.class);
    }
    private Mono<FixedTermAccount> findFixedTermAccountByDni(String dni, String account){
        return webClient.get().uri("/fixedTermAccount/dni/" + dni + "/account/"+account).
                retrieve().bodyToMono(FixedTermAccount.class);
    }

    private Mono<CurrentAccount> updateCurrentAccount(CurrentAccount currentAccount){
        return webClient.put().uri("/currentAccount/" + currentAccount.getId()).
                body(Mono.just(currentAccount), CurrentAccount.class)
                .retrieve()
                .bodyToMono(CurrentAccount.class);
    }
    private Mono<SavingAccount> updateSavingAccount(SavingAccount savingAccount){
        return webClient.put().uri("/savingAccount/" + savingAccount.getId()).
                body(Mono.just(savingAccount), SavingAccount.class)
                .retrieve()
                .bodyToMono(SavingAccount.class);
    }
    private Mono<FixedTermAccount> updateFixedTermAccount(FixedTermAccount fixedTermAccount){
        return webClient.put().uri("/fixedTermAccount/" + fixedTermAccount.getId()).
                body(Mono.just(fixedTermAccount), FixedTermAccount.class)
                .retrieve()
                .bodyToMono(FixedTermAccount.class);
    }

    @Override
    public Flux<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Mono<Transaction> saveTransactionOfCurrentAccount(Transaction transaction) {
        Mono<CurrentAccount> account= findCurrentAccountByDni(transaction.getDni(), transaction.getAccountNumber()).flatMap(x->{
            x.setBalance(x.getBalance().add(transaction.getAmount().negate()));
            return updateCurrentAccount(x);
        });
        return account.then(transactionRepository.save(transaction));
    }

    @Override
    public Mono<Transaction> saveTransactionOfSavingAccount(Transaction transaction) {
        Mono<SavingAccount> account= findSavingAccountByDni(transaction.getDni(), transaction.getAccountNumber()).flatMap(x->{
            x.setBalance(x.getBalance().add(transaction.getAmount().negate()));
            return updateSavingAccount(x);
        });
        return account.then(transactionRepository.save(transaction));
    }

    @Override
    public Mono<Transaction> saveTransactionOfFixedTermAccount(Transaction transaction) {
        Mono<FixedTermAccount> account= findFixedTermAccountByDni(transaction.getDni(), transaction.getAccountNumber()).flatMap(x->{
            x.setBalance(x.getBalance().add(transaction.getAmount().negate()));
            return updateFixedTermAccount(x);
        });
        return account.then(transactionRepository.save(transaction));
    }

    @Override
    public Mono<Transaction> findById(String id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Mono<Transaction> update(Transaction transaction, String id) {
        return transactionRepository.findById(id).flatMap(x->{
            x.setAmount(transaction.getAmount());
            x.setCurrency(transaction.getCurrency());
            x.setAccountNumber(transaction.getAccountNumber());
            x.setCvc(transaction.getCvc());
            return transactionRepository.save(x);
        });
    }

    @Override
    public Mono<Void> delete(String id) {
        return transactionRepository.deleteById(id);
    }
}
