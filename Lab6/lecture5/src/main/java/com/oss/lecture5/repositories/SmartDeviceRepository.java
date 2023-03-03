package com.oss.lecture5.repositories;

import com.oss.lecture5.models.SmartDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmartDeviceRepository extends JpaRepository<SmartDevice, Long> {
    Page<SmartDevice> findAll (Pageable pageable);
}
