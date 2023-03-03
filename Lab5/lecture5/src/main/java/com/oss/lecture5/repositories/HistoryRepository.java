package com.oss.lecture5.repositories;

import com.oss.lecture5.models.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    public List<History> findByYearMeasured (Integer yearMeasured);
    public  List<History> findByYearMeasuredAndMonthMeasured (Integer yearMeasured, Integer monthMeasured);
    public List<History> findByYearMeasuredAndSmartDeviceId(Integer yearMeasured, Long smartDeviceId);

    public List<History> findByYearMeasuredAndMonthMeasuredAndSmartDeviceId(Integer yearMeasured, Integer monthMeasured, Long smartDeviceId);

    Page<History> findAll (Pageable pageable);

}
