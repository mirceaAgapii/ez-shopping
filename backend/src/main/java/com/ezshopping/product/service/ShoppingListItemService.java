package com.ezshopping.product.service;

import com.ezshopping.product.mapper.ShoppingListMapper;
import com.ezshopping.product.model.dto.ShoppingListDTO;
import com.ezshopping.product.model.entity.ShoppingListItem;
import com.ezshopping.product.repository.ShoppingListItemRepository;
import com.ezshopping.user.model.entity.User;
import com.ezshopping.user.service.UserService;
import com.ezshopping.util.Utilities;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingListItemService {

    private final ShoppingListItemRepository shoppingListItemRepository;
    private final UserService userService;
    private final ShoppingListMapper mapper;

    public ShoppingListItemService(ShoppingListItemRepository shoppingListItemRepository,
                                   UserService userService,
                                   ShoppingListMapper mapper) {
        this.shoppingListItemRepository = shoppingListItemRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Transactional
    public void saveShoppingList(List<ShoppingListDTO> shoppingListDTOS) {
        String userId = shoppingListDTOS.stream().map(ShoppingListDTO::getUserId).findFirst().orElseThrow();
        User user = userService.getUserById(userId);
        shoppingListDTOS.forEach(sl -> {
            ShoppingListItem newItem = new ShoppingListItem(user, sl.getProductName(), sl.isActive());
            newItem.setId(Utilities.getNewUuid());
            shoppingListItemRepository.save(newItem);
        });
    }

    @Transactional
    public ShoppingListDTO saveShoppingListItem(ShoppingListDTO shoppingListDTO) {
        User user = userService.getUserById(shoppingListDTO.getUserId());
        if (!shoppingListItemRepository.existsByProductName(shoppingListDTO.getProductName())) {
            ShoppingListItem newItem = new ShoppingListItem(user, shoppingListDTO.getProductName(), shoppingListDTO.isActive());
            newItem.setId(Utilities.getNewUuid());
            shoppingListItemRepository.save(newItem);
            return mapper.map(newItem);
        } else {
            throw new EntityExistsException("Item is already in list");
        }
    }

    public List<ShoppingListDTO> getShoppingListForUser(String userId) {
        User user = userService.getUserById(userId);
        List<ShoppingListItem> shoppingListItems = shoppingListItemRepository.findAllByUser(user).orElseThrow();
        return shoppingListItems.stream().map(mapper::map).collect(Collectors.toList());
    }

    @Transactional
    public void deleteShoppingListItem(String itemId) {
        ShoppingListItem shoppingListItem = shoppingListItemRepository.findById(itemId).orElseThrow();
        shoppingListItemRepository.delete(shoppingListItem);
    }

    @Transactional
    public void updateStatus(String itemId, boolean isActive) {
        ShoppingListItem shoppingListItem = shoppingListItemRepository.findById(itemId).orElseThrow();
        shoppingListItem.setActive(isActive);
        shoppingListItemRepository.save(shoppingListItem);
    }
}
