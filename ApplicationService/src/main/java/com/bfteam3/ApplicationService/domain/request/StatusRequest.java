package com.bfteam3.ApplicationService.domain.request;
import lombok.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class StatusRequest {
    private String status;
    private String feedback;
}
