package com.softlab.common.service;

import com.softlab.common.RestData;
import com.softlab.common.exception.AppException;
import com.softlab.common.model.Pace;
import com.softlab.common.model.vo.DecryptVo;

/**
 * @author LiXiwen
 * @date 2019/11/7 20:12
 */

public interface UserService {
    /**
     * 登录按钮
     * @param code
     * @return
     * @throws AppException
     */
    String login(String code) throws AppException;

    /**
     * 解密数据
     * @param decryptVo
     * @return
     * @throws AppException
     */
    RestData decrypt(DecryptVo decryptVo) throws AppException;
}
