package com.bf.housing.service;

import com.bf.housing.entity.FacilityReportDetail;
import com.bf.housing.repository.ReportDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportDetailService {

    @Autowired
    private ReportDetailRepository repository;

    public List<FacilityReportDetail> findAllByReportId(Long reportId){
        return repository.findAllByReportId(reportId);
    }
}
