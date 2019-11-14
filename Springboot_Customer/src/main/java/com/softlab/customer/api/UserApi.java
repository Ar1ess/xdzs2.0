package com.softlab.customer.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.softlab.common.RestData;
import com.softlab.common.exception.AppException;
import com.softlab.common.model.Pace;
import com.softlab.common.model.Run;
import com.softlab.common.model.vo.DecryptVo;
import com.softlab.common.service.PaceService;
import com.softlab.common.service.UserService;
import com.softlab.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiXiwen
 * @date 2019/11/8 9:32
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class UserApi {
    private final Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Reference
    private UserService userService;

    @Reference
    private PaceService paceService;

    /**
     * 081iA7r61OX5LM1sYus61wV4r61iA7rR
     * @param code
     * @return
     */
    @RequestMapping(value = "/onlogin")
    public String login(@RequestParam(value = "code") String code) {
        logger.info("GET login , code : " + code);
        String rtv = userService.login(code);
        return rtv;
    }

    @PostMapping(value = "/decrypt")
    public RestData decrypt(@RequestBody DecryptVo decryptVo) {
        logger.info("POST decrypt , decryptVo : " + JsonUtil.getJsonString(decryptVo));
        RestData rtv = paceService.decrypt(decryptVo);
        return rtv;
    }

    @GetMapping("/selectPace")
    public RestData selectPace(@RequestParam(value = "openId") String openId) {
        logger.info("GET selectPace , openId : " + openId);
        RestData rtv = paceService.selectPace(openId);
        return rtv;
    }

    @RequestMapping(value = "/rundata", method = RequestMethod.POST)
    public RestData runData(@RequestBody Run run) {
        logger.info("POST runData : " + JsonUtil.getJsonString(run));
        RestData rtv = paceService.addRunData(run);
        return rtv;
    }

    @RequestMapping(value = "/selectRundata", method = RequestMethod.GET)
    public RestData selectRundata() {
        logger.info("GET selectRundata : ");
        RestData rtv = paceService.selectRunData();
        return rtv;
    }


}
