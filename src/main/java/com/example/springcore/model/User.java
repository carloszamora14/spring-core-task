package com.example.springcore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Identifiable {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean active;
}