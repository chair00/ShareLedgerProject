package com.example.demo.controller;

import com.example.demo.dto.InviteDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ReturnIdDTO;
import com.example.demo.service.InviteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor

@Tag(name = "초대", description = "가계부 초대 API")
public class InviteController {

    private final InviteService inviteService;

    @Operation(summary = "초대 생성", description = "가계부 관리자가 멤버를 초대한다.")
    @PostMapping("/ledger/{ledgerId}/invite")
    public ResponseEntity<ReturnIdDTO> createInvite(@PathVariable Long ledgerId, @RequestBody InviteDTO.Request req) {
        ReturnIdDTO inviteId = inviteService.createInvite(ledgerId, req);
        return ResponseEntity.ok(inviteId);
    }

    @Operation(summary = "초대 응답", description = "멤버가 가계부 초대에 대해 응답한다.")
    @PostMapping("/member/invite/{inviteId}")
    public ResponseEntity<ResponseDTO> responseInvite(@PathVariable Long inviteId, @RequestBody InviteDTO.Response res) {
        inviteService.responseInvite(inviteId, res);
        return ResponseEntity.ok(new ResponseDTO("초대에 대한 응답이 처리되었습니다."));
    }

}
