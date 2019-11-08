package com.softlab.common.service;

import com.softlab.common.RestData;
import com.softlab.common.exception.AppException;
import com.softlab.common.model.Pace;

/**
 * @author LiXiwen
 * @date 2019/11/7 20:12
 */

public interface UserService {
    String login(String code) throws AppException;
    RestData decrypt(Pace pace) throws AppException;
}
