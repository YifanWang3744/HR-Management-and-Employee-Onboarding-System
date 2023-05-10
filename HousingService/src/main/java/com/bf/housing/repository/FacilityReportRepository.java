package com.bf.housing.repository;

import com.bf.housing.entity.FacilityReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityReportRepository extends JpaRepository<FacilityReport, Long> {
    List<FacilityReport> findAllByEmployeeId(String employee_id);

    FacilityReport findFacilityReportByComments_Id(Long comment_id);
}
