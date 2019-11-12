package com.softlab.common.service;

import com.softlab.common.model.Version;

/**
 * @author : Ar1es
 * @date : 2019/11/12
 * @since : Java 8
 */
public interface VersionService {

    /**
     * 查询版本信息
     * @return Version
     */
    Version selectVersion();


    /**
     * 修改版本信息
     * @param version
     * @return 0 -- 修改失败 1 -- 修改成功
     */
    int updateVersion(Version version);
}
