package com.softlab.common.model.vo;

import com.softlab.common.model.Pace;
import lombok.Data;

import java.io.Serializable;

/**
 * @author LiXiwen
 * @date 2019/11/8 14:28
 */
@Data
public class PaceVo extends Pace implements Serializable {
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

}
