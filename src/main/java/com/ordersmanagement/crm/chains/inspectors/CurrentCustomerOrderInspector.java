package com.ordersmanagement.crm.chains.inspectors;

import com.ordersmanagement.crm.exceptions.CustomerNotFoundException;
import com.ordersmanagement.crm.exceptions.OrderNotFoundException;
import com.ordersmanagement.crm.models.entities.Order;
import com.ordersmanagement.crm.services.CustomerPaymentsManager;
import com.ordersmanagement.crm.services.OrderPaymentsManager;
import com.ordersmanagement.crm.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentCustomerOrderInspector implements OrderInspector {

    private final OrderService orderService;
    private final CustomerPaymentsManager customerPaymentsManager;
    private final OrderPaymentsManager orderPaymentsManager;

    @Override
    public Order process(Order order) throws OrderNotFoundException, CustomerNotFoundException {
        if (orderService.isCustomerChanged(order)) {
            orderPaymentsManager.removePaymentsFrom(order);
            customerPaymentsManager.payFromCustomerBalance(order);
        }
        return order;
    }
}
