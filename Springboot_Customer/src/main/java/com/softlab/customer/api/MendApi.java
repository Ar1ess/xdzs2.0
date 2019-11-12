package com.softlab.customer.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.softlab.common.RestData;
import com.softlab.common.model.Mend;
import com.softlab.common.service.MendService;
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
public class MendApi {

    private static final Logger logger = LoggerFactory.getLogger(MendApi.class);

    @Reference
    private MendService mendService;


    /**
     * 显示所有保修信息
     * @return RestData
     */
    @RequestMapping(value = "/selectAllArea", method = RequestMethod.GET)
    public RestData selectAllMend() {
        logger.info("保修信息查询 : ");

        List<Map<String, Object>> data = mendService.selectAllMend();

        return new RestData(data);

    }


    /**
     * 添加保修信息
     * @param mend
     * @return RestData
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public RestData addMend(@RequestBody Mend mend) {
        logger.info("报修开始新增 : " + mend.getLocation() + " " + mend.getContext());

        if ((0 >= mend.getNumber().length() || 10 < mend.getNumber().length()) || 0 >= mend.getName().length() || 11 != mend.getPhone().length()) {
            return new RestData(1, "输入数据格式不符合要求");
        }

        int flag = mendService.addMend(mend);

        return new RestData(flag);
    }


    /**
     * 删除保修信息
     * @param id
     * @return RestData
     */
    @RequestMapping(value = "/deleteArea", method = RequestMethod.GET)
    public RestData deleteMend(@RequestParam("id") Integer id) {
        logger.info("报修删除 : id = " + id);
        int flag = mendService.deleteMendById(id);

        return new RestData(flag);

    }






}
