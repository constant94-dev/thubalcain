package com.awl.cert.thubalcain.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
@Profile({"dev","prod"})
public class RedisConfig {
    private final GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 기본 key,value serializer 설정
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new CompositeSerializer());

        // hash 자료구조 serializer 설정
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new CompositeSerializer());

        return template;
    }

    private class CompositeSerializer implements RedisSerializer<Object> {
        @Override
        public byte[] serialize(Object value) throws SerializationException {
            log.info("custom serialize run!!");
            log.info("custom serialize value 확인: {}", value);

            return jsonRedisSerializer.serialize(value);
        }

        @Override
        public Object deserialize(byte[] bytes) throws SerializationException {
            if (bytes == null || bytes.length == 0) return null;

            return jsonRedisSerializer.deserialize(bytes);
        }
    }
}
