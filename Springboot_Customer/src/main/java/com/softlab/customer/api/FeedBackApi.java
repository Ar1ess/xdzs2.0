package com.softlab.customer.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.softlab.common.RestData;
import com.softlab.common.model.FeedBack;
import com.softlab.common.service.FeedBackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author : Ar1es
 * @date : 2019/11/11
 * @since : Java 8
 */
@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class FeedBackApi {

    private static final Logger logger = LoggerFactory.getLogger(FeedBackApi.class);

    @Reference
    private FeedBackService feedBackService;

    @RequestMapping(value = "/selectAllFanKui", method = RequestMethod.GET)
    public RestData selectAllFanKui() {
        logger.info("GET 反馈查询: ");

        List<Map<String, Object>> data = feedBackService.selectAllFeedBack();
        return new RestData(data);
    }

    /**
     * 实现反馈添加功能
     * @param feedBack
     * @return RestData
     */
    @RequestMapping(value = "/addFan", method = RequestMethod.POST)
    public RestData addFanKui(@RequestBody FeedBack feedBack) {
        logger.info("POST 反馈开始新增 = " + feedBack.getDetail());
        if ((0 >= feedBack.getNumber().length() || 10 < feedBack.getNumber().length()) || 0 >= feedBack.getName().length() || 11 != feedBack.getPhone().length()) {
            return new RestData(1, "输入数据格式不符合要求");
        }
            int flag = feedBackService.addFeedBack(feedBack);

            return new RestData(flag);
    }


    /**
     * 删除反馈
     * @param id
     * @return RestData
     */
    @RequestMapping(value = "/deleteFan", method = RequestMethod.GET)
    public RestData deleteFan(@RequestParam("id") Integer id) {
        logger.info("GET 反馈删除：id = " + id);
        int flag = feedBackService.deleteFeedBack(id);

        return new RestData(flag);
    }



}
