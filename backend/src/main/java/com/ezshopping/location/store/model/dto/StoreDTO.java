package com.ezshopping.location.store.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StoreDTO {

    private String id;
    private String storeName;
    private String address;
}
