package com.example.demo.repository;

import com.example.demo.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerRepository extends JpaRepository<Ledger, Long> {

    boolean existsByName(String name);

    List<Ledger> findByNameContaining(String name);

}