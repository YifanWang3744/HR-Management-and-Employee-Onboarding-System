package com.bf.housing.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PersonalDocument {
    private String id;
    private String path;
    private String title;
    private String comment;
    @CreatedDate
    private Date createDate;
}
