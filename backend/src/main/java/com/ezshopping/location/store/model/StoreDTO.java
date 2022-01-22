package com.ezshopping.location.store.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StoreDTO {

    private String id;
    private String storeName;
    private String address;
}
