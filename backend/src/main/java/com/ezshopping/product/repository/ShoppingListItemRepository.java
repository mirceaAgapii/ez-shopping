package com.ezshopping.product.repository;

import com.ezshopping.product.model.entity.ShoppingListItem;
import com.ezshopping.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, String> {

    Optional<List<ShoppingListItem>> findAllByUser(User user);
    boolean existsByProductNameAndUser(String productName, User user);
}
