package com.testhsm.testhsm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SatuanResponseDTO {
    private Long id;
    private LocalDateTime createdAt;
    private String description;
    private LocalDateTime updatedAt;
}
