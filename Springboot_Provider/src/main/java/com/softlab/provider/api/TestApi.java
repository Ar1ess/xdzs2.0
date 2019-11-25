package com.softlab.provider.api;

import com.softlab.common.RestData;
import com.softlab.common.service.PaceFzService;
import com.softlab.common.service.PaceService;
import com.softlab.common.util.JsonUtil;
import com.softlab.provider.mapper.RedisMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author LiXiwen
 * @date 2019/11/12 14:12
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class TestApi {
    private static final Logger logger = LoggerFactory.getLogger(TestApi.class);

    @Autowired
    private PaceFzService paceFzService;

    @GetMapping("/init")
    public void init() {
        paceFzService.init1();
    }


}
