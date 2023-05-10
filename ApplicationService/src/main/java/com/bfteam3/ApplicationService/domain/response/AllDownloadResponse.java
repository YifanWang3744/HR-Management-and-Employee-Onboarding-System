package com.bfteam3.ApplicationService.domain.response;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AllDownloadResponse {
    private List<DownloadResponse> downloads;
}
