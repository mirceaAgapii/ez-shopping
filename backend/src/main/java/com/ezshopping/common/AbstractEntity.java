package com.ezshopping.common;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@MappedSuperclass
@Setter
@Getter
public abstract class AbstractEntity {

    @Id
    private String id;
}
