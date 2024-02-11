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
public class ItemResponseDTO {
    private Long id;
    private String barcode;
    private LocalDateTime createdAt;
    private String itemCode;
    private String itemName;
    private Long satuanId;
    private LocalDateTime updatedAt;
}
