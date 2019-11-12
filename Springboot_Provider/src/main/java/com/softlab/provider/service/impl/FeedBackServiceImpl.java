package com.softlab.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.softlab.common.ErrorMessage;
import com.softlab.common.exception.AppException;
import com.softlab.common.model.FeedBack;
import com.softlab.common.service.FeedBackService;
import com.softlab.common.util.DateUtil;
import com.softlab.provider.mapper.FeedBackMapper;
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
@org.springframework.stereotype.Service
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private FeedBackMapper feedBackMapper;


    @Override
    public int addFeedBack(FeedBack feedBack) {
        feedBack.setTime(DateUtil.localTimeToTimestamp());

        int flag = feedBackMapper.insertFeedBack(feedBack);

        if (0 == flag) {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }

        return flag;
    }

    @Override
    public List<Map<String, Object>> selectAllFeedBack() {
        List<FeedBack> list = feedBackMapper.selectAllFeedBack();
        List<Map<String, Object>> rtv = new ArrayList<>();
        if (null != list) {
            for (FeedBack f : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("fid", f.getFId());
                map.put("name", f.getName());
                map.put("number", f.getNumber());
                map.put("phoneNumber", f.getPhone());
                map.put("collageMajor", f.getColleageMajor());
                map.put("detail", f.getDetail());
                map.put("time", DateUtil.timestampToString(f.getTime()));
                rtv.add(map);
            }
        } else {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }
        return rtv;
    }

    @Override
    public int deleteFeedBack(Integer id) {
        int flag = feedBackMapper.deleteFeedBack(id);

        if (0 == flag) {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }

        return flag;
    }
}
