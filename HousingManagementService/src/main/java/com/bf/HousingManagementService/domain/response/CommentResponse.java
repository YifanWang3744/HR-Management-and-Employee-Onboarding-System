package com.bf.HousingManagementService.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class CommentResponse {
    private String description;
    private String author;
    private Date lastModifiedTime;
}
