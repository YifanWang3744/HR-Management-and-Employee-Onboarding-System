package com.bfteam3.ApplicationService.domain.common;


//import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class Name {


    private String firstname;

    private String lastname;
    private String middlename;
    private String preferredname;
}
