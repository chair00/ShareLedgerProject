package com.example.demo.dto;

import com.example.demo.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "생성/수정 시 id 리턴")
public class ReturnIdDTO {
    @Schema(description = "생성/수정 id")
    private Long id;

    public ReturnIdDTO(Category entity) {
        this.id = entity.getId();
    }

    public ReturnIdDTO(Ledger entity) {
        this.id = entity.getId();
    }

    public ReturnIdDTO(History entity) {
        this.id = entity.getId();
    }

    public ReturnIdDTO(Member entity) {
        this.id = entity.getId();
    }

    public ReturnIdDTO(Long id) {
        this.id = id;
    }

    public ReturnIdDTO(Invite entity) {
        this.id = entity.getId();
    }

    public ReturnIdDTO(JoinRequest entity) {
        this.id = entity.getId();
    }
}

