package com.softlab.common.model.vo;

import com.softlab.common.model.User;
import lombok.Data;

/**
 * Created by LiXiwen on 2019/3/25.
 **/
@Data
public class DecryptVo extends User {

    /**
     * 微信步数的加密数据
     */
    private String encryptedData;
    /**
     * 微信用户的iv
     */
    private String iv;
    /**
     * onlogin接口返回的sessionKey
     */
    private String sessionKey;

    private Integer days;

}
