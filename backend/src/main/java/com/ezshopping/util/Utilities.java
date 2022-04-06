package com.ezshopping.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class Utilities {

    public static String getNewUuid() {
        return UUID.randomUUID().toString();
    }
}
