package com.ezshopping.product.model;

import com.ezshopping.location.cart.CartEntity;
import com.ezshopping.common.AbstractEntity;
import com.ezshopping.order.orderline.OrderLineEntity;
import com.ezshopping.stock.model.StockEntity;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ez_product")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class Product extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "barcode")
    private String barcode;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<StockEntity> stockEntities;

    @JsonIgnore
    @ManyToMany(mappedBy = "productList")
    private List<CartEntity> cartList;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<OrderLineEntity> orderLines;
}
