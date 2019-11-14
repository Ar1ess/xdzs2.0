package com.softlab.customer.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.softlab.common.RestData;
import com.softlab.common.model.ColleageContent;
import com.softlab.common.service.ColleageService;
import com.softlab.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author LiXiwen
 * @date 2019/11/13 8:56
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class ColleageApi {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference
    private ColleageService colleageService;

    /**
     * 每个图标是否亮的接口
     *
     * @return
     */
    @RequestMapping(value = "/colleage/time", method = RequestMethod.GET)
    public RestData colleageTime() {
        logger.info("GET colleageTime : ");
        RestData rtv = colleageService.selectStatus();
        return rtv;
    }


    /**
     * tip小建议
     *
     * @return
     */

    @RequestMapping(value = "/colleage/tip", method = RequestMethod.GET)
    public RestData colleageTip() {
        logger.info("GET colleageTip : ");
        RestData rtv = colleageService.selectTip();
        return rtv;
    }


    @RequestMapping(value = "/colleage/tip/detail/{tu}", method = RequestMethod.GET)
    public RestData colleageTipDetail(@PathVariable(value = "tu") String tu) {
        logger.info("GET colleageTipDetail , tu : " + tu);
        RestData rtv = colleageService.selectNoticeDetail(tu);
        return rtv;
    }

    /**
     * 管理colleageContent新通知
     * @param colleageContent
     * @param httpServletRequest
     * @return
     */
    /**
     * bumen : gongdian book  eat  swim  hospital water
     */
    @RequestMapping(value = "/mangeNefuContent", method = RequestMethod.POST)
    public RestData MangeNefuContent(@RequestBody ColleageContent colleageContent, HttpServletRequest httpServletRequest) {
        logger.info("POST MangeNefuContent , ColleageContent : " + JsonUtil.getJsonString(colleageContent));
        RestData rtv = colleageService.updateNotice(colleageContent);
        return rtv;
    }

}
