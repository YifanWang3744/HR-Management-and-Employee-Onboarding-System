package com.team3.employeeservice.request;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PersonalDocumentRequest {

    @Id
    @NotNull(message = "id cannot be empty")
    private String id;

    @NotNull(message = "path cannot be empty")
    private String path;

    @NotNull(message = "title cannot be empty")
    private String title;

    private String comment;
}