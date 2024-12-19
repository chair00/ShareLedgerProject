package com.example.demo.service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.LedgerDTO;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.entity.*;
import com.example.demo.enums.CategoryConstants;
import com.example.demo.enums.CategoryType;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.LedgerMemberRepository;
import com.example.demo.repository.LedgerRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final LedgerRepository ledgerRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final LedgerMemberRepository ledgerMemberRepository;
    private final LedgerMemberService ledgerMemberService;

    // 가계부 생성
    public ReturnIdDTO createLedger(LedgerDTO.RequestDTO ledgerDto, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버 id가 존재하지 않습니다."));

        // 가계부 최대 생성 개수 확인
        ledgerMemberService.validateLedgerCount(memberId);

        if (ledgerRepository.existsByName(ledgerDto.getLedgerName())) {
            throw new IllegalArgumentException("이미 존재하는 가계부 이름입니다.");
        }

        Ledger ledger = new Ledger(ledgerDto.getLedgerName());
        ledger.setOwner(member);
        ledgerRepository.save(ledger);

        // LedgerMember에 저장 -> owner의 권한은 read_write 로 설정
        LedgerMember ledgerMember = LedgerMember.builder()
                .ledger(ledger)
                .member(member)
                .role(LedgerRole.READ_WRITE)
                .build();

        ledgerMemberRepository.save(ledgerMember);

        // 분류 없음 카테고리 생성(수입)
        Category uncategorizedIn = Category.builder().
                name(CategoryConstants.UNCATEGORIZED_NAME)
                .type(CategoryType.IN)
                .ledger(ledger)
                .build();

        categoryRepository.save(uncategorizedIn);

        // 분류 없음 카테고리 생성(지출)
        Category uncategorizedOut = Category.builder().
                name(CategoryConstants.UNCATEGORIZED_NAME)
                .type(CategoryType.OUT)
                .ledger(ledger)
                .build();

        categoryRepository.save(uncategorizedOut);

        CategoryConstants.UNCATEGORIZED_IN_ID = uncategorizedIn.getId();
        CategoryConstants.UNCATEGORIZED_OUT_ID = uncategorizedOut.getId();

        return new ReturnIdDTO(ledger);

    }

    // 가계부 정보 조회
    public LedgerDTO.ResponseDTO getLedger(Long ledgerId) {

        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다."));

        return new LedgerDTO.ResponseDTO(ledgerId, ledger.getName(), ledger.getOwner().getUsername());

    }

    // 가계부 정보 수정
    @Transactional
    public ReturnIdDTO updateLedger(Long ledgerId, LedgerDTO.RequestDTO ledgerDto) {
        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다."));
        ledger.setName(ledgerDto.getLedgerName());

        return new ReturnIdDTO(ledger);
    }

    // 가계부 삭제
    public void deleteLedger(Long ledgerId, Long ownerId) {
        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new IllegalArgumentException("가계부 id가 존재하지 않습니다."));

        // 권한 확인 (가계부 관리자의 경우에만 삭제 가능)
        if (!ledger.getOwner().getId().equals(ownerId)) {
            throw new SecurityException("해당 가계부를 삭제할 권한이 없습니다.");
        }

        ledgerRepository.delete(ledger);
    }

    // 가계부 검색 기능
    public List<LedgerDTO.ResponseDTO> findLedger(String ledgerName) {

        List<Ledger> ledgers = ledgerRepository.findByNameContaining(ledgerName);

        return ledgers.stream()
                .map(ledger -> new LedgerDTO.ResponseDTO(ledger.getId(), ledger.getName(), ledger.getOwner().getUsername()))
                .collect(Collectors.toList());
    }

    // 가계부 권한 위임

}