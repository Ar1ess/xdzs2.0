package com.softlab.customer.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.softlab.common.service.ArticleService;
import com.softlab.common.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Ar1es
 * @date : 2019/11/8
 * @since : Java 8
 */

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class ArticleApi {

    private static final Logger logger = LoggerFactory.getLogger(ArticleApi.class);

    @Reference
    private ArticleService articleService;





}
