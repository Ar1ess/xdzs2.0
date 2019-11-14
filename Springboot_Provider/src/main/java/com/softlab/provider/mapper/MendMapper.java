package com.softlab.provider.mapper;

import com.softlab.common.model.Mend;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Ar1es
 * @date : 2019/11/11
 * @since : Java 8
 */

@Mapper
@Repository
public interface MendMapper {

    /**
     * 根据主键ID查询保修详细信息
     * @param id
     * @return mend
     */
    Mend selectMendById(Integer id);


    /**
     * 新增保修
     * @param mend
     * @return 0 -- 增加失败 1 -- 增加成功
     */
    int insertMend(Mend mend);


    /**
     * 查询所有保修信息
     * @return List<Mend>
     */
    List<Mend> selectAllMend();


    /**
     * 删除保修信息
     * @param id
     * @return 0 -- 删除失败 1 -- 删除成功
     */
    int deleteMend(Integer id);




}
