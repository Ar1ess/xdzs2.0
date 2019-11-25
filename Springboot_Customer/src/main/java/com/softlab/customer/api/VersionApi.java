package com.softlab.customer.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.softlab.common.RestData;
import com.softlab.common.model.Version;
import com.softlab.common.service.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author : Ar1es
 * @date : 2019/11/12
 * @since : Java 8
 */

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class VersionApi {

    private static final Logger logger = LoggerFactory.getLogger(VersionApi.class);

    @Reference
    private VersionService versionService;



    /**
     * 查询版本信息
     * @return RestData
     */
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public RestData VersionControll() {
        HashMap<String, String> hashMap = new HashMap<>();
        Version v = versionService.selectVersion();
        String version = v.getVersion();
        String data = v.getData();
        hashMap.put("version", version);
        hashMap.put("data", data);
        return new RestData(hashMap);
    }


    /**
     * 修改版本信息
     * @param version
     * @return RestData
     */
    @RequestMapping(value = "/updateVersion", method = RequestMethod.POST)
    public RestData updateVersion(@RequestBody Version version) {
        HashMap<String, Object> hashMap = new HashMap<>();
        int flag = versionService.updateVersion(version);
        if (0 < flag) {
            hashMap.put("code", 0);
            hashMap.put("message", "success");
        } else {
            hashMap.put("code", 1);
            hashMap.put("message", "error");
        }
        return new RestData(hashMap);
    }
}
