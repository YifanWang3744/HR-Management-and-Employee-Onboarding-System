package com.bf.housing.service;

import com.bf.housing.domain.request.NewHouseRequest;
import com.bf.housing.domain.resultWrapper.HouseDetail;
import com.bf.housing.domain.resultWrapper.HouseSummary;
import com.bf.housing.entity.*;
import com.bf.housing.exception.NoAvailableHouseException;
import com.bf.housing.repository.HouseRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HouseService {
    private HouseRepository houseRepository;

    @Autowired
    public void setHouseRepository(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    /**
     * for testing purpose
     * @return
     */
    @Transactional
    public House findHouseById(Long id) {
        return houseRepository.findHouseById(id);
    }

    @Transactional
    public List<House> findAll() {
        return houseRepository.findAll();
    }

    @Transactional
    public House addHouse(){
        Landlord landlord = Landlord.builder()
                .firstName("lenny").lastName("feign")
                .cellphone("123456789").email("xxx@hotmail.com")
                .houses(new HashSet<>())
                .build();
        House house = House.builder()
                .address("address")
                .maxOccupant(5)
                .full(false)
                .facilities(new HashSet<>())
                .landlord(landlord)
                .build();
        landlord.getHouses().add(house);
        Facility facility = Facility
                .builder()
                .house(house)
                .type("type")
                .description("desc")
                .quantity(5L)
                .reports(new HashSet<>())
                .build();
        house.getFacilities().add(facility);
        FacilityReport report = FacilityReport
                .builder()
                .facility(facility)
                .title("title")
                .description("report desc")
                .status(ReportStatus.Open)
                .build();
        FacilityReportDetail detail = FacilityReportDetail
                .builder()
                .report(report)
                .employeeId("randomid")
                .comment("comment")
                .createDate(new Date(System.currentTimeMillis()))
                .lastModificationDate(null)
                .build();
        report.setComments(Arrays.asList(detail));
        facility.getReports().add(report);

        houseRepository.save(house);
        houseRepository.flush();

        return house;
    }

    @Transactional
    public void addHouse(NewHouseRequest request){
        Landlord landlord = Landlord.builder()
                .firstName(request.getFirstName()).lastName(request.getLastName())
                .cellphone(request.getCellphone()).email(request.getEmail())
                .houses(new HashSet<>())
                .build();
        House house = House.builder()
                .address(request.getAddress())
                .maxOccupant(request.getMaxOccupants())
                .full(false)
                .facilities(request.getFacilities())
                .landlord(landlord)
                .build();
        landlord.getHouses().add(house);
        request.getFacilities().forEach(f->f.setHouse(house));
        houseRepository.save(house);
    }

    @Transactional
    public Long assign() throws NoAvailableHouseException {
        House house = houseRepository.findAllByFull(false).stream().findAny().orElseThrow(()->new NoAvailableHouseException());
        house.setMaxOccupant(house.getMaxOccupant()-1);
        if(house.getMaxOccupant() == 0) house.setFull(true);
        houseRepository.save(house);
        return house.getId();
    }

    @Transactional
    public void deleteHouse(Long houseId) {
        houseRepository.deleteById(houseId);
    }

    @Transactional
    public List<HouseSummary> viewHouses() {
        List<House> houses = houseRepository.findAll();
        return houses.stream()
                .map(h -> HouseSummary.builder().houseId(h.getId()).houseAddress(h.getAddress()).landlord(h.getLandlord()).build())
                .collect(Collectors.toList());
    }

    @Transactional
    public HouseDetail viewHouseById(Long houseId, int page, int size) {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if(!houseOptional.isPresent()) return null;
        House house = houseOptional.get();
        Set<Facility> facilities = house.getFacilities();
        List<FacilityReport> reports = facilities.stream().map(f -> f.getReports()).flatMap(r -> r.stream()).skip(page*size).limit(size).collect(Collectors.toList());
        reports.forEach(r->r.getComments());
        reports.sort((r1, r2)->r2.getCreateDate().compareTo(r1.getCreateDate()));
        return HouseDetail
                .builder()
                .houseAddress(house.getAddress())
                .landlord(house.getLandlord())
                .facilities(new ArrayList<>(house.getFacilities()))
                .facilityReports(reports)
                .build();
    }
}
