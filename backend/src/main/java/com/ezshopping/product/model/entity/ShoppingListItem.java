package com.ezshopping.product.model.entity;

import com.ezshopping.common.AbstractEntity;
import com.ezshopping.user.model.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ez_shopping_list_items")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class ShoppingListItem extends AbstractEntity {

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "active")
    private boolean active;
}
