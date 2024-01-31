package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class User {
    private int id;
    private String name;
    private String email;
    private String passwordHash;
    private String role;
}
