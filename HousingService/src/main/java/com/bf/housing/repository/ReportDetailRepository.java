package com.bf.housing.repository;

import com.bf.housing.entity.FacilityReportDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportDetailRepository extends JpaRepository<FacilityReportDetail, Long> {
    List<FacilityReportDetail> findAllByReportId(Long reportId);
}
