package com.demo.app.product.repositories;

import com.demo.app.product.entities.Enterprise;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseRepository extends ReactiveMongoRepository<Enterprise, String> {
}
