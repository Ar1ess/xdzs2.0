package com.softlab.customer.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.softlab.common.RestData;
import com.softlab.common.model.vo.CourseVo;
import com.softlab.common.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author : Ar1es
 * @date : 2019/11/11
 * @since : Java 8
 */
@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class CourseApi {

    private static final Logger logger = LoggerFactory.getLogger(FeedBackApi.class);

    @Reference
    private CourseService courseService;



    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public boolean getTime(@RequestParam(value = "openId") String openId, @RequestParam(value = "userName") String userName) {

     return courseService.selectRedisIsExist(openId);


    }

    @RequestMapping(value = "/zan", method = RequestMethod.POST)
    public RestData updateZan(@RequestBody CourseVo cv) {

        if (null != cv.getLike1() && !"".equals(cv.getLike1())) {
            logger.info("找到课程，投票数开始增加," + cv.getLike1());
            courseService.updateZan(cv.getLike1());
        }

        if (null != cv.getLike2() && !"".equals(cv.getLike2())) {
            logger.info("找到课程，投票数开始增加," + cv.getLike2());
            courseService.updateZan(cv.getLike2());
        }

        if (null != cv.getLike3() && !"".equals(cv.getLike3())) {
            logger.info("找到课程，投票数开始增加," + cv.getLike3());
            courseService.updateZan(cv.getLike3());
        }

        if (null != cv.getLike4() && !"".equals(cv.getLike4())) {
            logger.info("找到课程，投票数开始增加," + cv.getLike4());
            courseService.updateZan(cv.getLike4());
        }

        if (null != cv.getLike5() && !"".equals(cv.getLike5())) {
            logger.info("找到课程，投票数开始增加," + cv.getLike5());
            courseService.updateZan(cv.getLike5());
        }

        if (null != cv.getLike6() && !"".equals(cv.getLike6())) {
            logger.info("找到课程，投票数开始增加," + cv.getLike6());
            courseService.updateZan(cv.getLike6());

        }
        logger.info("票数增加完毕");

        return new RestData(0);
    }



    @RequestMapping(value = "/sort", method = RequestMethod.GET)
    public List<Map<String, Object>> sort() {

        return courseService.selectByOrder();
    }

}
