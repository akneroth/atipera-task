package com.example.test.demo.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    @Getter @Setter private int status;
    @Getter @Setter private String message;
}
