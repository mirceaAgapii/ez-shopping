package com.ezshopping.stock.model.entity;

import com.ezshopping.location.model.entity.Location;
import com.ezshopping.common.AbstractEntity;
import com.ezshopping.product.model.entity.Product;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "ez_stock")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@Builder
public class Stock extends AbstractEntity {

    @Column(name = "location_type")
    private String locationType;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "quantity")
    private Double quantity;
}
