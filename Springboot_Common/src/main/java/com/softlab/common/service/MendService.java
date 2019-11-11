package com.softlab.common.service;

import com.softlab.common.model.Mend;

import java.util.List;
import java.util.Map;

/**
 * @author : Ar1es
 * @date : 2019/11/11
 * @since : Java 8
 */
public interface MendService {


    /**
     * 添加保修信息
     * @param mend
     * @return 0 -- 增加失败 1 -- 增加成功
     */
    int addMend(Mend mend);


    /**
     * 查询所有保修信息
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> selectAllMend();

    /**
     * 删除保修信息
     * @param id
     * @return
     */
    int deleteMendById(Integer id);

}
