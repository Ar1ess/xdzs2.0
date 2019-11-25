package com.softlab.provider.mapper;

import com.softlab.common.model.Pace;
import com.softlab.common.model.Run;
import com.softlab.common.model.vo.PaceVo;
import com.softlab.common.model.vo.RunVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LiXiwen
 * @date 2019/11/11 12:55
 */
@Mapper
@Repository
public interface PaceMapper {

    /**
     * 添加单个pace
     * @param pace
     * @return
     */
    boolean addPace(Pace pace);

    /**
     * 按照rank降序查询
     * @return
     */
    List<PaceVo> selectPaceByRank();

    /**
     * 更新pace表
     * @param pace
     * @return
     */
    boolean updatePace(Pace pace);

    /**
     * 更新部分用户信息
     * @param list
     * @return
     */
    boolean updatePartPace(@Param("list") List<PaceVo> list);
    /**
     * 查询paceVo，用作返回值的全部信息
     * @param pace
     * @return
     */
    List<PaceVo> selectPaceVo(Pace pace);


    /**
     * 查询步数
     * @return
     */
    List<Pace> selectPace1();

    /**
     * 添加跑步数据
     * @param run
     * @return
     */
    boolean addRunData(Run run);

    /**
     * 查询跑步数据
     * @return
     */
    List<RunVo> selectRunData();

    /**
     * 初始化
     * @return
     */
    List<PaceVo> init1();

    /**
     * 查询除当前用户外的所有用户
     * @param pace
     * @return
     */
    List<PaceVo> selectPartPaceByRank(Pace pace);

}
