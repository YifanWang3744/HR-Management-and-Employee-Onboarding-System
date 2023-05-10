package com.bfteam3.ApplicationService.service;
import com.bfteam3.ApplicationService.domain.common.Car;
import com.bfteam3.ApplicationService.repository.Application.CarRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepo carRepo;

    public CarService(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    public Car saveCar(Car newCar){
        return carRepo.save(newCar);
    }

    public List<Car> findCarsByEmployeeId(String id){
        return  carRepo.findByEmployeeId(id);
    }
}
