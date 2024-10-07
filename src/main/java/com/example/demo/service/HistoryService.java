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

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final LedgerRepository ledgerRepository;
    private final CategoryRepository categoryRepository;

    // 내역 생성
    public ReturnIdDTO save(Long ledgerId, HistoryDTO.Create historyReqDto) {

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
//    public ReturnIdDTO update(Long historyId, HistoryDTO.Update historyReqDto) {
//
//        History history = historyRepository.findById(historyId).orElseThrow(() -> new IllegalArgumentException("내역 id가 존재하지 않습니다."));
//
//        history.ss
//    }

    // 내역 삭제


    // 내역 목록 조회


    // 총 금액 조회


    // 총 금액 목록 조회

}
