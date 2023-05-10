package com.bfteam3.ApplicationService.repository.Application;
import com.bfteam3.ApplicationService.domain.entity.Application.ApplicationWorkFlow;
import com.bfteam3.ApplicationService.domain.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationWorkFlowRepo extends JpaRepository<ApplicationWorkFlow, Long> {

    List<ApplicationWorkFlow> findAll();

    //@Query("FROM ApplicationWorkFlow a WHERE a.employeeId = :id")
    ApplicationWorkFlow findApplicationByEmployeeId(String id);

//    @Query("FROM ApplicationWorkFlow a WHERE a.applicationStatus = :status")
//    List<ApplicationWorkFlow> findApplicationByStatus(String status);

    List<ApplicationWorkFlow> findAllByApplicationStatus(ApplicationStatus applicationStatus);
}
