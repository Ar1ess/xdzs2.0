/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package com.softlab.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author ZhengYi
 * @date 17-7-25
 * @since : Java 8
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();


    public static String getJsonString(Object object) {
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error(e.getLocalizedMessage());
        }
        return jsonString;
    }

    public static Map<String, Object> getMapFromJson(String jsonString) {
        Map<String, Object> map;
        try {
            map = objectMapper.readValue(jsonString, Map.class);
        } catch (IOException e) {
            map = null;
            logger.error(e.getLocalizedMessage());
        }
        return map;
    }

    public static List<Object> getListFromJson(String jsonString) {
        List<Object> list;
        try {
            list = objectMapper.readValue(jsonString, List.class);
        } catch (IOException e) {
            list = null;
            logger.error(e.getLocalizedMessage());
        }
        return list;
    }
}
