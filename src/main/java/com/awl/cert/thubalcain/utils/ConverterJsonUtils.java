package com.awl.cert.thubalcain.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

@Slf4j
public class ConverterJsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static <T> T readFileToMapper(String path, Class<T> clazz) {
        try(InputStream inputStream = ResourceUtils.class.getResourceAsStream("/" + path)) {
            if (Objects.isNull(inputStream)) {
                throw new FileNotFoundException("파일을 찾지 못 했습니다.");
            }

            byte[] readBytes = inputStream.readAllBytes();
            String fromJson = new String(readBytes);

            return objectMapper.readValue(fromJson, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String writeToJsonString(T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    protected ConverterJsonUtils(){}
}
