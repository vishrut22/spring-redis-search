package com.dcb.redissearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPooled;

@Configuration
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisConfiguration {
    private String host;
    private Integer port;

    @Bean
    JedisPooled jedisPool() {
        return new JedisPooled(host,port);
    }

}
