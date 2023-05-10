//package com.bfteam3.ApplicationService.service;
//import com.bfteam3.ApplicationService.domain.common.Car;
//import com.bfteam3.ApplicationService.repository.Application.CarRepo;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
//
//@ExtendWith(MockitoExtension.class)
//public class CarServiceTest {
//    @Mock
//    private CarRepo carRepo;
//
//    @InjectMocks
//    private CarService carService;
//
//    @Test
//    void test_saveCar_success() {
//        Car newCar = new Car((long)101, "dfvbdf", "2347", "dgvae", "gvedvgef");
//        Mockito.when(carRepo.save(newCar)).thenReturn(newCar);
//        assertEquals(newCar, carService.saveCar(newCar));
//    }
//
//    @Test
//    void test_saveCar_fail() {
//        Car newCar = new Car((long)101, "dfvbdf", "2347", "dgvae", "gvedvgef");
//        Car otherCar = new Car((long)124, "fdhdfh", "3456", "dgvae", "gvedvgef");
//        Mockito.when(carRepo.save(newCar)).thenReturn(otherCar);
//        assertNotEquals(newCar, carService.saveCar(newCar));
//    }
//
//    @Test
//    void test_findCarByEmployeeId_success() {
//        Car newCar = new Car((long)101, "dfvbdf", "2347", "dgvae", "gvedvgef");
//        Car otherCar = new Car((long)124, "fdhdfh", "3456", "dgvae", "gvedvgef");
//        List<Car> expected = new ArrayList<>();
//        expected.add(newCar);
//        expected.add(otherCar);
//        Mockito.when(carRepo.findByEmployeeId("64654")).thenReturn(expected);
//        assertEquals(expected, carService.findCarsByEmployeeId("64654"));
//    }
//
//    @Test
//    void test_findCarByEmployeeId_fail() {
//        Car newCar = new Car((long)101, "dfvbdf", "2347", "dgvae", "gvedvgef");
//        Car otherCar = new Car((long)124, "fdhdfh", "3456", "dgvae", "gvedvgef");
//        List<Car> expected = new ArrayList<>();
//        expected.add(newCar);
//        expected.add(otherCar);
//        Mockito.when(carRepo.findByEmployeeId("64654")).thenReturn(expected);
//        assertNotEquals(new ArrayList<Car>(), carService.findCarsByEmployeeId("64654"));
//    }
//
//}
