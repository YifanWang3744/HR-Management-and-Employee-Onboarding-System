package com.beaconfire.onboardingservice.domain.Application;
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
