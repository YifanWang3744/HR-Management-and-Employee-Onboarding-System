package com.beaconfire.onboardingservice.domain.Application;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DownloadResponse {
    private String id;
    private String download;
}
