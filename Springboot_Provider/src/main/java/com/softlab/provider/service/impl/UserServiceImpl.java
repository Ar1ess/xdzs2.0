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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

import static com.softlab.common.GlobalConst.*;

/**
 * @author LiXiwen
 * @date 2019/11/7 20:14
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    int n;
    int m1;
    int m2;
    int m3;
    int m4;
    int m5;
    int m6;
    int m7;
    int m8;
    int m9;

    int n1;
    int n2;
    int n3;
    int n4;
    int n5;
    int n6;
    int n7;
    int n8;
    int n9;

    private final UserMapper userMapper;
    private final RedisService redisService;
    private final PaceFzService paceFzService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RedisService redisService,  PaceFzService paceFzService) {
        this.userMapper = userMapper;
        this.redisService = redisService;
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
    @Transactional
    public RestData addUser(User user) {
        // todo ：获取redis中用户数量
        n = (int) redisService.zCard();
        if (n == 0) {
            n = userMapper.getUserSize();
        }
        logger.info("用户数量n : " + n);
        setConst();
        // todo ：查看是否有该用户

        // todo ：没有
        if (redisService.zScore(user.getOpenId()) == null) {
            logger.info("没有该用户 , user = "+JsonUtil.getJsonString(user));
            if(userMapper.addUser(user) <=0 & redisService.zAdd(user.getOpenId(), user.getUserPace()) <= 0 ) {
                logger.error("添加user ：error ");
                throw new AppException(1, "添加User失败!");
            }
            // 让用户数量+1
            redisService.incrUserSize();

            int rank = (int) redisService.zRank(user.getOpenId());
            logger.info("rank + 1 : " + (rank + 1));
            Pace pace = new Pace();
            BeanUtils.copyProperties(user, pace);
            pace.setPace(user.getUserPace());
            pace.setRank(rank + 1);
            setPace(rank + 1, pace);

            if (!paceFzService.addPace(pace)) {
                logger.error("添加Pace失败!");
                throw new AppException(1, "添加Pace失败!");
            }
            setRtv(pace);

            setOtherRtv(pace, rank);


        } else {
            // todo : 是否执行更新
            if (user.getUserPace() != redisService.zScore(user.getOpenId())) {
                // todo ：更新，先更新数据库
                if (0 >= userMapper.updateUser(user)) {
                    logger.error("更新User失败!");
                    throw new AppException(1, "更新User失败!");
                }
                redisService.zAdd(user.getOpenId(), user.getUserPace());
                int rank = (int) redisService.zRank(user.getOpenId());
                Pace pace = new Pace();
                BeanUtils.copyProperties(user, pace);
                pace.setRank(rank + 1);
                // todo ：查看该排名所在段位并赋值
                setPace(rank + 1, pace);

                if (!paceFzService.updatePace(pace)) {
                    logger.error("更新Pace失败");
                    throw new AppException(1, "更新Pace失败");
                }
                setRtv(pace);
                redisService.delList();

                setOtherRtv(pace, rank);

            }
        }
        return new RestData(0, "success");
    }

    // https://blog.csdn.net/u014252478/article/details/99946834

    private void setOtherRtv(Pace pace, int rank) {
        List<PaceVo> paceVos = paceFzService.selectPartPaceByRank(pace);

        // todo : 必须从0开始，加上parallel是并行处理
        Stream.iterate(0, i -> i+1).limit(paceVos.size()).parallel().forEach(
                i -> {
                    if (i >= rank) {
                        paceVos.get(i).setRank(paceVos.get(i).getRank() + 1);
                        setPace(paceVos.get(i).getRank(), paceVos.get(i));
                    }
                });

        redisService.setList(paceVos);
        // todo ： update mysql
        paceFzService.updatePartPace(paceVos);
    }

    private void setConst() {
        m1 = (int) (n * 1 / 55);
        m2 = (int) (n * 2 / 55);
        m3 = (int) (n * 3 / 55);
        m4 = (int) (n * 4 / 55);
        m5 = (int) (n * 5 / 55);
        m6 = (int) (n * 6 / 55);
        m7 = (int) (n * 7 / 55);
        m8 = (int) (n * 8 / 55);
        m9 = (int) (n * 9 / 55);

        n1 = m1 + 1;
        n2 = m2 + m1 + 1;
        n3 = m3 + m2 + m1 + 1;
        n4 = m4 + m3 + m2 + 1;
        n5 = m5 + m4 + m3 + m2 + m1 + 1;
        n6 = m6 + m5 + m4 + m3 + m2 + m1 + 1;
        n7 = m7 + m6 + m5 + m4 + m3 + m2 + m1 + 1;
        n8 = m8 + m7 + m6 + m5 + m4 + m3 + m2 + m1 + 1;
        n9 = m9 + m8 + m7 + m6 + m5 + m4 + m3 + m2 + m1 + 1;
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
            System.out.println("n1");
            pace.setPaiwei("冠军王者");
            pace.setImg("wang");
            pace.setColor("#fff143");
        } else if (i <= n2) {
            System.out.println("n2");
            pace.setPaiwei("冲刺星耀");
            pace.setImg("xing");
            pace.setColor("#ffcc66");
        } else if (i <= n3) {
            System.out.println("n3");
            pace.setPaiwei("力行大师");
            pace.setImg("da");
            pace.setColor("#725E82");
        } else if (i <= n4) {
            System.out.println("n4");
            pace.setPaiwei("运动钻石");
            pace.setImg("zuan");
            pace.setColor("#ed5736");
        } else if (i <= n5) {
            System.out.println("n5");
            pace.setPaiwei("健步铂金");
            pace.setImg("bo");
            pace.setColor("#0094ff");
        } else if (i <= n6) {
            System.out.println("n6");
            pace.setPaiwei("速行黄金");
            pace.setImg("huang");
            pace.setColor("#eacd76");
        } else if (i <= n7) {
            System.out.println("n7");
            pace.setPaiwei("行走白银");
            pace.setImg("bai");
            pace.setColor("#e9e7ef");
        } else if (i <= n8) {
            System.out.println("n8");
            pace.setPaiwei("踱步青铜");
            pace.setImg("qing");
            pace.setColor("#a78e44");
        } else if (i <= n9) {
            System.out.println("n9");
            pace.setPaiwei("宅家咸鱼");
            pace.setImg("zhai");
            pace.setColor("#1bd1a5");
        } else {
            System.out.println("n10");
            pace.setPaiwei("躺着不动");
            pace.setImg("tang");
            pace.setColor("#003371");
        }
    }



}
