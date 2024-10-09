package com.example.demo.repository;

import com.example.demo.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    // 특정 기간 데이터 조회
    List<History> findByLedgerIdAndDateBetween(Long ledgerId, LocalDate startDate, LocalDate endDate);

    // 특정 날짜 데이터 조회
    List<History> findByLedgerIdAndDate(Long ledgerId, LocalDate date);

    // 일간/월간/연간/사용자 지정 총 금액 조회
    @Query("SELECT SUM(e.price) FROM History e WHERE e.ledger.id = :ledgerId AND e.date BETWEEN :startDate AND :endDate")
    Long findSumOfPriceBetween(@Param("ledgerId") Long ledgerId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
