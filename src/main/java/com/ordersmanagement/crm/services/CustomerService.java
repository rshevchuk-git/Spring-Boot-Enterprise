package com.ordersmanagement.crm.services;

import com.ordersmanagement.crm.dao.orders.CustomerRepository;
import com.ordersmanagement.crm.models.entities.CustomerEntity;
import com.ordersmanagement.crm.utils.PaymentUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    public boolean existsById(Integer customerId) {
        return customerRepository.existsById(customerId);
    }

    public Optional<CustomerEntity> getById(Integer customerId) {
        return customerRepository.findById(customerId);
    }

    public CustomerEntity saveCustomer(CustomerEntity newCustomer) {
        return customerRepository.save(newCustomer);
    }

    public boolean deleteCustomer(Integer customerID){
        if(existsById(customerID)) {
            customerRepository.deleteById((customerID));
            return true;
        } else {
            return false;
        }
    }

    public int paidOnCustomerBalance(Integer customerId, String receiver){
        return customerRepository.findAllByCustomerIdAndPayLogContaining(customerId, receiver)
                .stream()
                .map(CustomerEntity::getPayLog)
                .map(log -> log.split("\n"))
                .flatMap(Arrays::stream)
                .filter(log -> log.contains(receiver))
                .reduce(0, (currentVal, log) -> currentVal + PaymentUtils.getSumFromLog(log), Integer::sum);
    }
}
