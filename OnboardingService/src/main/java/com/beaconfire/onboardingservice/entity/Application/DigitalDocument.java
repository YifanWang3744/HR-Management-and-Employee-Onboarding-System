package com.beaconfire.onboardingservice.entity.Application;
import lombok.*;
import org.springframework.data.annotation.Id;


import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DigitalDocument {
    @Id
    private Long id;
    private DocumentType type;
    private Boolean isRequired;
    private String path;
    private String title;
    private String description;
    private Date createDate;

}
