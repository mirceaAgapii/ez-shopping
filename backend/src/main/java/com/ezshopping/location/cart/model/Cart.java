package com.ezshopping.location.cart.model;

import com.ezshopping.location.model.Location;
import com.ezshopping.product.model.Product;
import com.ezshopping.user.model.User;
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
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false, insertable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "ez_cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> productList;
}
