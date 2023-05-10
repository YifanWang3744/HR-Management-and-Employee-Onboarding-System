package com.bf.housing.repository;

import com.bf.housing.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    House findHouseById(Long id);
    List<House> findAll();
    List<House> findAllByFull(Boolean full);
}
