package com.bfteam3.ApplicationService.domain.response;
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
