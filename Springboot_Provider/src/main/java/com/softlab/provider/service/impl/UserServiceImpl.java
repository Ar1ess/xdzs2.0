package com.softlab.provider.service.impl;

import com.softlab.common.ErrorMessage;
import com.softlab.common.RestData;
import com.softlab.common.model.Pace;
import com.softlab.common.model.User;
import com.softlab.common.model.vo.PaceVo;
import com.softlab.common.service.PaceFzService;
import com.softlab.common.service.RedisService;
import com.softlab.common.util.HttpRequestor;
import com.softlab.common.util.JsonUtil;
import com.softlab.provider.mapper.PaceMapper;
import com.softlab.provider.mapper.RedisMapper;
import com.softlab.provider.mapper.UserMapper;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.softlab.common.exception.AppException;
import com.softlab.common.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.softlab.common.GlobalConst.*;

/**
 * @author LiXiwen
 * @date 2019/11/7 20:14
 */
@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    int n;
    int m1 = (int) (n * 1 / 55);
    int m2 = (int) (n * 2 / 55);
    int m3 = (int) (n * 3 / 55);
    int m4 = (int) (n * 4 / 55);
    int m5 = (int) (n * 5 / 55);
    int m6 = (int) (n * 6 / 55);
    int m7 = (int) (n * 7 / 55);
    int m8 = (int) (n * 8 / 55);
    int m9 = (int) (n * 9 / 55);

    int n1 = m1 + 1;
    int n2 = m2 + m1 + 1;
    int n3 = m3 + m2 + m1 + 1;
    int n4 = m4 + m3 + m2 + 1;
    int n5 = m5 + m4 + m3 + m2 + m1 + 1;
    int n6 = m6 + m5 + m4 + m3 + m2 + m1 + 1;
    int n7 = m7 + m6 + m5 + m4 + m3 + m2 + m1 + 1;
    int n8 = m8 + m7 + m6 + m5 + m4 + m3 + m2 + m1 + 1;
    int n9 = m9 + m8 + m7 + m6 + m5 + m4 + m3 + m2 + m1 + 1;

    private final RedisMapper redisMapper;
    private final UserMapper userMapper;
    private final RedisService redisService;
    private final PaceMapper paceMapper;
    private final PaceFzService paceFzService;

    @Autowired
    public UserServiceImpl(RedisMapper redisMapper, UserMapper userMapper, RedisService redisService, PaceMapper paceMapper, PaceFzService paceFzService) {
        this.redisMapper = redisMapper;
        this.userMapper = userMapper;
        this.redisService = redisService;
        this.paceMapper = paceMapper;
        this.paceFzService = paceFzService;
    }

    @Override
    public String login(String code) {
        String oppid;
        net.sf.json.JSONObject oppidObj;
        // todo : url+code+url0
        try {
            oppid = new HttpRequestor().doGet( url + code + url0);
        } catch (Exception e) {
            logger.error("获取openId失败");
            throw new AppException(1, "获取openId失败");
        }
        oppidObj = net.sf.json.JSONObject.fromObject(oppid);
        String rtv = new Gson().toJson(oppidObj);
        return rtv;
    }

    /**
     * 预设部分redis key的值
     * @param user
     * @return
     */
    @Override
    public RestData addUser(User user) {
        // todo ：获取redis中用户数量
        n = (int) redisService.zCard();
        if (n == 0) {
            n = userMapper.getUserSize();
        }
        logger.info("用户数量n : " + n);
        // todo ：查看是否有该用户

        // todo ：没有
        if (redisService.zScore(user.getOpenId()) == null) {
            logger.info("list == null ,list.size() == 0,user = "+JsonUtil.getJsonString(user));
            if(userMapper.addUser(user) <= 0 &&
                    !redisService.zAdd(user.getOpenId(), user.getUserPace())) {
                throw new AppException(1, "添加User失败!");
            }

            int rank = (int) redisService.zRank(user.getOpenId());
            Pace pace = new Pace();
            BeanUtils.copyProperties(user, pace);
            pace.setPace(user.getUserPace());
            pace.setRank(rank);
            setPace(rank, pace);
            if (!paceFzService.addPace(pace)) {
                throw new AppException(1, "添加Pace失败!");
            }
            setRtv(pace);
            List<PaceVo> paceVos = paceFzService.selectPaceDescRank();
            redisService.setList(paceVos);
            // 让用户数量+1
            redisService.incrUserSize();
        } else {
            // todo : 是否执行更新
            if (user.getUserPace() != redisService.zScore(user.getOpenId())) {
                // todo ：更新，先更新数据库
                if (0 >= userMapper.updateUser(user)) {
                    throw new AppException(1, "更新User失败!");
                }
                redisService.zAdd(user.getOpenId(), user.getUserPace());
                int rank = (int) redisService.zRank(user.getOpenId());
                Pace pace = new Pace();
                BeanUtils.copyProperties(user, pace);
                pace.setRank(rank);
                // todo ：查看该排名所在段位并赋值
                setPace(rank, pace);
                if (!paceFzService.updatePace(pace)) {
                    throw new AppException(1, "更新Pace失败");
                }
                setRtv(pace);
                redisService.delList();
                List<PaceVo> paceVoList = paceMapper.selectPaceDescRank();
                redisService.setList(paceVoList);
            }
        }
        return new RestData(0, "success");
    }

    public void setRtv(Pace pace) {
        PaceVo paceVo = paceFzService.selectPaceVo(pace).get(0);
        Map<String,Object> map = new HashMap<>(8);
        map.put("Ipaiwei", paceVo.getPaiwei());
        map.put("Istyle", paceVo.getImg());
        map.put("Ipace", paceVo.getPace());
        map.put("Irank", paceVo.getRank());
        map.put("Iicon", paceVo.getIcon());
        map.put("Iname", paceVo.getName());
        map.put("Iimg", paceVo.getImg());
        redisService.setPaceVoBean(pace.getOpenId(), map);
    }


    public void setPace(int i, Pace pace) {
        if (i <= n1) {
            pace.setPaiwei("冠军王者");
            pace.setImg("wang");
            pace.setColor("#fff143");
        }
        if (i > n1 && i <= n2) {
            pace.setPaiwei("冲刺星耀");
            pace.setImg("xing");
            pace.setColor("#ffcc66");
        }
        if (i > n2 && i <= n3) {
            pace.setPaiwei("力行大师");
            pace.setImg("da");
            pace.setColor("#725E82");
        }
        if (i > n3 && i <= n4) {
            pace.setPaiwei("运动钻石");
            pace.setImg("zuan");
            pace.setColor("#ed5736");
        }
        if (i > n4 && i <= n5) {
            pace.setPaiwei("健步铂金");
            pace.setImg("bo");
            pace.setColor("#0094ff");
        }
        if (i > n5 && i <= n6) {
            pace.setPaiwei("速行黄金");
            pace.setImg("huang");
            pace.setColor("#eacd76");
        }
        if (i > n6 && i <= n7) {
            pace.setPaiwei("行走白银");
            pace.setImg("bai");
            pace.setColor("#e9e7ef");
        }
        if (i > n7 && i <= n8) {
            pace.setPaiwei("踱步青铜");
            pace.setImg("qing");
            pace.setColor("#a78e44");
        }
        if (i > n8 && i <= n9) {
            pace.setPaiwei("宅家咸鱼");
            pace.setImg("zhai");
            pace.setColor("#1bd1a5");
        }
        if (i > n9) {
            pace.setPaiwei("躺着不动");
            pace.setImg("tang");
            pace.setColor("#003371");
        }
    }



}
