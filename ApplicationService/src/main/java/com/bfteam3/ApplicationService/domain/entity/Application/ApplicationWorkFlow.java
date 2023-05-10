package com.bfteam3.ApplicationService.domain.entity.Application;
import com.bfteam3.ApplicationService.domain.entity.ApplicationStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "application_workflow")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ApplicationWorkFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "employee_id")
    private String employeeId;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "last_modification_date")
    private Date lastModificationDate;
    @Column(name = "status")
    private ApplicationStatus applicationStatus;
    @Column(name = "comment")
    private String comment;
}

