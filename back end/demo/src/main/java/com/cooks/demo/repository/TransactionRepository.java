package com.cooks.demo.repository;

import com.cooks.demo.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction,String> {
}
