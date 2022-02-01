package com.ezshopping.location.cart.model.entity;

import com.ezshopping.location.model.entity.Location;
import com.ezshopping.product.model.entity.Product;
import com.ezshopping.user.model.entity.User;
import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ez_cart")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Builder
public class Cart extends Location {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "ez_cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> productList;
}
