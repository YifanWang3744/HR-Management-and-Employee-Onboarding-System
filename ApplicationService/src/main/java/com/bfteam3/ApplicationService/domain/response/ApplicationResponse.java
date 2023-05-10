package com.bfteam3.ApplicationService.domain.response;

import com.bfteam3.ApplicationService.domain.common.ApplicationForm;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ApplicationResponse {
    private ResponseStatus status;
    private ApplicationForm form;
}
