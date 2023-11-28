package com.vmware.gemfire.caching.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.vmware.gemfire.caching.domain","com.vmware.gemfire.caching.service"})
public class GenericConfig {
}
