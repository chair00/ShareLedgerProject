package com.example.demo.service;

import com.example.demo.dto.HistoryDTO;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.specification.HistorySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final LedgerRepository ledgerRepository;
    private final CategoryRepository categoryRepository;

    private final LedgerMemberService ledgerMemberService;


    // 내역 생성
    public ReturnIdDTO save(Long ledgerId, HistoryDTO.HistoryRequest historyReqDto, Long memberId) {

        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다"));

        LedgerMember ledgerMember = ledgerMemberService.findByLedgerIdAndMemberId(ledgerId, memberId);

        if (!ledgerMember.canWrite()) {
            throw new IllegalStateException("해당 멤버는 내역을 생성할 권한이 없습니다.");
        }

        History history = historyReqDto.toEntity();

        history.setLedger(ledger);

        Category category = categoryRepository.findById(historyReqDto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("카테고리 id가 존재하지 않습니다"));
        history.setCategory(category);

        return new ReturnIdDTO(historyRepository.save(history));
    }

    // 내역 상세 조회
    public HistoryDTO.HistoryResponse find(Long historyId) {
        History history = historyRepository.findById(historyId).orElseThrow(() -> new IllegalArgumentException("내역 id가 존재하지 않습니다."));

        return new HistoryDTO.HistoryResponse(history);
        //return CategoryResDto.builder().name(category.getName()).type(category.getType()).parentCategoryId(parentId).children(children).build();
    }

    // 내역 수정
    @Transactional
    public ReturnIdDTO update(Long historyId, HistoryDTO.HistoryRequest historyReqDto) {

        History history = historyRepository.findById(historyId).orElseThrow(() -> new IllegalArgumentException("내역 id가 존재하지 않습니다."));

        history.setName(historyReqDto.getName());
        history.setDate(historyReqDto.getDate());
        history.setPrice(historyReqDto.getPrice());
        history.setMemo(historyReqDto.getMemo());

        Category category = categoryRepository.findById(historyReqDto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("카테고리 id가 존재하지 않습니다"));
        history.setCategory(category);

        return new ReturnIdDTO(history);
    }

    // 내역 삭제
    @Transactional
    public void delete(Long historyId) {

        History history = historyRepository.findById(historyId).orElseThrow(() -> new IllegalArgumentException("내역 id가 존재하지 않습니다."));

        historyRepository.deleteById(history.getId());
    }

//    // 내역 목록 조회 (일간 / 월간)
//    public List<HistoryDTO.Response> findByDate (Long ledgerId, LocalDateTime startDate, LocalDateTime endDate) {
//
//        return HistoryDTO.Response.ResponseList(historyRepository.findByLedgerIdAndDateBetween(ledgerId, startDate, endDate));
//    }

    // 총 금액 조회
    public HistoryDTO.ResponseTotalPrice getPriceSumForDateRange(Long ledgerId, LocalDateTime startDate, LocalDateTime endDate) {

        Long totalInPrice = historyRepository.findSumOfIncomePriceBetween(ledgerId, startDate, endDate);
        Long totalOutPrice = historyRepository.findSumOfOutcomePriceBetween(ledgerId, startDate, endDate);
        Long totalPrice = totalInPrice - totalOutPrice;

        return new HistoryDTO.ResponseTotalPrice(totalInPrice, totalOutPrice, totalPrice);
    }

//    public List<HistoryDTO.Response> findByCategories(Long ledgerId, List<Long> categories) {
//
//        return HistoryDTO.Response.ResponseList(historyRepository.findByCategories(ledgerId, categories));
//    }

    public List<HistoryDTO.HistoryResponse> findByConditions(Long ledgerId, List<Long> categories, LocalDateTime startDate, LocalDateTime endDate) {
        Specification<History> specification = Specification
                .where(HistorySpecification.hasLedgerId(ledgerId))
                .and(HistorySpecification.hasCategories(categories))
                .and(HistorySpecification.hasDateBetween(startDate, endDate));

        return HistoryDTO.HistoryResponse.ResponseList(historyRepository.findAll(specification));
    }

    //만들어야함
//    // 총 금액 목록 조회
//    public HistoryDTO.Response getHistoryListForCondition(Long ledgerId, LocalDate startDate, LocalDate endDate) {
//
//        return new HistoryDTO.ResponseTotalPrice(historyRepository.findSumOfPriceBetween(ledgerId, startDate, endDate));
//    }

}
