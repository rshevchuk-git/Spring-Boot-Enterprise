package com.ordersmanagement.crm.services;

import com.ordersmanagement.crm.dao.business.OrderKindRepository;
import com.ordersmanagement.crm.models.entities.Order;
import com.ordersmanagement.crm.models.entities.OrderKind;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderKindService {

    private final OrderKindRepository orderKindRepository;
    private final OrderService orderService;

    public List<OrderKind> getAllOrderKinds() {
        return orderKindRepository.findAll();
    }
    
    public OrderKind saveOrderKind(OrderKind newOrderKind) {
        return orderKindRepository.save(newOrderKind);
    }

    public boolean deleteOrderKind(Integer orderKindId) {
        if (orderKindRepository.existsByKindId(orderKindId)) {
            orderKindRepository.deleteById(orderKindId);
            return true;
        } else {
            return false;
        }
    }

    public void replaceOrderKinds(OrderKind replaceKind, OrderKind newKind) {
        List<Order> ordersByKind = orderService.getAllByOrderKind(replaceKind);
        ordersByKind.forEach(order -> order.setOrderKind(newKind));
        orderService.saveAll(ordersByKind);
    }
}
