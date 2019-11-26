package com.softlab.common.service;

import com.softlab.common.RestData;
import com.softlab.common.model.ColleageContent;

/**
 * @author LiXiwen
 * @date 2019/11/13 8:58
 */
public interface ColleageService {
    /**
     * 查询状态
     * @return
     */
    RestData selectStatus();

    /**
     * 查询tip
     * @return
     */
    RestData selectTip();

    /**
     * 查询通知详情
     * @param bumen
     * @return
     */
    RestData selectNoticeDetail(String bumen);

    /**
     * 更新通知
     * @return
     */
    RestData updateNotice(ColleageContent colleageContent);

}
