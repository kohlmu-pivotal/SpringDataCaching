package com.vmware.gemfire.caching.service;

import com.vmware.gemfire.caching.domain.Customer;
import com.vmware.gemfire.caching.transaction.AtmTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsumerService {

  private LookAsideCachedCustomerService lookAsideCachedCustomerService;
  Logger logger = LoggerFactory.getLogger(ConsumerService.class);

  public ConsumerService() {
    logger.info("Creating consumer service");
  }

  public ConsumerService(LookAsideCachedCustomerService lookAsideCachedCustomerService) {
    this.lookAsideCachedCustomerService = lookAsideCachedCustomerService;
  }

  public void receiveMessage(AtmTransaction tx) {
    String account = tx.fromAcct1 + tx.fromAcct2;
    logger.info("Received transaction <" + tx.processId + "> of " + account);
    Optional<Customer> customer = lookAsideCachedCustomerService.findById(account);
    if (customer.isEmpty()) {
      logger.info("Customer " + account + " not found");
    }
    // TODO (Production): send this data down the Enterprise Message Hub / Persist / Do whatever
  }
}
