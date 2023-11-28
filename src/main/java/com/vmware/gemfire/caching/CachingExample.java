package com.vmware.gemfire.caching;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.gemfire.caching.domain.Customer;
import com.vmware.gemfire.caching.service.CustomerService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SpringBootApplication
@ComponentScan(basePackages = "com.vmware.gemfire.caching.config")
public class CachingExample {

  public static void main(String[] args) {
    new SpringApplicationBuilder(CachingExample.class).web(WebApplicationType.NONE).run(args);
  }

  @Bean
  ApplicationRunner applicationRunner(CustomerService customerService) {
    return (arguments) -> {
      toStream("/data/sample_contact_info.json", Customer.class)
          .forEach(customer -> customerService.cachePut(customer));
    };
  }

  protected <T> Stream<T> toStream(String path, Class<T> classId) throws IOException {
    InputStream inputStream = getClass().getResourceAsStream(path);
    ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    JsonParser jsonParser = objectMapper.getFactory().createParser(inputStream);

    if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
      throw new IllegalStateException("Not an array");
    }

    jsonParser.nextToken(); // advance jsonParser to start of first object
    Iterator<T> iterator = jsonParser.readValuesAs(classId);

    return StreamSupport.stream(
        Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
        false);
  }
}
