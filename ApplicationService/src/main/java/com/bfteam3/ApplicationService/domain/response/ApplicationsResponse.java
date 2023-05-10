package com.bfteam3.ApplicationService.domain.response;

import com.bfteam3.ApplicationService.domain.entity.Application.ApplicationWorkFlow;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApplicationsResponse {
    private ResponseStatus status;
    private List<ApplicationWorkFlow> pending;
    private List<ApplicationWorkFlow> accepted;
    private List<ApplicationWorkFlow> rejected;
}
