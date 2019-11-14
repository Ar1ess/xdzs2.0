package com.softlab.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.softlab.common.ErrorMessage;
import com.softlab.common.GlobalConst;
import com.softlab.common.exception.AppException;
import com.softlab.common.model.Version;
import com.softlab.common.service.VersionService;
import com.softlab.provider.mapper.RedisMapper;
import com.softlab.provider.mapper.VersionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : Ar1es
 * @date : 2019/11/12
 * @since : Java 8
 */

@Service
//@org.springframework.stereotype.Service
public class VersionServiceImpl implements VersionService {


    private static final Logger logger = LoggerFactory.getLogger(VersionServiceImpl.class);

    private final VersionMapper versionMapper;
    private final RedisMapper redisMapper;


    @Autowired
    public VersionServiceImpl(VersionMapper versionMapper, RedisMapper redisMapper) {
        this.versionMapper = versionMapper;
        this.redisMapper = redisMapper;
    }


    @Override
    public Version selectVersion() {
        if (redisMapper.hasKey(GlobalConst.VERSION)) {
            return (Version) redisMapper.get(GlobalConst.VERSION);
        }
        Version version = versionMapper.selectVersion();
        if (version != null) {
            redisMapper.set(GlobalConst.VERSION, version);
        } else {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }
        return version;
    }

    @Override
    public int updateVersion(Version version) {
        version.setId(1);

        int flag = versionMapper.updateVersion(version);

        if (0 < flag) {
            if (redisMapper.hasKey(GlobalConst.VERSION)) {
                logger.info("版本信息修改成功, 将删除缓存");
                redisMapper.del(GlobalConst.VERSION);
                logger.info("删除缓存成功");
            }
        } else {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }

        return flag;
    }
}
