package com.vmware.gemfire.caching.service;

import com.vmware.gemfire.caching.domain.Customer;
import com.vmware.gemfire.caching.repository.CustomerRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Cacheable(cacheNames = "Customers",key = "#result.accountNumber")
  public Customer cacheFind(String accountNumber){
    return customerRepository.findById(accountNumber).get();
  }

  @CachePut(cacheNames = "Customers", key = "#result.accountNumber")
  public Customer cachePut(Customer customer) {
    return customer;
  }

  public Customer cacheGet(String id) {
    return null;
  }
}
