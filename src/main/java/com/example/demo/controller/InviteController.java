package com.example.demo.controller;

import com.example.demo.dto.InviteDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.dto.signUp.CustomUserDetails;
import com.example.demo.service.InviteService;
import com.example.demo.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor

@Tag(name = "초대", description = "가계부 초대 API")
public class InviteController {

    private final InviteService inviteService;
    private final NotificationService notificationService;

    @Operation(summary = "초대 생성 및 알림 전송", description = "가계부 관리자가 멤버를 초대하고, 성공적으로 실행될 경우 멤버에게 즉시 초대 알림이 전송된다.")
    @PostMapping("/ledger/{ledgerId}/invite")
    public ResponseEntity<ReturnIdDTO> createInvite(
            @PathVariable Long ledgerId,
            @RequestBody InviteDTO.Request req,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 초대 생성
        ReturnIdDTO inviteId = inviteService.createInvite(ledgerId, req, userDetails.getId());

        // 초대 알림 전송
        String ledgerName = inviteService.getLedgerName(ledgerId);
        notificationService.sendInviteNotification(req.getMemberUsername(), ledgerName); // 알림 전송

        return ResponseEntity.ok(inviteId);
    }

    @Operation(summary = "초대 목록 조회", description = "멤버가 가계부 초대 목록을 조회한다.")
    @GetMapping("/member/invite")
    public ResponseEntity<List<InviteDTO.RequestData>> getInvite(@AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(inviteService.getInviteData(userDetails.getId()));
    }

    @Operation(summary = "초대 응답 및 알림 전송", description = "멤버가 가계부 초대에 대해 응답하고, 성공적으로 실행될 경우 가계부 관리자에게 즉시 응답 알림이 전송된다.")
    @PostMapping("/member/invite/{inviteId}")
    public ResponseEntity<ResponseDTO> responseInvite(
            @PathVariable Long inviteId,
            @RequestBody InviteDTO.Response res,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 초대 응답
        inviteService.responseInvite(inviteId, res);

        // 초대 응답 알림 전송
        String ledgerOwnerUsername = inviteService.getLedgerOwnerUsername(inviteId);
        String memberName = inviteService.getMemberName(inviteId);
        boolean isAccepted = inviteService.getResponseAction(inviteId);
        notificationService.sendInviteResponseNotification(
                ledgerOwnerUsername,
                memberName,
                isAccepted
        );

        return ResponseEntity.ok(new ResponseDTO("초대에 대한 응답이 처리되었습니다."));
    }

}
