package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.ex.MyExecption;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CustomerService implements ICustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new MyExecption("Not have a Customer with ID:"+customerId));
        return customer;

    }
}