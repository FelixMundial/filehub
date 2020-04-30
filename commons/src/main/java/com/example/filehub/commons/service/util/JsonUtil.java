package com.example.filehub.commons.service.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author yinfelix
 * @date 2020/2/20
 */
@Slf4j
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    public static <T> T getPojoFromJsonString(String string, Class<T> clazz) {
        T dto = null;
        try {
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            /*
            可对对象级联属性进行解析
             */
            dto = objectMapper.readValue(string, clazz);
        } catch (JsonProcessingException e) {
            log.error("在将字符串解析成实体类时发生了JSON解析错误", e);
            e.printStackTrace();
        }
        return dto;
    }

    public static <T> T getPojoFromInputStream(InputStream stream, Class<T> clazz) {
        T dto = null;
        try {
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            dto = objectMapper.readValue(stream, clazz);
        } catch (IOException e) {
            log.error("在将字符串解析成实体类时发生了JSON解析错误", e);
            e.printStackTrace();
        }
        return dto;
    }

    public static String getJsonStringFromObject(Object dto) {
        String resultJsonString = null;
        try {
            resultJsonString = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            log.error("在将实体类解析成字符串实体类时发生了JSON解析错误", e);
            e.printStackTrace();
        }
        return resultJsonString;
    }

    public static String getJsonStringFromObjectIgnoresNull(Object dto) {
        String resultJsonString = null;
        try {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            resultJsonString = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            log.error("在将实体类解析成字符串实体类时发生了JSON解析错误", e);
            e.printStackTrace();
        }
        return resultJsonString;
    }
}
