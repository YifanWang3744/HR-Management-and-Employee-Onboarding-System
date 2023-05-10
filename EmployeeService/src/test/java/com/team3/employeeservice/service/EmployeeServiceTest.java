package com.team3.employeeservice.service;

import com.team3.employeeservice.domain.Employee;
import com.team3.employeeservice.domain.VisaStatus;
import com.team3.employeeservice.exception.EmployeeNotFoundException;
import com.team3.employeeservice.repository.EmployeeRepository;
import com.team3.employeeservice.resultWrapper.ProfileWrapper;
import com.team3.employeeservice.resultWrapper.StatusWrapper;
import com.team3.employeeservice.resultWrapper.VisaWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository repository;
    
    @InjectMocks
    EmployeeService service;

    @Test
    void test_findAlEmployees_success() {
        List<Employee> expected = new ArrayList<>();
        expected.add(new Employee("1", 1L, "Peppa", "Pig", null,
                null, null, "peppa@piggy.com", "1112223344",
                null, "Female", null, new Date(System.currentTimeMillis()),
                null, null, null, null, null,
                null, null, null, null));
        expected.add(new Employee("2", 2L, "George", "Pig", null,
                null, null, "george@piggy.com", "2223334455",
                null, "Male", null, new Date(System.currentTimeMillis()),
                null, null, null, null, null,
                null, null, null, null));
        expected.add(new Employee("3", 3L, "Rebecca", "Rabbit", null,
                null, null, "rebecca@rabbit.com", "3334445566",
                null, "Female", null, new Date(System.currentTimeMillis()),
                null, null, null, null, null,
                null, null, null, null));

        Mockito.when(repository.findAll()).thenReturn(expected);
        assertEquals(expected, service.findAllEmployees());
    }

    @Test
    void test_findEmployeeById_success() throws EmployeeNotFoundException {
        Optional<Employee> expected = Optional.ofNullable(
                new Employee("1", 1L, "Peppa", "Pig", null,
                null, null, "peppa@piggy.com", "1112223344",
                null, "Female", null, new Date(System.currentTimeMillis()),
                null, null, null, null, null,
                null, null, null, null));

        Mockito.when(repository.findById("1")).thenReturn(expected);
        assertEquals(expected.get(), service.findEmployeeById("1"));
    }

    @Test
    void test_findEmployeeById_fail() {
        Mockito.when(repository.findById("1")).thenReturn(Optional.ofNullable(null));
        assertThrows(EmployeeNotFoundException.class, () -> service.findEmployeeById("1"));
    }

    @Test
    void test_findEmployeeByEmail_success() throws EmployeeNotFoundException {
        Employee expected = new Employee("1", 1L, "Peppa", "Pig", null,
                null, null, "peppa@piggy.com", "1112223344",
                null, "Female", null, new Date(System.currentTimeMillis()),
                null, null, null, null, null,
                null, null, null, null);

        Mockito.when(repository.findEmployeeByEmail("peppa@piggy.com")).thenReturn(expected);
        assertEquals(expected, service.findEmployeeByEmail("peppa@piggy.com"));
    }

    @Test
    void test_findEmployeeByEmail_fail() throws EmployeeNotFoundException {
        Mockito.when(repository.findEmployeeByEmail("peppa@piggy.com")).thenReturn(null);
        assertThrows(EmployeeNotFoundException.class, () -> service.findEmployeeByEmail("peppa@piggy.com"));
    }

    @Test
    void test_getVisaStatuses() {
        // mock employees
        Employee e1 = new Employee();
        e1.setId("1");
        e1.setFirstName("Peppa");
        e1.setLastName("Pig");
        e1.setVisaStatusList(new ArrayList<>(Arrays.asList(
            new VisaStatus(
                    "1", "F1(CPT/OPT)", true, null,
                    new Date(System.currentTimeMillis()+1000*60*60*24*200), null)
        )));
        Employee e2 = new Employee();
        e2.setId("2");
        e2.setFirstName("Harry");
        e2.setLastName("Potter");
        e2.setVisaStatusList(new ArrayList<>(Arrays.asList(
            new VisaStatus(
                    "1", "H1B", false, null,
                    new Date(System.currentTimeMillis()+1000*60*60*24*400), null)
        )));
        Employee e3 = new Employee();
        e3.setId("3");
        e3.setFirstName("Hello");
        e3.setLastName("Kitty");
        e3.setVisaStatusList(new ArrayList<>(Arrays.asList(
            new VisaStatus(
                    "1", "H1B", true, null,
                    new Date(System.currentTimeMillis()-1000*60*60*24*400), null)
        )));
        List<Employee> employees = new ArrayList<>(Arrays.asList(e1, e2, e3));
        Mockito.when(repository.findAll(PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(employees));

        // get actual status wrapper list
        List<StatusWrapper> actualStatusList = service.getVisaStatuses(0, 10);
        assertEquals(3, actualStatusList.size());

        // compare to the expected values
        for (int i = 0; i < 2; i++) {
            Employee e = employees.get(i);
            StatusWrapper s = actualStatusList.get(i);
            assertEquals(e.getFirstName()+" "+e.getLastName(), s.getName());

            VisaStatus expectedVisaStatus = e.getVisaStatusList().get(0);
            if (i == 0) {
                VisaWrapper actualVisaStatus = s.getVisaWrapperList().get(0);
                assertEquals(e.getVisaStatusList().size(), s.getVisaWrapperList().size());
                assertEquals(expectedVisaStatus.getVisaType(), actualVisaStatus.getVisaType());
                assertEquals(expectedVisaStatus.getEndDate(), actualVisaStatus.getEndDate());
                assertEquals(TimeUnit.DAYS.convert(expectedVisaStatus.getEndDate().getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS),
                        actualVisaStatus.getDaysLeft());
            } else {
                assertEquals(0, s.getVisaWrapperList().size());
            }
        }
    }

    @Test
    void test_getAllSummaries() {
        // mock employees
        Employee e1 = new Employee();
        e1.setLastName("A");
        Employee e2 = new Employee();
        e2.setLastName("B");
        Employee e3 = new Employee();
        e3.setLastName("C");

        Page<Employee> page = new PageImpl<>(Arrays.asList(e1, e2, e3));

        Mockito.when(repository.findAll(PageRequest.of(0, 3, Sort.Direction.ASC, "lastName"))).thenReturn(page);
        List<ProfileWrapper> result = service.getAllSummaries(0, 3);

        assertEquals(3, result.size());
        assertEquals("A", result.get(0).getLastName());
        assertEquals("B", result.get(1).getLastName());
        assertEquals("C", result.get(2).getLastName());
    }

    @Test
    void test_getSummariesByEmail() {
        Employee e1 = new Employee();
        e1.setLastName("A");
        e1.setEmail("a@b.com");
        Employee e2 = new Employee();
        e2.setLastName("B");
        e2.setEmail("a@b.com");

        Page<Employee> page = new PageImpl<>(Arrays.asList(e1, e2));
        Mockito.when(repository.findAllByEmailOrderByLastName("a@b.com", PageRequest.of(0, 3))).thenReturn(page);

        List<ProfileWrapper> result = service.getSummariesByEmail(0, 3, "a@b.com");
        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getLastName());
        assertEquals("B", result.get(1).getLastName());
    }

    @Test
    void test_getSummariesByName() {
        Employee e1 = new Employee();
        e1.setFirstName("A");
        e1.setLastName("a");

        Page<Employee> page = new PageImpl<>(Arrays.asList(e1));
        Mockito.when(repository.findAllByFirstNameAndLastName("A", "a", PageRequest.of(0, 3))).thenReturn(page);

        List<ProfileWrapper> result = service.getSummariesByName(0, 3, "A", "a");
        assertEquals(1, result.size());
        assertEquals("a", result.get(0).getLastName());
        assertEquals("A", result.get(0).getFirstName());
    }
}
