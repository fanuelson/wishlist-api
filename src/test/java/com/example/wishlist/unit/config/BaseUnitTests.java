package com.example.wishlist.unit.config;

import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class BaseUnitTests {

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
}
