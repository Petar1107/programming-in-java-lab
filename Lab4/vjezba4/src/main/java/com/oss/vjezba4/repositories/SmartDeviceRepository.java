package com.oss.vjezba4.repositories;

import com.oss.vjezba4.models.SmartDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmartDeviceRepository extends JpaRepository<SmartDevice, Long> {
}
