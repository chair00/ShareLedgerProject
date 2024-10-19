package com.example.demo.repository;

import com.example.demo.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long>, JpaSpecificationExecutor<History> {

//    // 특정 기간 데이터 목록 조회
//    List<History> findByLedgerIdAndDateBetween(Long ledgerId, LocalDateTime startDate, LocalDateTime endDate);

    // 일간/월간/연간/사용자 지정 총 수입 금액 조회
    @Query("SELECT SUM(e.price) FROM History e WHERE e.ledger.id = :ledgerId AND e.date BETWEEN :startDate AND :endDate AND e.category.type ='IN'")
    Long findSumOfIncomePriceBetween(@Param("ledgerId") Long ledgerId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // 일간/월간/연간/사용자 지정 총 지출 금액 조회
    @Query("SELECT SUM(e.price) FROM History e WHERE e.ledger.id = :ledgerId AND e.date BETWEEN :startDate AND :endDate AND e.category.type ='OUT'")
    Long findSumOfOutcomePriceBetween(@Param("ledgerId") Long ledgerId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

//    // 카테고리 다중 검색
//    @Query("SELECT e FROM History e WHERE e.ledger.id = :ledgerId AND e.category.id IN :categories")
//    List<History> findByCategories(@Param("ledgerId") Long ledgerId, @Param("categories") List<Long> categories);
}
