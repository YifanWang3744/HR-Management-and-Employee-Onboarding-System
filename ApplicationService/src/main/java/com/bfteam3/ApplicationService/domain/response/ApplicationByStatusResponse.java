package com.bfteam3.ApplicationService.domain.response;

import com.bfteam3.ApplicationService.domain.entity.Application.ApplicationWorkFlow;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApplicationByStatusResponse {
    private ResponseStatus status;
    private List<ApplicationWorkFlow> applications;
}
