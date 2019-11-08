package com.softlab.provider.service.impl;

import com.softlab.common.ErrorMessage;
import com.softlab.common.GlobalConst;
import com.softlab.common.RestData;
import com.softlab.common.model.Pace;
import com.softlab.common.model.vo.DecryptVo;
import com.softlab.common.util.AES;
import com.softlab.common.util.HttpRequestor;
import com.softlab.common.util.JsonUtil;
import org.apache.commons.codec.binary.Base64;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.softlab.common.exception.AppException;
import com.softlab.common.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
        net.sf.json.JSONObject oppidObj;

        // todo : url+code+url0
        try {
            oppid = new HttpRequestor().doGet( url + code + url0);
        } catch (Exception e) {
            throw new AppException(1, "获取openId失败");
        }
        logger.info("oppid : " + oppid);
        oppidObj = net.sf.json.JSONObject.fromObject(oppid);
        logger.info("openId : " + JsonUtil.getJsonString(oppidObj));
        return new Gson().toJson(oppidObj);
    }

    @Override
    public RestData decrypt(DecryptVo decryptVo) throws AppException {
        int paceSum = 0;
        try {
            byte[] resultByte = AES.decrypt(Base64.decodeBase64(decryptVo.getEncryptedData()),
                    Base64.decodeBase64(decryptVo.getSessionKey()),
                    Base64.decodeBase64(decryptVo.getIv()));

            if (null != resultByte && resultByte.length > 0) {
                String userInfo = new String(resultByte, "UTF-8");
                // 先转成JSONBbject对象
                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(userInfo);
                // 获取其中key为stepInfoList的jsonArray
                JSONArray jsonArray = jsonObject.getJSONArray("stepInfoList");
                for (int i = 28; i < jsonArray.size(); i++) {
                    paceSum += (int) jsonArray.getJSONObject(i).get("step");
                }

                if (decryptVo.getUserName().length() > 5) {
                    decryptVo.setUserName(decryptVo.getUserName().substring(0, 3) + ".");
                }
                decryptVo.setUserPace(paceSum);


            }
        } catch (Exception e) {
            throw new AppException(1, ErrorMessage.JIE_MI + e.getMessage());
        }

        return null;
    }
}
