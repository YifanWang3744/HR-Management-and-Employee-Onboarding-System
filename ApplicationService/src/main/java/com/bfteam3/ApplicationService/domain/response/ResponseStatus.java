package com.bfteam3.ApplicationService.domain.response;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseStatus {
    private Boolean success;
    private String message;
}


