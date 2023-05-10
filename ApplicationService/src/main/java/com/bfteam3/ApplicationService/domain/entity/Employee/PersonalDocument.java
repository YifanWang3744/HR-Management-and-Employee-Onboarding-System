package com.bfteam3.ApplicationService.domain.entity.Employee;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonalDocument {
    @Id
    private String id;
    private String path;
    private String title;
    private String Comment;
    private Date createDate;
}

