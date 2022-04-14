package com.example.spring3_demo.model;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class User {
    @Schema(description = "name", example = "dean")
    private String name;
    @Schema(description = "age", example = "12", required = false)
    private Integer age;
}
