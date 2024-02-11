package com.testhsm.testhsm.repository;

import com.testhsm.testhsm.entity.Satuan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface SatuanRepository extends JpaRepository<Satuan, Long> {
    Page<Satuan> findAll(Pageable pageable);
}
