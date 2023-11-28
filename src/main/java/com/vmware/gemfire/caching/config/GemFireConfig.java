package com.vmware.gemfire.caching.config;

import com.vmware.gemfire.caching.repository.CustomerRepository;
import io.micrometer.common.util.StringUtils;
import org.apache.geode.cache.RegionShortcut;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.cache.client.SocketFactory;
import org.apache.geode.cache.client.proxy.ProxySocketFactories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.cache.config.EnableGemfireCaching;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableCachingDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.config.annotation.EnablePool;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@Configuration
@ClientCacheApplication
@EnableGemfireCaching
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
@EnablePool(name = "sniPool", socketFactoryBeanName = "myProxySocketFactory")
@EnablePdx
@EnableCachingDefinedRegions(clientRegionShortcut = ClientRegionShortcut.PROXY, serverRegionShortcut = RegionShortcut.PARTITION, poolName = "sniPool")
public class GemFireConfig {

  private static final Logger logger = LoggerFactory.getLogger(GemFireConfig.class);

  @Bean
  SocketFactory myProxySocketFactory(@Value("${gemfire.sni.host}") String host, @Value("${gemfire.sni.port}") int port) {
    if (port > 0 && !StringUtils.isBlank(host)) {
      logger.info("Connecting to GemFire load balancer proxy at " + host + ":" + port);
      return ProxySocketFactories.sni(host, port);
    }
    return SocketFactory.DEFAULT;
  }
}
