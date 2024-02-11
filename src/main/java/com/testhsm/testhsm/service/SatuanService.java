package com.testhsm.testhsm.service;

import com.testhsm.testhsm.dto.SatuanResponseDTO;
import com.testhsm.testhsm.entity.Satuan;
import com.testhsm.testhsm.repository.SatuanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SatuanService {

    @Autowired
    private SatuanRepository satuanRepository;

    public Satuan createSatuan(Satuan satuan) {
        return satuanRepository.save(satuan);
    }

    public List<Satuan> getAllSatuan() {
        return satuanRepository.findAll();
    }

    public Page<SatuanResponseDTO> findPaginatedSatuan(int pageNo, int pageSize, Sort sort) {
        int adjustedPageNo = Math.max(pageNo, 1);
        Pageable pageable = PageRequest.of(adjustedPageNo - 1, pageSize, sort);
        Page<Satuan> satuanPage = satuanRepository.findAll(pageable);
        return satuanPage.map(this::convertToResponseDTO);
    }

    private SatuanResponseDTO convertToResponseDTO(Satuan satuan) {
        return SatuanResponseDTO.builder()
                .id(satuan.getId())
                .createdAt(satuan.getCreatedAt())
                .description(satuan.getDescription())
                .updatedAt(satuan.getUpdatedAt())
                .build();
    }


    public Satuan findSatuanById(Long id) {
        return satuanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Satuan ID: " + id + " does not exist."));
    }

    public Satuan updateSatuan(Satuan satuan) {
        Satuan existingSatuan = satuanRepository.findById(satuan.getId())
                .orElseThrow(() -> new RuntimeException("Satuan ID: " + satuan.getId() + " does not exist."));

        if (satuan.getDescription() != null) {
            existingSatuan.setDescription(satuan.getDescription());
        }

        return satuanRepository.save(existingSatuan);

    }

    public void deleteSatuan(Long id) {
        if (!satuanRepository.existsById(id)) {
            throw new RuntimeException("Satuan ID: " + id + " does not exist.");
        }

        satuanRepository.deleteById(id);
    }
}
