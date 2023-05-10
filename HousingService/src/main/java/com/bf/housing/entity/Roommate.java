package com.bf.housing.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Roommate {
    private String firstName;
    private String lastName;
    private String preferredName;
    private String phoneNumber;
}
