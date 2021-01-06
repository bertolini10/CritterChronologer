package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;

import java.util.List;

public interface ICustomerService {

    Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer findById(long customerId);
}
