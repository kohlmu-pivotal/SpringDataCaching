package com.vmware.gemfire.caching.repository;

import com.vmware.gemfire.caching.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,String> {
}
