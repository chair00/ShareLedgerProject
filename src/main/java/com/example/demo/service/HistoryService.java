package com.example.demo.service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.HistoryDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.History;
import com.example.demo.entity.Ledger;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.HistoryRepository;
import com.example.demo.repository.LedgerRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final LedgerRepository ledgerRepository;
    private final CategoryRepository categoryRepository;

    // 내역 생성
    public ReturnIdDTO save(Long ledgerId, HistoryDTO.Request historyReqDto) {

        History history = historyReqDto.toEntity();

        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다"));
        history.setLedger(ledger);

        Category category = categoryRepository.findById(historyReqDto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("카테고리 id가 존재하지 않습니다"));
        history.setCategory(category);

        return new ReturnIdDTO(historyRepository.save(history));
    }

    // 내역 상세 조회
    public HistoryDTO.Response find(Long historyId) {
        History history = historyRepository.findById(historyId).orElseThrow(() -> new IllegalArgumentException("내역 id가 존재하지 않습니다."));

        return new HistoryDTO.Response(history);
        //return CategoryResDto.builder().name(category.getName()).type(category.getType()).parentCategoryId(parentId).children(children).build();
    }

    // 내역 수정
    @Transactional
    public ReturnIdDTO update(Long historyId, HistoryDTO.Request historyReqDto) {

        History history = historyRepository.findById(historyId).orElseThrow(() -> new IllegalArgumentException("내역 id가 존재하지 않습니다."));

        history.setName(historyReqDto.getName());
        history.setDate(historyReqDto.getDate());
        history.setPrice(historyReqDto.getPrice());

        Category category = categoryRepository.findById(historyReqDto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("카테고리 id가 존재하지 않습니다"));
        history.setCategory(category);

        return new ReturnIdDTO(history);
    }

    // 내역 삭제
    public void delete(Long historyId) {

        History history = historyRepository.findById(historyId).orElseThrow(() -> new IllegalArgumentException("내역 id가 존재하지 않습니다."));

        historyRepository.deleteById(history.getId());
    }

    // 내역 목록 조회 (일간 / 월간)
    public List<HistoryDTO.Response> findByDate1 (Long ledgerId, String year, String month, String day) {

        if (day.equals("-1")) {

            LocalDate startOfMonth = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1); // 해당 월의 첫 번째 날
            LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth()); // 해당 월의 마지막 날

            return HistoryDTO.Response.ResponseList(historyRepository.findByLedgerIdAndDateBetween(ledgerId, startOfMonth, endOfMonth));
        }

        LocalDate date = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        return HistoryDTO.Response.ResponseList(historyRepository.findByLedgerIdAndDate(ledgerId, date));
    }

//    // 내역 목록 조회 (월간)
//    public List<HistoryDTO.Response> findByMonth(Long ledgerId, int year, int month) {
//
//        LocalDate startOfMonth = LocalDate.of(year, month, 1); // 해당 월의 첫 번째 날
//        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth()); // 해당 월의 마지막 날
//
//        return HistoryDTO.Response.ResponseList(historyRepository.findByLedgerIdAndDateBetween(ledgerId, startOfMonth, endOfMonth));
//    }
//
//    // 내역 목록 조회 (월간)
//    public List<HistoryDTO.Response> findByDate(Long ledgerId, LocalDate date) {
//        return HistoryDTO.Response.ResponseList(historyRepository.findByLedgerIdAndDate(ledgerId, date));
//    }

    // 총 금액 조회
    public HistoryDTO.ResponseTotalPrice getPriceSumForDateRange(Long ledgerId, LocalDate startDate, LocalDate endDate) {

        return new HistoryDTO.ResponseTotalPrice(historyRepository.findSumOfPriceBetween(ledgerId, startDate, endDate));
    }

    //만들어야함
//    // 총 금액 목록 조회
//    public HistoryDTO.Response getHistoryListForCondition(Long ledgerId, LocalDate startDate, LocalDate endDate) {
//
//        return new HistoryDTO.ResponseTotalPrice(historyRepository.findSumOfPriceBetween(ledgerId, startDate, endDate));
//    }

}
