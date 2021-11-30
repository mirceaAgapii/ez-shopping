package com.ezshopping.order;

import com.ezshopping.model.AbstractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderService extends AbstractService<OrderEntity> {
    @Override
    public List<OrderEntity> getAll() {
        return null;
    }
}
