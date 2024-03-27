package com.example.wishlist.usecases.validators;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Configuration
@ConfigurationProperties(prefix = "validation")
@Validated
public class ValidationProperties {

    @NotNull
    private Integer maxProducts;
}
