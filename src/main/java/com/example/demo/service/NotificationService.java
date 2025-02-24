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
                "/topic/notification",
                new Notification("초대 알림", ledgerName + " 가계부에 초대되었습니다.")
        );
    }

    public void sendInviteResponseNotification(String ledgerOwnerUsername, String memberName, boolean accepted) {
        String message = accepted ?
                memberName + " 님이 초대를 수락했습니다." :
                memberName + " 님이 초대를 거절했습니다.";

        messagingTemplate.convertAndSendToUser(
                ledgerOwnerUsername,
                "/topic/notification",
                new Notification("초대 응답 알림", message)
        );
    }

    public void sendJoinNotification(String ledgerOwnerUsername, String memberName) {
        messagingTemplate.convertAndSendToUser(
                ledgerOwnerUsername,
                "/topic/notification",
                new Notification("가입 요청 알림", memberName + " 님이 가계부에 가입 요청을 보냈습니다.")
        );
    }

    public void sendJoinResponseNotification(String memberUsername, String ledgerName, boolean accepted) {
        String message = accepted ?
                ledgerName + " 에서 가입 요청을 수락했습니다." :
                ledgerName + " 에서 가입 요청을 거절했습니다.";

        messagingTemplate.convertAndSendToUser(
                memberUsername,
                "/topic/notification",
                new Notification("가입 요청에 대한 응답 알림", message)
        );
    }
}
