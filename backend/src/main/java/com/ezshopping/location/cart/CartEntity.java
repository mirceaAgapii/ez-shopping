package com.ezshopping.location.cart;

import com.ezshopping.model.AbstractEntity;
import com.ezshopping.product.ProductEntity;
import com.ezshopping.user.UserEntity;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ez_cart")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CartEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", updatable = false, insertable = false)
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "ez_cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> productList;
}
