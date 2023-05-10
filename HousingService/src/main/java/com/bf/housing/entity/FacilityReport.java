package com.bf.housing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facility_report")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FacilityReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;
    @Column(name = "employee_id")
    private String employeeId;
    @ManyToOne
    @JoinColumn(name = "facility_id")
    @JsonIgnore
    @ToString.Exclude
    private Facility facility;
    private String title;
    private String description;
    private Date createDate;
    @Enumerated(EnumType.STRING)
    private ReportStatus status;
    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    private List<FacilityReportDetail> comments = new ArrayList<>();
}
