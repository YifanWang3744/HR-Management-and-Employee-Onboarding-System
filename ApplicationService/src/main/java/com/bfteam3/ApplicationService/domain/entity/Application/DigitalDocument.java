package com.bfteam3.ApplicationService.domain.entity.Application;
import com.bfteam3.ApplicationService.domain.entity.DocumentType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "digital_document")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DigitalDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type")
    private DocumentType type;
    @Column(name = "is_required")
    private Boolean isRequired;
    @Column(name = "path")
    private String path;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "create_date")
    private Date createDate;

}
