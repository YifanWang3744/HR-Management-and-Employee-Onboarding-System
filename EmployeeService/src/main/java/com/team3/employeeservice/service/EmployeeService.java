package com.team3.employeeservice.service;

import com.team3.employeeservice.domain.Employee;
import com.team3.employeeservice.domain.VisaStatus;
import com.team3.employeeservice.exception.EmployeeNotFoundException;
import com.team3.employeeservice.repository.EmployeeRepository;
import com.team3.employeeservice.resultWrapper.ProfileWrapper;
import com.team3.employeeservice.resultWrapper.StatusWrapper;
import com.team3.employeeservice.resultWrapper.VisaWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> findAllEmployees() {
        return repository.findAll();
    }

    public Employee findEmployeeById(String id) throws EmployeeNotFoundException {
//        return repository.findById(id).orElse(null);
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee with id=" + id + " Not Found!"));
    }

    public Employee findEmployeeByEmail(String email) throws EmployeeNotFoundException {
        Employee employee = repository.findEmployeeByEmail(email);

        if (employee == null)
            throw new EmployeeNotFoundException("Employee with email=" + email + " Not Found!");

        return employee;
    }

    // security contents
    public String getCurrentEmployeeId() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void saveOrUpdateEmployee(Employee employee) {
        repository.save(employee);
    }

    public List<StatusWrapper> getVisaStatuses(int page, int size) {
        List<Employee> employees = repository.findAll(PageRequest.of(page, size)).toList();

        List<StatusWrapper> statusWrapperList = employees.stream().map(e ->{
                    StatusWrapper statusWrapper = StatusWrapper.builder().name(e.getFirstName()+" "+e.getLastName()).build();

                    List<VisaWrapper> visaWrappers = e.getVisaStatusList()
                            .stream()
                            .filter(visa -> visa.getActiveFlag() && visa.getEndDate().getTime() > System.currentTimeMillis())
                            .map(visa ->
                                    VisaWrapper.builder()
                                            .visaType(visa.getVisaType())
                                            .endDate(visa.getEndDate())
                                            .daysLeft(TimeUnit.DAYS.convert(visa.getEndDate().getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS))
                                            .build()
                            ).collect(Collectors.toList());

                    statusWrapper.setVisaWrapperList(visaWrappers);

                    return statusWrapper;
                }).collect(Collectors.toList());

        return statusWrapperList;
    }

    public List<ProfileWrapper> getAllSummaries(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "lastName");
        Page<Employee> employees = repository.findAll(PageRequest.of(page, size, sort));
        return summaryGenerator(employees);
    }

    public List<ProfileWrapper> getSummariesByEmail(int page, int size, String email) {
        Page<Employee> employees = repository.findAllByEmailOrderByLastName(email, PageRequest.of(page, size));
        return summaryGenerator(employees);
    }

    public List<ProfileWrapper> getSummariesByName(int page, int size, String firstName, String lastName) {
        Page<Employee> employees = repository.findAllByFirstNameAndLastName(firstName, lastName, PageRequest.of(page, size));
        return summaryGenerator(employees);
    }

    private List<ProfileWrapper> summaryGenerator(Page<Employee> employees){
        List<ProfileWrapper> summaries = employees.stream().map(e -> {
            Optional<VisaStatus> visaStatusOptional = e.getVisaStatusList()
                    .stream()
                    .filter(visa -> visa.getActiveFlag() && visa.getEndDate().getTime() > System.currentTimeMillis())
                    .findFirst();

            String title = visaStatusOptional.isPresent()? visaStatusOptional.get().getVisaType(): "N/A";

            ProfileWrapper summary = ProfileWrapper
                    .builder()
                    .id(e.getId())
                    .firstName(e.getFirstName())
                    .lastName(e.getLastName())
                    .email(e.getEmail())
                    .SSN(e.getSsn())
                    .phone(e.getCellPhone())
                    .title(title)
                    .build();

            return summary;
        }).collect(Collectors.toList());

        return summaries;
    }

    public List<Employee> findEmployeesByHouseId(Long houseId){
        return repository.findAllByHouseId(houseId);
    }


    public Integer getOccupantsByHouseId(Long houseId) {
        return repository.findAllByHouseId(houseId).size();
    }

    public String findEmployeeIdByUserId(Long userId) {
        Optional<Employee> employeeOptional = repository.findEmployeeByUserId(userId);
        if(!employeeOptional.isPresent()) return null;
        return employeeOptional.get().getId();
    }
}
