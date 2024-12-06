package com.example.demo.service;

import com.example.demo.dto.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendInviteNotification(String memberUsername, String ledgerName) {
        messagingTemplate.convertAndSendToUser(
                memberUsername,
                "/topic/invite",
                new Notification("초대 알림", ledgerName + " 가계부에 초대되었습니다.")
        );
    }

    public void sendInviteResponseNotification(String ledgerOwnerUsername, String memberName, boolean accepted) {
        String message = accepted ?
                memberName + " 님이 초대를 수락했습니다." :
                memberName + " 님이 초대를 거절했습니다.";

        messagingTemplate.convertAndSendToUser(
                ledgerOwnerUsername,
                "topic/invite-response",
                new Notification("초대 응답 알림", message)
        );
    }
}
