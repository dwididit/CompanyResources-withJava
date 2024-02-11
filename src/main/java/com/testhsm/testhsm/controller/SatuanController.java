package com.testhsm.testhsm.controller;

import com.testhsm.testhsm.dto.SatuanRequestDTO;
import com.testhsm.testhsm.dto.SatuanResponseDTO;
import com.testhsm.testhsm.entity.Satuan;
import com.testhsm.testhsm.service.SatuanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Satuan", description = "Contains all the operations that can be performed on a satuan.")
@RestController
@RequestMapping("/satuan")
public class SatuanController {

    @Autowired
    private SatuanService satuanService;

    @PostMapping
    public ResponseEntity<SatuanResponseDTO> createSatuan(@RequestBody SatuanRequestDTO satuanRequestDTO) {
        Satuan satuan = new Satuan();
        satuan.setDescription(satuanRequestDTO.getDescription());

        Satuan savedSatuan = satuanService.createSatuan(satuan);

        SatuanResponseDTO satuanResponseDTO = SatuanResponseDTO.builder()
                .id(savedSatuan.getId())
                .createdAt(savedSatuan.getCreatedAt())
                .description(savedSatuan.getDescription())
                .updatedAt(savedSatuan.getUpdatedAt())
                .build();

        return ResponseEntity.ok(satuanResponseDTO);
    }

    @GetMapping("/pagination")
    public Page<SatuanResponseDTO> getPaginatedSatuan(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction)
    {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return satuanService.findPaginatedSatuan(pageNo, pageSize, sort);
    }

    @GetMapping
    public List<SatuanResponseDTO> getALlSatuan() {
        return satuanService.getAllSatuan().stream()
                .map(satuan -> new SatuanResponseDTO(satuan.getId(),
                        satuan.getCreatedAt(), satuan.getDescription(), satuan.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSatuanById(@PathVariable Long id) {
        try {
            Satuan satuan = satuanService.findSatuanById(id);

            SatuanResponseDTO satuanResponseDTO = SatuanResponseDTO.builder()
                    .id(satuan.getId())
                    .createdAt(satuan.getCreatedAt())
                    .description(satuan.getDescription())
                    .updatedAt(satuan.getUpdatedAt())
                    .build();
            return ResponseEntity.ok(satuanResponseDTO);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSatuan(@PathVariable Long id, @RequestBody SatuanRequestDTO satuanRequestDTO) {
        try {
            Satuan satuan = new Satuan();
            satuan.setId(id);
            satuan.setDescription(satuanRequestDTO.getDescription());

            Satuan updatedSatuan = satuanService.updateSatuan(satuan);

            if (updatedSatuan == null) {
                return new ResponseEntity<>("Satuan ID not found", HttpStatus.NOT_FOUND);
            }

            SatuanResponseDTO satuanResponseDTO = SatuanResponseDTO.builder()
                    .id(updatedSatuan.getId())
                    .createdAt(updatedSatuan.getCreatedAt())
                    .description(updatedSatuan.getDescription())
                    .updatedAt(updatedSatuan.getUpdatedAt())
                    .build();

            return ResponseEntity.ok(satuanResponseDTO);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSatuan(@PathVariable Long id) {
        try {
            satuanService.deleteSatuan(id);
            return ResponseEntity.ok("Satuan ID: " + id + " was successfully deleted.");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
