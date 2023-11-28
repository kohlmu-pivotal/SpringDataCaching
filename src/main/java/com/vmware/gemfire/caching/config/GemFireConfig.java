package com.vmware.gemfire.caching.config;

import com.vmware.gemfire.caching.repository.CustomerRepository;
import org.apache.geode.cache.RegionShortcut;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.cache.config.EnableGemfireCaching;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableCachingDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@Configuration
@ClientCacheApplication
@EnableGemfireCaching
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
@EnablePdx
@EnableCachingDefinedRegions(clientRegionShortcut = ClientRegionShortcut.PROXY,serverRegionShortcut = RegionShortcut.PARTITION)
public class GemFireConfig {
}
