package com.softlab.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.softlab.common.ErrorMessage;
import com.softlab.common.exception.AppException;
import com.softlab.common.model.Mend;
import com.softlab.common.service.MendService;
import com.softlab.common.util.DateUtil;
import com.softlab.provider.mapper.MendMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Ar1es
 * @date : 2019/11/11
 * @since : Java 8
 */

@Service
//@org.springframework.stereotype.Service
public class MendServiceImpl implements MendService {

    @Autowired
    private MendMapper mendMapper;



    @Override
    public int addMend(Mend mend) {
        mend.setTime(DateUtil.localTimeToTimestamp());

        int flag = mendMapper.insertMend(mend);

        if (0 == flag) {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }

        return flag;
    }

    @Override
    public List<Map<String, Object>> selectAllMend() {
        List<Mend> list = mendMapper.selectAllMend();
        List<Map<String, Object>> rtv = new ArrayList<>();
        if (null != list) {
            for (Mend mend : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("areaId", mend.getAreaId());
                map.put("name", mend.getName());
                map.put("number", mend.getNumber());
                map.put("phoneNumber", mend.getPhone());
                map.put("context", mend.getContext());
                map.put("location", mend.getLocation());
                map.put("time", DateUtil.timestampToString(mend.getTime()));
                rtv.add(map);
            }
        } else {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }
        return rtv;
    }

    @Override
    public int deleteMendById(Integer id) {
        int flag = mendMapper.deleteMend(id);

        if (0 == flag) {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }

        return flag;
    }
}
