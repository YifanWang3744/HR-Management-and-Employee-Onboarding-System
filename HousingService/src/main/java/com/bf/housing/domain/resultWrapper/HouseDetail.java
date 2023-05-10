package com.bf.housing.domain.resultWrapper;

import com.bf.housing.entity.Employee;
import com.bf.housing.entity.Facility;
import com.bf.housing.entity.FacilityReport;
import com.bf.housing.entity.Landlord;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HouseDetail {
    private String houseAddress;
    private Landlord landlord;
    private List<EmployeeWrapper> occupants;
    private List<Facility> facilities;
    private List<FacilityReport> facilityReports;
}
