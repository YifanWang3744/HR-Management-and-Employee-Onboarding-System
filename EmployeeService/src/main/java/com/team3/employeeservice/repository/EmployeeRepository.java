package com.team3.employeeservice.repository;

import com.team3.employeeservice.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Employee findEmployeeByEmail(String email);
    Page<Employee> findAllByEmailOrderByLastName(String email, Pageable page);
    Page<Employee> findAllByFirstNameAndLastName(String firstName, String lastName, Pageable page);
    List<Employee> findAllByHouseId(Long houseId);
    Optional<Employee> findEmployeeByUserId(Long userId);
}
