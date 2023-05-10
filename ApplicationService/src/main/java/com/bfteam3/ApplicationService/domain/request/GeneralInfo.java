package com.bfteam3.ApplicationService.domain.request;
import com.bfteam3.ApplicationService.domain.common.Address;
import com.bfteam3.ApplicationService.domain.common.Car;
import com.bfteam3.ApplicationService.domain.common.Name;
import com.bfteam3.ApplicationService.domain.common.PhoneNumber;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralInfo {
    private Name name;
    private Address address;
    private PhoneNumber phoneNumber;
    private Car car;
    private String ssn;
    private String dob;
    private String gender;
    private CheckPerm perm;

}
