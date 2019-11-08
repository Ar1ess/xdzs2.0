package com.softlab.provider.service.impl;

import com.softlab.common.GlobalConst;
import com.softlab.common.RestData;
import com.softlab.common.model.Pace;
import com.softlab.common.util.AES;
import com.softlab.common.util.HttpRequestor;
import com.softlab.common.util.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.softlab.common.exception.AppException;
import com.softlab.common.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.softlab.common.GlobalConst.*;

/**
 * @author LiXiwen
 * @date 2019/11/7 20:14
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String login(String code) {
        String oppid = "";
        JSONObject oppidObj;

        // todo : url+code+url0
        try {
            oppid = new HttpRequestor().doGet( url + code + url0);
        } catch (Exception e) {
            throw new AppException(1, "获取openId失败");
        }
        oppidObj = JSONObject.fromObject(oppid);
        logger.info("openId : " + JsonUtil.getJsonString(oppidObj));
        return new Gson().toJson(oppidObj);
    }

    @Override
    public RestData decrypt(Pace pace) throws AppException {
        byte[] resultByte = AES.decrypt(Base64.decodeBase64(pace.getEncryptedData()),
                Base64.decodeBase64(pace.getSessionKey()),
                Base64.decodeBase64(pace.getIv()));
        return null;
    }
}
