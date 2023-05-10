package com.bf.housing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="facility_report_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FacilityReportDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "report_id")
    @ToString.Exclude
    @JsonIgnore
    private FacilityReport report;
    private String employeeId;
    private String comment;
    private Date createDate;
    private Date lastModificationDate;
}
