package com.softlab.customer.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.softlab.common.RestData;
import com.softlab.common.exception.AppException;
import com.softlab.common.model.Pace;
import com.softlab.common.service.UserService;
import com.softlab.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiXiwen
 * @date 2019/11/8 9:32
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class UserApi {
    private static final Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Reference
    private UserService userService;

    @RequestMapping(value = "/onlogin")
    public String login(@RequestParam(value = "code") String code) {
        logger.info("code : " + code);

        userService.login(code);
        return "true";
    }

    @PostMapping(value = "/decrypt")
    public RestData decrypt(@RequestBody Pace pace) {
        logger.info("pace : " + JsonUtil.getJsonString(pace));

        Map<String, Object> map = new HashMap<>(8);


    }

}
