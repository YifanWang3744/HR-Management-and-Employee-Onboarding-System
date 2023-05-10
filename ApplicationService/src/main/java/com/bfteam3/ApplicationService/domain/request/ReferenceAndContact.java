package com.bfteam3.ApplicationService.domain.request;
import com.bfteam3.ApplicationService.domain.common.Contact;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReferenceAndContact {
    private Contact reference;
    private Contact contact;

}
