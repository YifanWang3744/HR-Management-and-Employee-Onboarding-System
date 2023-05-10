package com.team3.employeeservice.response;

import com.team3.employeeservice.domain.PersonalDocument;
import lombok.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PersonalDocumentListResponse {
    private ResponseStatus status;
    private List<PersonalDocument> personalDocumentList;
}
