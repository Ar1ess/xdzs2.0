package com.softlab.provider.mapper;

import com.softlab.common.model.Version;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author : Ar1es
 * @date : 2019/11/12
 * @since : Java 8
 */

@Mapper
@Repository
public interface VersionMapper {


    /**
     * 查询版本信息
     * @return Version
     */
    @Select("select * from version limit 1")
    Version selectVersion();


    /**
     * 修改版本信息
     * @param version
     * @return 0 -- 修改失败 1 -- 修改成功
     */
    @Update("update version set version = #{version}, data = #{data} where id = #{id}")
    int updateVersion(Version version);
}
