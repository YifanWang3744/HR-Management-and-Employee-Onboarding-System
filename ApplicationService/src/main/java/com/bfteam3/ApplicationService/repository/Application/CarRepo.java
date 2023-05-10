package com.bfteam3.ApplicationService.repository.Application;
import com.bfteam3.ApplicationService.domain.common.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepo extends JpaRepository<Car, Integer> {

    @Query("FROM Car c WHERE c.employeeId = :id")
    public List<Car> findByEmployeeId(String id);

}
