package com.softlab.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.softlab.common.ErrorMessage;
import com.softlab.common.RestData;
import com.softlab.common.exception.AppException;
import com.softlab.common.model.Pace;
import com.softlab.common.model.Run;
import com.softlab.common.model.vo.DecryptVo;
import com.softlab.common.model.vo.PaceVo;
import com.softlab.common.model.vo.RunVo;
import com.softlab.common.service.PaceService;
import com.softlab.common.service.RedisService;
import com.softlab.common.util.AES;
import com.softlab.common.util.JsonUtil;
import com.softlab.provider.mapper.PaceMapper;
import com.softlab.provider.mapper.RedisMapper;
import com.softlab.provider.rabbitmq.MQSender;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.softlab.common.GlobalConst.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiXiwen
 * @date 2019/11/11 8:03
 */
@com.alibaba.dubbo.config.annotation.Service
public class PaceServiceImpl implements PaceService {
    private static final Logger logger = LoggerFactory.getLogger(PaceServiceImpl.class);

    private final RedisMapper redisMapper;
    private final MQSender mqSender;
    private final PaceMapper paceMapper;
    private final RedisService redisService;

    @Autowired
    public PaceServiceImpl(RedisMapper redisMapper, MQSender mqSender, PaceMapper paceMapper, RedisService redisService) {
        this.redisMapper = redisMapper;
        this.mqSender = mqSender;
        this.paceMapper = paceMapper;
        this.redisService = redisService;
    }

    @Override
    public RestData decrypt(DecryptVo decryptVo) {
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
                mqSender.send(decryptVo);
                return new RestData(new HashMap<>(2).put("解密情况", "success"));
            } else {
                throw new AppException(1, ErrorMessage.JIE_MI);
            }
        } catch (Exception e) {
            throw new AppException(1, ErrorMessage.JIE_MI + e.getMessage());
        }
    }


    @Override
    public RestData selectPace(String openId) {
        Object obj = redisService.getPaceVoBean(openId);
        List<PaceVo> other;
        other = redisService.getList();
        if (null == other) {
            logger.warn("用户进来立马就点步数，系统异步任务还未完成，查数据库");
            other = paceMapper.selectPaceDescRank();
            redisService.setList(other);
        }
        logger.info("other : " + JsonUtil.getJsonString(other));

        if (null != obj) {
            logger.info("redis --> my : " + JsonUtil.getJsonString(obj));
            return new RestData(obj, other);
        } else {
            Pace pace = new Pace();
            pace.setOpenId(openId);
            PaceVo paceVo = paceMapper.selectPaceVo(pace).get(0);
            Map<String,Object> map = new HashMap<>(8);
            map.put("Ipaiwei", paceVo.getPaiwei());
            map.put("Istyle", paceVo.getImg());
            map.put("Ipace", paceVo.getPace());
            map.put("Irank", paceVo.getRank());
            map.put("Iicon", paceVo.getIcon());
            map.put("Iname", paceVo.getName());
            map.put("Iimg", paceVo.getImg());
            redisService.setPaceVoBean(pace.getOpenId(), map);

            logger.warn("mysql --> my : " + JsonUtil.getJsonString(map));
            return new RestData(map, other);
        }
    }

    @Override
    public RestData addRunData(Run run) {
        if (paceMapper.addRunData(run)) {
            return new RestData(0, "add runData success");
        }
        return new RestData(1, "error");
    }

    @Override
    public RestData selectRunData() {
        List<RunVo> rtv = paceMapper.selectRunData();
        if (rtv.size() > 0) {
            return new RestData(rtv);
        }
        return new RestData(1, "无数据!");
    }


}
