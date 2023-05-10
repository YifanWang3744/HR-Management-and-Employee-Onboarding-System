package com.bf.housing.domain.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EditCommentRequest {
    private Long id;
    private String comment;
}
