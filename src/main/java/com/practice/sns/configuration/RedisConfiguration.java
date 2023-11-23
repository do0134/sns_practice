package com.practice.sns.configuration;

import com.practice.sns.model.User;
import io.lettuce.core.RedisURI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfiguration {

    private final RedisProperties redisProperties;
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
//        RedisURI redisURI = RedisURI.create(redisProperties.getUrl());
//        org.springframework.data.redis.connection.RedisConfiguration configuration = LettuceConnectionFactory.createRedisConfiguration(redisURI);
//        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(configuration);
//        lettuceConnectionFactory.afterPropertiesSet();
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, User> userRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
        return redisTemplate;
    }
}
